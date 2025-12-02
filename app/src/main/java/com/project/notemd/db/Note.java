package com.project.notemd.db;

public class Note {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    public Note(int id, int userId, String title, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
