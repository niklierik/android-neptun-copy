package me.eriknikli.neptuncopy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.fragments.NavFragment;
import me.eriknikli.neptuncopy.models.User;

public class MarksActivity extends AppCompatActivity {

    TextView text;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        auth.addAuthStateListener(l -> {
            User.getLatest(auth, db, u -> {
                if (u != null) {
                    text.setText(u.getIsTeacher() ? "Jegybeírás" : "Jegyeim");
                    NavFragment.init(this, auth, db);
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
            });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        text = findViewById(R.id.marksTitle);

    }
}