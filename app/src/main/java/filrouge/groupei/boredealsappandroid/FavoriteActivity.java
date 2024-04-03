package filrouge.groupei.boredealsappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;


    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerView = findViewById(R.id.fav_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeAdapter = new StoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(storeAdapter);


        fetchFavoriteStoreIds();
    }


    private void fetchStores(List<String> favoriteIds) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/X3LAX/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        StoreService service = retrofit.create(StoreService.class);

        service.getStores().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Store> allStores = response.body();
                    
                    List<Store> favoriteStores = allStores.stream()
                            .filter(store -> favoriteIds.contains(String.valueOf(store.getId())))
                            .collect(Collectors.toList());
                    storeAdapter.setData(favoriteStores);


                } else {
                    Toast.makeText(FavoriteActivity.this, "Failed to fetch stores.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {
                Toast.makeText(FavoriteActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchFavoriteStoreIds() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference favoriteStoresRef = FirebaseDatabase.getInstance().getReference("favorite_stores").child(userId);
        favoriteStoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> favoriteIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    favoriteIds.add(snapshot.getKey());
                }
                fetchStores(favoriteIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FavoriteActivity.this, "Failed to fetch favorite stores.", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
