package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.api.model.WorkModel;
import com.miaoyu.barc.api.service.WorkService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work")
public class WorkController {
    @Autowired
    private WorkService workService;

    /**获取全部Work实体
     * @return List类型中包含所有Work实体*/
    @IgnoreAuth
    @GetMapping("/all")
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
    /**根据已登录的用户UUID获取符合发布者条件的work实体
     * @return List类型中包含所有符合条件的work实体*/
    @GetMapping("/works_by_me")
    public ResponseEntity<J> getWorksByMeControl(HttpServletRequest request) {
        return workService.getWorksByMeService(request.getAttribute("uuid").toString());
    }
    /**根据已知UUID获取符合发布者条件的work实体
     * @param uuid uuid
     * @return List类型中包含所有符合条件的work实体*/
    @IgnoreAuth
    @GetMapping("/works_by_uuid")
    public ResponseEntity<J> getWorksByUuidControl(@RequestParam("uuid") String uuid) {
        return workService.getWorksByMeService(uuid);
    }
    /**根据work_id获取唯一符合条件的work实体
     * @param workId work_id
     * @return 唯一符合条件的work实体*/
    @IgnoreAuth
    @GetMapping("/only")
    public ResponseEntity<J> getWorkOnlyControl(
            @RequestParam("work_id") String workId
    ) {
        return workService.getWorksByIdService(workId);
    }
    /**上传作品
     * @param requestModel 作品的实体
     * @return 上传是否成功*/
    @PostMapping("/upload")
    public ResponseEntity<J> uploadWorkControl(
            HttpServletRequest request,
            @RequestBody WorkModel requestModel
            ) {
        return workService.uploadWorkService(request.getAttribute("uuid").toString(), requestModel);
    }
    /**修改作品（兼容维护者及以上维护性修改）
     * @param requestModel 作品的实体
     * @return 上传是否成功*/
    @PutMapping("/update")
    public ResponseEntity<J> updateWorkControl(
            HttpServletRequest request,
            @RequestBody WorkModel requestModel
    ) {
        return workService.updateWorkService(request.getAttribute("uuid").toString(), requestModel);
    }

    /**删除作品
     * @param workId 作品ID
     * @return 删除是否成功*/
    @DeleteMapping("/delete")
    public ResponseEntity<J> deleteWorkControl(@RequestParam("work_id") String workId) {
        return null;
    }
}
