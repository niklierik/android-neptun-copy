package me.eriknikli.neptuncopy.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.fragments.NavFragment;
import me.eriknikli.neptuncopy.models.User;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onStart() {
        super.onStart();
        NavFragment.init(this, auth, db);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        User.getLatest(auth, db, (u) -> {
            if (u == null) {
                setIntent(new Intent(this, MainActivity.class));
            }
        });
        setContentView(R.layout.activity_home);

    }
}