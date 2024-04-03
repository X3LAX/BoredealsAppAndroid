package filrouge.groupei.boredealsappandroid;

import androidx.annotation.NonNull;
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

/**
 * Activité de connexion utilisateur.
 */
public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(view -> {
            if (!validateUsername() | !validatePassword()) {
                // La validation a échoué, gérer l'erreur
            } else {
                loginUser();
            }
        });

        signupRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Valide le champ du nom d'utilisateur.
     * @return true si le champ est valide, false sinon.
     */
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    /**
     * Valide le champ du mot de passe.
     * @return true si le champ est valide, false sinon.
     */
    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    /**
     * Authentifie l'utilisateur avec l'adresse e-mail et le mot de passe fournis.
     */
    private void loginUser() {
        String email = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Connexion réussie, mise à jour de l'interface utilisateur avec les informations de l'utilisateur connecté
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(LoginActivity.this, "Authentication success.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Si la connexion échoue, afficher un message à l'utilisateur.
                Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
                loginPassword.setError("Invalid Credentials");
                loginPassword.requestFocus();
            }
        });
    }
}
