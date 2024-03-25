package filrouge.groupei.boredealsappandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Firebase firestore;

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alex");
        user.put("last", "Boretti");
        user.put("description", "Je suis un étudiant en informatique");


        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));


        storeList = generateSampleData();
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                List<Store> filteredStores = new ArrayList<>();
                for (Store store : storeList) {
                    if (store.getDiscountPercentage() >= progress) {
                        filteredStores.add(store);
                    }
                }
                storeAdapter.setData(filteredStores);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }
        });

    }

    private List<Store> generateSampleData() {
        List<Store> stores = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.characters);
        String[] descriptions = getResources().getStringArray(R.array.description);
        Random random = new Random();

        for (int i = 0; i < names.length; i++) {
            int discountPercentage = random.nextInt(41);
            stores.add(new Store(names[i], descriptions[i], discountPercentage));
        }

        return stores;
    }

}