package com.miaoyu.barc.api.service;

import com.miaoyu.barc.api.mapper.WorkMapper;
import com.miaoyu.barc.api.model.WorkModel;
import com.miaoyu.barc.api.model.entity.WorkEntity;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkService {
    @Autowired
    private WorkMapper workMapper;

    public ResponseEntity<J> getWorksAllService() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectAll()));
    }

    public ResponseEntity<J> getWorksByCategoryService(String categoryId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByCategoryId(categoryId)));
    }

    public ResponseEntity<J> getWorksBySchoolService(String schoolId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectBySchoolId(schoolId)));
    }

    public ResponseEntity<J> getWorksByClubService(String clubId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByClubId(clubId)));
    }

    public ResponseEntity<J> getWorksByStudentService(String studentId) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByStudentId(studentId)));
    }
    public ResponseEntity<J> getWorksByMeService(String uuid) {
        List<WorkEntity> works = workMapper.selectByUuid(uuid);
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, works));
    }
    public ResponseEntity<J> getWorksByIdService(String workId) {
        WorkModel work = workMapper.selectById(workId);
        if (Objects.isNull(work)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, work));
    }
    public ResponseEntity<J> uploadWorkService(String uuid, WorkModel requestModel) {
return null;
    }
}
