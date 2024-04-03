package filrouge.groupei.boredealsappandroid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Cette classe fournit des utilitaires pour interagir avec Firebase, notamment pour gérer les magasins favoris.
 */
public class FirebaseUtils {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    /**
     * Constructeur de la classe FirebaseUtils.
     * Initialise l'instance de FirebaseAuth et la référence à la base de données Firebase.
     */
    public FirebaseUtils() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("favorite_stores");
    }

    /**
     * Ajoute un magasin aux favoris de l'utilisateur actuellement connecté.
     *
     * @param storeId L'identifiant du magasin à ajouter aux favoris.
     */
    public void addStoreToFavorites(String storeId) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase.child(userId).child(storeId).setValue(true);
        }
    }

    /**
     * Supprime un magasin des favoris de l'utilisateur actuellement connecté.
     *
     * @param storeId L'identifiant du magasin à supprimer des favoris.
     */
    public void removeStoreFromFavorites(String storeId) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase.child(userId).child(storeId).removeValue();
        }
    }
}
