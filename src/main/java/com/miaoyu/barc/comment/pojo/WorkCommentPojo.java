package com.miaoyu.barc.comment.pojo;

import com.miaoyu.barc.comment.model.WorkCommentReplyModel;

import java.time.LocalDateTime;
import java.util.List;

public class WorkCommentPojo {
    private String id;
    private String work_id;
    private String author;
    private String content;
    private LocalDateTime created_at;
    private List<WorkCommentReplyModel> replies;

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

    public List<WorkCommentReplyModel> getReplies() {
        return replies;
    }

    public void setReplies(List<WorkCommentReplyModel> replies) {
        this.replies = replies;
    }
}
