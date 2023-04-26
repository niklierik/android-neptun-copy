package me.eriknikli.neptuncopy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.utils.ErrorHandling;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, btnToRegister;
    EditText email, password;
    TextView errorText;

    private FirebaseAuth mAuth;

    private void moveToMain() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(l -> {
            if (l.getCurrentUser() != null) {
                moveToMain();
            }
        });
    }

    private void setBtnsEnabled(boolean value) {
        loginBtn.setEnabled(value);
        btnToRegister.setEnabled(value);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
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
                setBtnsEnabled(true);
                moveToMain();
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
    }
}