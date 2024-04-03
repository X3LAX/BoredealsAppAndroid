package filrouge.groupei.boredealsappandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView favRecyclerView;
    private StoreAdapter favoriteAdapter;
    private List<Store> favoriteList = new ArrayList<>();
//    private DatabaseReference favoriteRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

//        favRecyclerView = findViewById(R.id.fav_recycler_view);
//        favRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        favoriteAdapter = new StoreAdapter(favoriteList);
//        favRecyclerView.setAdapter(favoriteAdapter);

//        favoriteRef = FirebaseDatabase.getInstance().getReference("favorites");

//        favoriteRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                favoriteList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Store store = snapshot.getValue(Store.class);
//                    favoriteList.add(store);
//                }
//                favoriteAdapter.notifyDataSetChanged();
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }
}
