package com.miaoyu.barc.feedback.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class FeedbackImageModel {
    private String id;
    private String parent_id;
    private String content;
    private LocalDateTime created_at;

}
