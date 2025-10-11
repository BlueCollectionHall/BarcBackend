package com.miaoyu.barc.api.work.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkCoverImageModel {
    private String id;
    private String work_id;
    private String image_name;
    private String image_url;
    private LocalDateTime created_at;
}
