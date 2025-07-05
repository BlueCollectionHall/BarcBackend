package com.miaoyu.barc.api.work.service;

import com.miaoyu.barc.api.work.mapper.WorkClaimMapper;
import com.miaoyu.barc.api.work.mapper.WorkMapper;
import com.miaoyu.barc.api.work.model.WorkModel;
import com.miaoyu.barc.api.work.model.entity.WorkEntity;
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
    @Autowired
    private WorkClaimMapper workClaimMapper;

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
        if (Objects.isNull(requestModel.getId())) {
            requestModel.setId(new GenerateUUID().getUuid36l());
        } else {
            if (!Objects.isNull(workMapper.selectById(requestModel.getId()))) {
                return ResponseEntity.ok(new ErrorR().normal("作品ID已经被占用！"));
            }
        }
        // 未被认领
        if (!requestModel.getIs_claim()) {
            if (Objects.isNull(requestModel.getAuthor_nickname())) {
                return ResponseEntity.ok(new ErrorR().normal("您需要标明作品来源作者在其他平台的常用昵称！"));
            }
            if (Objects.isNull(requestModel.getUploader())) {
                return ResponseEntity.ok(new ErrorR().normal("待认领的作品必须填写上传者的UUID信息"));
            }
            if (!Objects.equals(requestModel.getUploader(), uuid)) {
                return ResponseEntity.ok(new ErrorR().normal("上传者UUID与已登录账号不一致"));
            }
            requestModel.setAuthor("707B0FBF6AAA35B788069B07AEFEA12B");
        }
        // 本人作品
        else {
            if (!requestModel.getAuthor().equals(uuid)) {
                return ResponseEntity.ok(new ErrorR().normal("作者UUID与已登录账号不一致"));
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

    public ResponseEntity<J> delectWorkService(String workId) {
        boolean delete = workMapper.delete(workId);
        if (delete) {
            return ResponseEntity.ok(new ChangeR().udu(true, 2));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 2));
    }
}
