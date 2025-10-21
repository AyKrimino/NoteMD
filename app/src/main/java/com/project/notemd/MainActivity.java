package com.project.notemd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
    private Button loginButton, signupButton;
    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password"
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("password", password);

                Toast.makeText(MainActivity.this, "Login successfully!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        signupButton = findViewById(R.id.goToSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Signup successfully!",
                        Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this, SignupActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString("message", "test");
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String registerEmail = bundle.getString("register_email");
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

            if (registerEmail != null) {
                emailInput.setText(registerEmail);
            }
        }
    }
}
