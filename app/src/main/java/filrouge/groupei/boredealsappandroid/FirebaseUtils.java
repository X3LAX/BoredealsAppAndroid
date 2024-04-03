package filrouge.groupei.boredealsappandroid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FirebaseUtils {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public FirebaseUtils() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("favorite_stores");
    }

    public void addStoreToFavorites(String storeId) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase.child(userId).child(storeId).setValue(true);
        }
    }

    public void removeStoreFromFavorites(String storeId) {
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase.child(userId).child(storeId).removeValue();
        }
    }
}
