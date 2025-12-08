package com.project.notemd;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.notemd.db.AuthService;
import com.project.notemd.db.User;

public class SuccessActivity extends AppCompatActivity {
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        authService = new AuthService(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        User user = null;

        String email = null;
        if (bundle != null) {
            email = bundle.getString("email");
            user = authService.getUserByEmail(email);
        }

        TextView textView = findViewById(R.id.welcomeText);
        if (textView != null) {
            if (email != null && !email.isEmpty()) {
                textView.setText("Welcome, " + email + "!");
            } else {
                textView.setText("Welcome!");
            }
        }

        if (user != null) {
            bundle.putInt("user_id", user.getId());
        }

        Button goToNotesBtn = findViewById(R.id.goToNotesBtn);
        goToNotesBtn.setOnClickListener(v -> {
            Intent notesIntent = new Intent(SuccessActivity.this, NotesActivity.class);
            notesIntent.putExtras(bundle);
            startActivity(notesIntent);
        });
    }
}
