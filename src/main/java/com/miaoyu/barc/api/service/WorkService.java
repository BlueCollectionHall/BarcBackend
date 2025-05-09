package com.miaoyu.barc.api.service;

import com.miaoyu.barc.api.mapper.WorkMapper;
import com.miaoyu.barc.api.model.WorkModel;
import com.miaoyu.barc.api.model.entity.WorkEntity;
import com.miaoyu.barc.permission.ComparePermission;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.utils.GenerateUUID;
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
    @Autowired
    private UserArchiveMapper userArchiveMapper;

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
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (Objects.isNull(userArchive)) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (!Objects.isNull(requestModel.getId())) {
            WorkModel work = workMapper.selectById(requestModel.getId());
            if (!Objects.isNull(work)) {
                return ResponseEntity.ok(new ErrorR().normal("作品ID已被其他作品使用！"));
            }
        }
        requestModel.setId(new GenerateUUID().getUuid36l());
        if (!requestModel.getIs_claim()) {
            requestModel.setAuthor(uuid);
            if (Objects.isNull(requestModel.getAuthor_nickname())) {
                requestModel.setAuthor_nickname(userArchive.getNickname());
            }
        }
        boolean insert = workMapper.insert(requestModel);
        if (insert) {
            return ResponseEntity.ok(new ChangeR().udu(true, 1));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 1));
    }
    public ResponseEntity<J> updateWorkService(String uuid, WorkModel requestModel) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (Objects.isNull(userArchive)) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (!uuid.equals(requestModel.getAuthor())) {
            boolean compare = new ComparePermission().compare(userArchive.getPermission(), PermissionConst.FIR_MAINTAINER);
            if (!compare) {
                return ResponseEntity.ok(new UserR().insufficientAccountPermission());
            }
        }
        boolean b = workMapper.update(requestModel);
        if (b) {
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
}
