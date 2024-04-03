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

/**
 * Cette classe représente l'activité principale de l'application où les utilisateurs peuvent afficher
 * une liste de magasins disponibles.
 */

public class StoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList = new ArrayList<>();

    /**
     * Méthode appelée lors de la création de l'activité.
     * @param savedInstanceState L'état enregistré précédemment de l'activité.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration du RecyclerView pour afficher la liste des magasins
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Création de l'adaptateur pour le RecyclerView
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);
    }
}
