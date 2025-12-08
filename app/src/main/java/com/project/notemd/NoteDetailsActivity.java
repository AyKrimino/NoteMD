package com.project.notemd;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.notemd.db.Note;
import com.project.notemd.db.NotesService;

public class NoteDetailsActivity extends AppCompatActivity {
    private NotesService notesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        notesService = new NotesService(this);

        int noteId = getIntent().getIntExtra("note_id", -1);

        TextView titleView = findViewById(R.id.noteTitle);
        TextView contentView = findViewById(R.id.noteContent);

        Note note = notesService.getNoteById(noteId);

        if (note != null) {
            titleView.setText(note.getTitle());
            contentView.setText(note.getContent());
        }
    }
}
