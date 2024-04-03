package filrouge.groupei.boredealsappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> allStores;
    private SeekBar seekBar;
    private TextView percentageTextView;
    private static final int ANIMATION_INTERVAL = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));

        storeAdapter = new StoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(storeAdapter);

        seekBar = findViewById(R.id.seekBar);
        percentageTextView = findViewById(R.id.percentageTextView);

        fetchAllStores();

        // Pour l'animation
        ImageView heart = findViewById(R.id.openFav);
        Animation heartBeat = AnimationUtils.loadAnimation(MainActivity.this, R.anim.heart_beat);

        Handler handler = new Handler();
        Runnable animationRunnable = new Runnable() {
            @Override
            public void run() {
                heart.startAnimation(heartBeat);
                handler.postDelayed(this, ANIMATION_INTERVAL);
            }

        };
        handler.postDelayed(animationRunnable, ANIMATION_INTERVAL);

        findViewById(R.id.openFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
        });


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            fetchFavoriteStores(currentUser.getUid());
        }
    }

    private void fetchAllStores() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/X3LAX/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        StoreService service = retrofit.create(StoreService.class);

        service.getStores().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allStores = response.body();
                    updateUI(allStores);

                    int maxDiscount = allStores.stream().mapToInt(Store::getDiscountPercentage).max().orElse(100);
                    setupSeekBar(maxDiscount);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch stores.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSeekBar(int maxDiscount) {
        seekBar.setMax(maxDiscount / 5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percentage = progress * 5;
                percentageTextView.setText(percentage + "%");

                List<Store> filteredStores = new ArrayList<>();
                for (Store store : allStores) {
                    if (store.getDiscountPercentage() >= percentage) {
                        filteredStores.add(store);
                    }
                }
                storeAdapter.setData(filteredStores);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateUI(List<Store> stores) {
        storeAdapter.setData(stores);
        storeAdapter.notifyDataSetChanged();
    }

    private void fetchFavoriteStores(String uid) {
        DatabaseReference favoriteStoresRef = FirebaseDatabase.getInstance().getReference().child("favorite_stores").child(uid);
        favoriteStoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Integer> favoriteStoreIds = new ArrayList<>();
                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    String storeId = storeSnapshot.getKey();
                    if (storeId != null) {
                        favoriteStoreIds.add(Integer.parseInt(storeId));
                    }
                }
                updateUIWithFavoriteStores(favoriteStoreIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error fetching favorite stores: " + databaseError.getMessage());
            }
        });
    }

    private void updateUIWithFavoriteStores(List<Integer> favoriteStoreIds) {
        List<Store> favoriteStores = new ArrayList<>();
        for (Store store : allStores) {
            if (favoriteStoreIds.contains(store.getId())) {
                store.setFavourite(true);
            }
            favoriteStores.add(store);
        }
        updateUI(favoriteStores);
    }
}