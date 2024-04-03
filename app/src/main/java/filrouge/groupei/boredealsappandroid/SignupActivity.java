package filrouge.groupei.boredealsappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activité de création de compte utilisateur.
 */
public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseAuth mAuth; // Référence d'authentification Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> createUser());

        loginRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Crée un nouvel utilisateur avec les informations fournies.
     */
    private void createUser() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String name = signupName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Inscription réussie, mise à jour de l'interface utilisateur avec les informations de l'utilisateur inscrit
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Registration successful.",
                                    Toast.LENGTH_SHORT).show();

                            // Optionnel: Enregistre des champs supplémentaires dans la base de données en temps réel
                            saveAdditionalUserData(user, name);

                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Si l'inscription échoue, afficher un message à l'utilisateur.
                            Toast.makeText(SignupActivity.this, "Registration failed: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**
     * Enregistre des données utilisateur supplémentaires dans la base de données en temps réel.
     * @param user L'utilisateur Firebase actuel.
     * @param name Le nom de l'utilisateur.
     */
    private void saveAdditionalUserData(FirebaseUser user, String name) {
        if (user != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            HelperClass helperClass = new HelperClass(name, user.getEmail(), user.getUid()); // En supposant que HelperClass est ajusté pour Firebase Auth
            databaseReference.child(user.getUid()).setValue(helperClass);
        }
    }
}
