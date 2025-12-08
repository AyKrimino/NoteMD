package com.project.notemd;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.project.notemd.db.NotesService;

public class CreateNoteActivity extends AppCompatActivity {

    private int userId;
    private NotesService notesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        userId = getIntent().getIntExtra("user_id", -1);
        notesService = new NotesService(this);

        EditText titleInput = findViewById(R.id.noteTitle);
        EditText contentInput = findViewById(R.id.noteContent);
        Button saveBtn = findViewById(R.id.saveNoteBtn);

        saveBtn.setOnClickListener(v -> {
            String t = titleInput.getText().toString();
            String c = contentInput.getText().toString();

            notesService.createNote(userId, t, c);
            finish();
        });
    }
}
