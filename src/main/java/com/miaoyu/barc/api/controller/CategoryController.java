package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.api.service.CategoryService;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPath
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**获取全部分类及其子分类
     * @return List类型中父级分类及其包含List类型中后代分类*/
    @IgnoreAuth
    @GetMapping("/categories_all")
    public ResponseEntity<J> getCategoriesAllControl() {
        return categoryService.getCategoriesAllService();
    }
}
