package me.eriknikli.neptuncopy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.activities.MainActivity;
import me.eriknikli.neptuncopy.models.User;
import me.eriknikli.neptuncopy.utils.DateHandler;
import me.eriknikli.neptuncopy.utils.ErrorHandling;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Button registerBtn, toLoginBtn;
    DatePicker birthdate;
    EditText email, password, passwordAgain, address, familyname, forename;
    CheckBox isTeacher;
    TextView errorText;

    private void moveToMain() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void buttonsEnabled(boolean value) {
        registerBtn.setEnabled(value);
        toLoginBtn.setEnabled(value);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(l -> {
            if (l.getCurrentUser() != null) {
                moveToMain();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.btnRegister);
        toLoginBtn = findViewById(R.id.toLoginBtn);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        passwordAgain = findViewById(R.id.editPasswordAgain);
        familyname = findViewById(R.id.editFamilyname);
        forename = findViewById(R.id.editForename);
        address = findViewById(R.id.editAddress);
        birthdate = findViewById(R.id.birthdate);
        isTeacher = findViewById(R.id.isTeacher);
        errorText = findViewById(R.id.register_errorText);

        registerBtn.setOnClickListener(l -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            String passwordAgain = this.passwordAgain.getText().toString();
            String familyname = this.familyname.getText().toString();
            String forename = this.forename.getText().toString();
            String address = this.address.getText().toString();
            if (email == null || email.isEmpty()) {
                errorText.setText("Nem adtál meg e-mail címet.");
                return;
            }
            if (password == null || password.isEmpty()) {
                errorText.setText("Nem adtál meg jelszót.");
                return;
            }
            if (passwordAgain == null || !passwordAgain.equals(password)) {
                errorText.setText("A két jelszó nem egyezik.");
                return;
            }
            if (familyname == null || familyname.isEmpty()) {
                errorText.setText("Nem adtad meg a vezetékneved.");
                return;
            }
            if (forename == null || forename.isEmpty()) {
                errorText.setText("Nem adtad meg a keresztneved.");
                return;
            }
            if (address == null || address.isEmpty()) {
                errorText.setText("Nem adtad meg a lakcímed.");
                return;
            }
            int day = birthdate.getDayOfMonth();
            int month = birthdate.getMonth();
            int year = birthdate.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatedDate = sdf.format(calendar.getTime());
            Date date = DateHandler.getDateFromString(formatedDate);
            buttonsEnabled(false);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(authResult -> {
                User user = new User(familyname, forename, date, address, email, isTeacher.isChecked());
                db.collection("users").add(user).addOnCompleteListener(task -> {
                    moveToMain();
                }).addOnFailureListener(e -> {
                    buttonsEnabled(true);
                    errorText.setText(e.getMessage());
                });
            }).addOnFailureListener(e -> {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    ErrorHandling.getErrorMessage((FirebaseAuthInvalidCredentialsException) e);
                    return;
                }
                buttonsEnabled(true);
            });

        });
        toLoginBtn.setOnClickListener(l -> {
            moveToLogin();
        });
    }
}