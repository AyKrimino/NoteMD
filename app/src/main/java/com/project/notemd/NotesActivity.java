package com.project.notemd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.notemd.db.Note;
import com.project.notemd.db.NotesService;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private NotesService notesService;
    private int userId;
    private ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesService = new NotesService(this);

        userId = getIntent().getIntExtra("user_id", -1);

        notesList = findViewById(R.id.notesList);
        Button addBtn = findViewById(R.id.addNoteBtn);

        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(NotesActivity.this, CreateNoteActivity.class);
            i.putExtra("user_id", userId);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        List<Note> list = notesService.getNotes(userId);
        List<String> titles = new ArrayList<>();

        for (Note n : list) titles.add(n.getTitle());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);

        notesList.setAdapter(adapter);
    }
}
