package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiPath
public class StudentController {

    /**获取全部BA中的student实体
     * @return List类型中包含全部的student实体*/
    @IgnoreAuth
    @GetMapping("/students_all")
    public ResponseEntity<J> getAllStudentsControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**根据school_id获取符合条件的所有student实体
     * @param schoolId school的id
     * @return List类型中包含所有符合条件的student实体*/
    @IgnoreAuth
    @GetMapping("/students_by_school")
    public ResponseEntity<J> getStudentsBySchoolControl(
            @RequestParam("school_id") String schoolId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**根据club_id获取符合条件的所有student实体
     * @param clubId club的id
     * @return List类型中包含所有符合条件的student实体*/
    @IgnoreAuth
    @GetMapping("/students_by_club")
    public ResponseEntity<J> getStudentsByClubControl(
            @RequestParam("club_id") String clubId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**根据student_id获取唯一符合的student实体
     * @param studentId student的id
     * @return 唯一的student实体*/
    @IgnoreAuth
    @GetMapping("/student_only")
    public ResponseEntity<J> getStudentByIdControl(
            @RequestParam("student_id") String studentId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**上传student参数
     * @param upType 上传方式"upload"/"update"
     * @param request school的实体
     * @return 修改是否正确完成*/
    @PostMapping("/up_student")
    public ResponseEntity<J> upStudentControl(
            @RequestParam("up_type") String upType,
            @RequestBody J request
    ) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }
}
