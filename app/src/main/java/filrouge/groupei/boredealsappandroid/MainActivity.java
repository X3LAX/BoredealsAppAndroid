package filrouge.groupei.boredealsappandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
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

    private static final int ANIMATION_INTERVAL = 3000;

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
        seekBar.setMax(20);
        TextView percentageTextView = findViewById(R.id.percentageTextView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percentage = progress * 5;
                percentageTextView.setText(String.valueOf(percentage) + "%");

                List<Store> filteredStores = new ArrayList<>();
                for (Store store : storeList) {
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

        // Pour l'animation
        ImageView bellImageView = findViewById(R.id.bell);
        Animation rotateAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_bell);

        Handler handler = new Handler();
        Runnable animationRunnable = new Runnable() {
            @Override
            public void run() {
                bellImageView.startAnimation(rotateAnimation);
                handler.postDelayed(this, ANIMATION_INTERVAL);
            }
        };

        handler.postDelayed(animationRunnable, ANIMATION_INTERVAL);

    }

    private int parsePercentage(String percentageString) {
        return Integer.parseInt(percentageString.replace("%", ""));
    }

    private List<Store> generateSampleData() {
        List<Store> stores = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.characters);
        String[] descriptions = getResources().getStringArray(R.array.description);
        String[] percentages = getResources().getStringArray(R.array.discount_percentages);

        if (names.length != descriptions.length || names.length != percentages.length) {
            return stores;
        }

        for (int i = 0; i < names.length; i++) {
            stores.add(new Store(names[i], descriptions[i], parsePercentage(percentages[i])));
        }

        return stores;
    }
}
