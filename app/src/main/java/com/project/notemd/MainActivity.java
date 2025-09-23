package com.project.notemd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Handler handler;
    private int progress = 0;
    private Runnable progressRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progress);

        handler = new Handler();

        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (progress < 100) {
                    progress++;
                    progressBar.setProgress(progress);
                    handler.postDelayed(this, 200);
                }
            }
        };

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Login successfully!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SuccessActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(progressRunnable, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(progressRunnable);
    }
}
