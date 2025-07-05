package com.miaoyu.barc.api.work.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.api.work.model.WorkCategoryModel;
import com.miaoyu.barc.api.work.service.WorkCategoryService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work/category")
public class WorkCategoryController {
    private final WorkCategoryService workCategoryService;

    public WorkCategoryController(WorkCategoryService workCategoryService) {
        this.workCategoryService = workCategoryService;
    }

    @PostMapping("/record")
    public ResponseEntity<J> recordWorkAndCategoryControl(
            HttpServletRequest request, @RequestBody WorkCategoryModel requestModel
    ) {
        return workCategoryService.recordWorkAndCategoryService(request.getAttribute("uuid").toString(), requestModel);
    }

    @IgnoreAuth
    @GetMapping("/categories_by_work")
    public ResponseEntity<J> getCategoriesByWorkIdControl(@RequestParam("work_id") String workId) {
        return workCategoryService.getCategoriesByWorkIdService(workId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<J> deleteWorkCategoryControl(
            HttpServletRequest request,
            @RequestParam("id") String id
    ) {
        return workCategoryService.deleteWorkCategoryService(request.getAttribute("uuid").toString(), id);
    }
}
