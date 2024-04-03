package filrouge.groupei.boredealsappandroid;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;


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
     *
     * @param savedInstanceState L'état enregistré précédemment de l'activité.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference favoriteStoresRef = FirebaseDatabase.getInstance().getReference().child("favorite_stores").child(currentUser.getUid());

            favoriteStoresRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Effacer la liste actuelle des magasins
                    storeList.clear();

                    // Parcourir chaque magasin favori dans la base de données
                    for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                        String storeId = storeSnapshot.getKey();

                        // Créer un objet Store avec l'ID du magasin favori et l'état favori
                        Store store = new Store();
                        store.setId(Integer.parseInt(storeId));
                        store.setFavourite(true);

                        // Ajouter le magasin à la liste des magasins
                        storeList.add(store);
                    }

                    // Notifier l'adaptateur de la mise à jour des données
                    storeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Gérer les erreurs éventuelles
                    Log.e("StoreActivity", "Erreur lors de la récupération des magasins favoris", databaseError.toException());
                }
            });
        }
    }
}
