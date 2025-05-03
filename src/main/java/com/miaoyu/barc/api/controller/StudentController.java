package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPath
@RequestMapping("/student")
public class StudentController {

    /**获取全部BA中的student实体
     * @return List类型中包含全部的student实体*/
    @GetMapping("/all")
    public ResponseEntity<J> getAllStudentsControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }

    /**根据school_id获取符合条件的所有student实体
     * @param schoolId school的id
     * @return List类型中包含所有符合条件的student实体*/
    @GetMapping("/by_school")
    public ResponseEntity<J> getStudentsBySchoolControl(
            @RequestParam("school_id") String schoolId
    ) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
    }
}
