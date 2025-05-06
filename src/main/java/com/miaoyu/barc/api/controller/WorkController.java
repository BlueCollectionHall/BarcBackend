package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.api.service.WorkService;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPath
public class WorkController {
    @Autowired
    private WorkService workService;

    /**获取全部Work实体
     * @return List类型中包含所有Work实体*/
    @IgnoreAuth
    @GetMapping("/works_all")
    public ResponseEntity<J> getWorksAllControl() {
        return workService.getWorksAllService();
    }
    /**根据层级分类id获取所有符合条件的work实体
     * @param categoryId 层级分类id
     * @return List类型中包含所有work实体*/
    @IgnoreAuth
    @GetMapping("/works_by_category")
    public ResponseEntity<J> getWorksByCategoryControl(
            @RequestParam("category_id") String categoryId
    ) {
        return workService.getWorksByCategoryService(categoryId);
    }
    /**根据school_id获取所有符合条件的work实体
     * @param schoolId school_id
     * @return List类型中包含所有符合要求的work实体*/
    @IgnoreAuth
    @GetMapping("/works_by_school")
    public ResponseEntity<J> getWorksBySchoolControl(
            @RequestParam("school_id") String schoolId
    ) {
        return workService.getWorksBySchoolService(schoolId);
    }
    /**根据club_id获取所有符合条件的work实体
     * @param clubId club_id
     * @return List类型中包含所有符合要求的work实体*/
    @IgnoreAuth
    @GetMapping("/works_by_club")
    public ResponseEntity<J> getWorksByClubControl(
            @RequestParam("club_id") String clubId
    ) {
        return workService.getWorksByClubService(clubId);
    }
    /**根据student_id获取所有符合条件的work实体
     * @param studentId student_id
     * @return List类型中包含所有符合要求的work实体*/
    @IgnoreAuth
    @GetMapping("/works_by_student")
    public ResponseEntity<J> getWorksByStudentControl(
            @RequestParam("student_id") String studentId
    ) {
        return workService.getWorksByStudentService(studentId);
    }
    /**根据work_id获取唯一符合条件的work实体
     * @param workId work_id
     * @return 唯一符合条件的work实体*/
    @IgnoreAuth
    @GetMapping("/work_only")
    public ResponseEntity<J> getWorkOnlyControl(
            @RequestParam("work_id") String workId
    ) {
        return workService.getWorksByIdService(workId);
    }
}
