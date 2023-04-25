package me.eriknikli.neptuncopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private void moveToMain() {
        // TODO
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            moveToMain();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.login_btnToRegister);
        btn.setOnClickListener((listener) -> {
            Intent switchToRegister = new Intent(this, RegisterActivity.class);
            startActivity(switchToRegister);
        });

        EditText email = findViewById(R.id.editTextTextEmailAddress3);
        EditText password = findViewById(R.id.editTextTextPassword2);

        btn = findViewById(R.id.login_btnLogin);
        btn.setOnClickListener((listener) -> {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener((authResult) -> {
                // then
                moveToMain();
            }).addOnFailureListener((authResult) -> {
                // catch
                String msg = authResult.getMessage();

            });
        });
    }
}