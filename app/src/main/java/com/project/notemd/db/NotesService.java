package com.project.notemd.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotesService {
    private final DBHelper dbHelper;

    public NotesService(Context context) {
        this.dbHelper = new DBHelper(context.getApplicationContext());
    }

    public long createNote(int userId, String title, String content) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("title", title);
        cv.put("content", content);
        cv.put("created_at", String.valueOf(System.currentTimeMillis()));
        cv.put("updated_at", String.valueOf(System.currentTimeMillis()));

        long id = db.insert("notes", null, cv);
        db.close();
        return id;
    }

    public List<Note> getNotes(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Note> res = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM notes WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (c != null) {
            while (c.moveToNext()) {
                Note note = new Note(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getInt(c.getColumnIndexOrThrow("user_id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("content")),
                        c.getString(c.getColumnIndexOrThrow("created_at")),
                        c.getString(c.getColumnIndexOrThrow("updated_at"))
                );
                res.add(note);
            }
            c.close();
        }
        db.close();
        return res;
    }

    public void updateNote(int id, String title, String content) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        cv.put("updated_at", String.valueOf(System.currentTimeMillis()));

        db.update("notes", cv, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM notes WHERE id = ?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            Note note = new Note(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("user_id")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("content")),
                    c.getString(c.getColumnIndexOrThrow("created_at")),
                    c.getString(c.getColumnIndexOrThrow("updated_at"))
            );
            c.close();
            db.close();
            return note;
        }

        c.close();
        db.close();
        return null;
    }
}
