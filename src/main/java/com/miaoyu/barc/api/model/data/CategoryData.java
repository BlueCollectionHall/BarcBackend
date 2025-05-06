package com.miaoyu.barc.api.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CategoryData {
    private String id;
    private String parent_id;
    private String name;
    private Integer level;
    private Integer sort;
    private Boolean is_enabled;
    private String icon;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private List<CategoryData> children; // 子分类列表

    public void addChild(CategoryData child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

}