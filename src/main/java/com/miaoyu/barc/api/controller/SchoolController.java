package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiPath
public class SchoolController {
    /**获取所有school实体
     * @return List类型包含所有school实体*/
    @IgnoreAuth
    @GetMapping("/schools_all")
    public ResponseEntity<J> getAllSchoolsControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**
     * 根据school_id获得唯一符合的school实体
     * @param schoolId school的id
     * @return 唯一的school实体*/
    @IgnoreAuth
    @GetMapping("/school_only")
    public ResponseEntity<J> getSchoolByIdControl(
            @RequestParam("school_id") String schoolId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**上传school参数
     * @param upType 上传方式"upload"/"update"
     * @param request school的实体
     * @return 修改是否正确完成*/
    @PostMapping("/up_school")
    public ResponseEntity<J> upSchoolControl(
            @RequestParam("up_type") String upType,
            @RequestBody J request) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }
}
