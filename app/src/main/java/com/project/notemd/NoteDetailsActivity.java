package com.project.notemd;

import android.os.Bundle;
import android.webkit.WebView;
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
        WebView markdownView = findViewById(R.id.markdownView);

        Note note = notesService.getNoteById(noteId);

        if (note != null) {
            titleView.setText(note.getTitle());

            String html = "<html><head>" +
                    "<style>" +
                    "body { font-size: 17px; line-height: 1.6; }" +
                    "code { background:#eee; padding:4px; border-radius:4px; }" +
                    "h1,h2,h3 { margin-top:20px; }" +
                    "</style>" +
                    "</head><body>" +
                    markdownToHtml(note.getContent()) +
                    "</body></html>";

            markdownView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    }

    private String markdownToHtml(String markdown) {
        markdown = markdown.replaceAll("(?m)^### (.+)$", "<h3>$1</h3>");
        markdown = markdown.replaceAll("(?m)^## (.+)$", "<h2>$1</h2>");
        markdown = markdown.replaceAll("(?m)^# (.+)$", "<h1>$1</h1>");

        markdown = markdown.replaceAll("\\*\\*(.+?)\\*\\*", "<b>$1</b>");
        markdown = markdown.replaceAll("\\*(.+?)\\*", "<i>$1</i>");

        markdown = markdown.replaceAll("`([^`]+)`", "<code>$1</code>");

        markdown = markdown.replaceAll("(?m)^- (.+)$", "<li>$1</li>");
        markdown = markdown.replaceAll("(<li>.*</li>)", "<ul>$1</ul>");

        markdown = markdown.replace("\n", "<br>");

        return markdown;
    }
}
