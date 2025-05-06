package com.miaoyu.barc.api.service;

import com.miaoyu.barc.api.mapper.CategoryMapper;
import com.miaoyu.barc.api.model.data.CategoryData;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public ResponseEntity<J> getCategoriesAllService() {
        List<CategoryData> allCategories = categoryMapper.selectAll();
        // 创建ID到分类映射
        Map<String, CategoryData> categoryMap = new HashMap<>();
        // 找出所有顶级分类并映射
        List<CategoryData> rootCategories = new ArrayList<>();
        for (CategoryData cat : allCategories) {
            categoryMap.put(cat.getId(), cat);
            if (cat.getLevel() == 1) {
                rootCategories.add(cat);
            }
        }
        // 建立父子关系
        for (CategoryData cat : allCategories) {
            if (cat.getParent_id() != null && !cat.getParent_id().isEmpty()) {
                CategoryData parent = categoryMap.get(cat.getParent_id());
                if (parent != null) {
                    parent.addChild(cat);
                }
            }
        }
        // 按sort字段排序
        sortCategories(rootCategories);

        return ResponseEntity.ok(new ResourceR().resourceSuch(true, rootCategories));
    }
    private void sortCategories(List<CategoryData> categories) {
        if (categories == null) return;
        // 排序当前层级
        categories.sort(Comparator.comparingInt(CategoryData::getSort));
        // 递归排序子层级
        for (CategoryData cat : categories) {
            if (cat.getChildren() != null) {
                sortCategories(cat.getChildren());
            }
        }
    }
}
