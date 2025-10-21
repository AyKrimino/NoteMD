package com.project.notemd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.notemd.db.AuthService;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput, confirmPasswordInput;
    private Button signupButton;
    private AuthService authService;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String msg = data.getString("message");
        Log.d(TAG, "*********" + msg);

        authService = new AuthService(this);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.passwordConfirm);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                Log.d(TAG, "onClick: Email = " + email);
                data.putString("register_email", email);
                intent.putExtras(data);
                setResult(RESULT_OK, intent);

                // TODO: check email format and other fields constraints.
                boolean ok = authService.register(email, password);

                if (!ok) {
                    Toast.makeText(SignupActivity.this, "Registration failed (maybe " +
                                    "email exists)",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Registered — please login",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
