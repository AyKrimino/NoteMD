package com.project.notemd;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String email = bundle.getString("email");

        TextView textView = findViewById(R.id.welcomeText);
        if (textView != null) {
            textView.setText("Welcome, " + email + "!");
        } else {
            textView.setText("Welcome!");
        }
    }
}
