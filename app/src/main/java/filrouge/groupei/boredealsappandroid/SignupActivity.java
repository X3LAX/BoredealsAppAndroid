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

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseAuth mAuth; // Firebase Authentication reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
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
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Registration successful.",
                                    Toast.LENGTH_SHORT).show();

                            // Optional: Save additional fields in Realtime Database
                            saveAdditionalUserData(user, name);

                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Registration failed: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveAdditionalUserData(FirebaseUser user, String name) {
        if (user != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            HelperClass helperClass = new HelperClass(name, user.getEmail(), user.getUid()); // Assuming HelperClass is adjusted for Firebase Auth
            databaseReference.child(user.getUid()).setValue(helperClass);
        }
    }
}
