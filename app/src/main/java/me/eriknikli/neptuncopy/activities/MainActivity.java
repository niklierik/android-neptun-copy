package me.eriknikli.neptuncopy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.firestore.FirebaseFirestore;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.models.User;
import me.eriknikli.neptuncopy.utils.ErrorHandling;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, btnToRegister;
    EditText email, password;
    TextView errorText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    private boolean loading;

    private void moveToMain() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(l -> {
            if (l.getCurrentUser() != null) {
                User.getLatest(mAuth, mStore, u -> {
                    if (u != null) {
                        moveToMain();
                    }
                    setLoading(false);
                });
                return;
            }
            setLoading(false);
        });
    }

    private void setLoading(boolean value) {
        setBtnsEnabled(!value);
        email.setEnabled(!value);
        password.setEnabled(!value);
    }

    private void setBtnsEnabled(boolean value) {
        loginBtn.setEnabled(value);
        btnToRegister.setEnabled(value);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_main);

        btnToRegister = findViewById(R.id.login_btnToRegister);
        loginBtn = findViewById(R.id.login_btnLogin);
        btnToRegister.setOnClickListener((listener) -> {
            Intent switchToRegister = new Intent(this, RegisterActivity.class);
            startActivity(switchToRegister);
        });

        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.editTextTextPassword2);

        errorText = findViewById(R.id.errorText);

        loginBtn.setOnClickListener((listener) -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                errorText.setText("Nincs megadva e-mail cím vagy jelszó.");
                return;
            }
            setBtnsEnabled(false);
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener((authResult) -> {
                mStore.collection("users").document(authResult.getUser().getUid()).get().addOnSuccessListener(l -> {
                    User.getLatest(mAuth, mStore, u -> {
                        moveToMain();
                    });
                }).addOnFailureListener(e -> {
                    setBtnsEnabled(true);
                });
            }).addOnFailureListener((e) -> {
                setBtnsEnabled(true);
                String msg = e.getMessage();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    FirebaseAuthInvalidCredentialsException fe = (FirebaseAuthInvalidCredentialsException) e;
                    msg = ErrorHandling.getErrorMessage(fe);
                }
                errorText.setText(msg);
            });
        });
        setLoading(true);
    }
}