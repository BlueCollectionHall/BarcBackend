package com.miaoyu.barc.comment.model;

import java.time.LocalDateTime;

public class WorkCommentModel {
    private String id;
    private String work_id;
    private String author;
    private String content;
    private LocalDateTime created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
