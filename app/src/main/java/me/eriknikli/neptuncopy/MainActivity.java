package me.eriknikli.neptuncopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.login_btnToRegister);
        btn.setOnClickListener((listener) -> {
            Intent switchToRegister = new Intent(this, RegisterActivity.class);
            startActivity(switchToRegister);
        });
    }
}