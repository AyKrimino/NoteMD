package com.project.notemd.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    private final DBHelper dbHelper;

    public AuthService(Context context) {
        this.dbHelper = new DBHelper(context.getApplicationContext());
    }

    public boolean register(String email, String password) {
        if (getUserByEmail(email) != null) return false;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String hashedPassword = hashPassword(password);

            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password_hash", hashedPassword);

            int id = (int) db.insert("users", null, contentValues);
            db.close();

            return id != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, password_hash FROM users WHERE " +
                        "email =" +
                        " ?",
                new String[]{email});

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String hashedPassword = cursor.getString(1);
            user = new User(id, email, hashedPassword);
            cursor.close();
        }

        db.close();
        return user;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b: hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
