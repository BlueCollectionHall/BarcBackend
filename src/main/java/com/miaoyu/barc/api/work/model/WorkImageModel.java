package com.miaoyu.barc.api.work.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkImageModel {
    private String id;
    private String work_id;
    private Integer sort;
    private String image_name;
    private String image_url;
    private LocalDateTime created_at;
}
