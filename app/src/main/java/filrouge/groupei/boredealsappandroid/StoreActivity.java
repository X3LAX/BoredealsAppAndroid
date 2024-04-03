package filrouge.groupei.boredealsappandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList = new ArrayList<>();

//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference storeRef = db.collection("stores");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);
    }
}
