package com.miaoyu.barc.api.work.service;

import com.miaoyu.barc.annotation.RequireSelfOrPermissionAnno;
import com.miaoyu.barc.annotation.RequireUserAndPermissionAnno;
import com.miaoyu.barc.api.mapper.StudentMapper;
import com.miaoyu.barc.api.model.StudentModel;
import com.miaoyu.barc.api.work.enumeration.WorkStatusEnum;
import com.miaoyu.barc.api.work.mapper.WorkImageMapper;
import com.miaoyu.barc.api.work.mapper.WorkMapper;
import com.miaoyu.barc.api.work.model.WorkImageModel;
import com.miaoyu.barc.api.work.model.WorkModel;
import com.miaoyu.barc.api.work.model.entity.WorkEntity;
import com.miaoyu.barc.permission.ComparePermission;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.utils.GenerateUUID;
import com.miaoyu.barc.utils.J;
import com.miaoyu.barc.utils.minio.MinioObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class WorkService {
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private MinioObjects minioObjects;
    @Autowired
    private WorkImageMapper workImageMapper;

    public ResponseEntity<J> getNewWorkService(int day, boolean isStudentList) {
        List<WorkEntity> works = workMapper.selectByDay(day, WorkStatusEnum.PUBLIC);
        if (isStudentList) {
            List<StudentModel> students = works.stream()
                    .map(WorkEntity::getStudent)
                    .distinct()
                    .map(studentMapper::selectById)
                    .filter(Objects::nonNull)
                    .toList();
            return ResponseEntity.ok(new ResourceR().resourceSuch(true, students));
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, works));
    }

    public ResponseEntity<J> getWorksAllService(WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectAll(statusEnum)));
    }

    public ResponseEntity<J> getWorksByCategoryService(String categoryId, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByCategoryId(categoryId, statusEnum)));
    }

    public ResponseEntity<J> getWorksBySchoolService(String schoolId, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectBySchoolId(schoolId, statusEnum)));
    }

    public ResponseEntity<J> getWorksByClubService(String clubId, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByClubId(clubId, statusEnum)));
    }

    public ResponseEntity<J> getWorksByStudentService(String studentId, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByStudentId(studentId, statusEnum)));
    }
    public ResponseEntity<J> getWorksByMeService(String uuid, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByUuid(uuid, statusEnum)));
    }
    public ResponseEntity<J> getWorksByUuidService(String uuid, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByUuid(uuid, statusEnum)));
    }
    public ResponseEntity<J> getWorksByUsernameService(String username, WorkStatusEnum statusEnum) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workMapper.selectByUsername(username, statusEnum)));
    }
    public ResponseEntity<J> getWorksByIdService(String workId) {
        WorkModel work = workMapper.selectById(workId);
        if (Objects.isNull(work)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        return switch (work.getStatus()) {
            case PRIVATE -> ResponseEntity.ok(new ErrorR().normal("私有作品无法访问"));
            case BAN -> ResponseEntity.ok(new ErrorR().normal("作品已被封禁"));
            case OFF -> ResponseEntity.ok(new ErrorR().normal("作品已被下架"));
            default -> ResponseEntity.ok(new ResourceR().resourceSuch(true, work));
        };
    }
    public ResponseEntity<J> getWorkByIdWithMeService(String uuid, String workId) {
        WorkModel work = workMapper.selectById(workId);
        if (Objects.isNull(work)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        if (work.getAuthor().equals(uuid) || work.getUploader().equals(uuid)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(true, work));
        }
        return ResponseEntity.ok(new UserR().uuidMismatch());
    }
    @Transactional
    @RequireUserAndPermissionAnno({@RequireUserAndPermissionAnno.Check()})
    public ResponseEntity<J> uploadWorkService(String uuid, WorkModel requestModel, MultipartFile[] files) {
        WorkModel tempWork;
        // 不存在ID，需要自动生成ID
        if (requestModel.getId() == null || requestModel.getId().isEmpty()) {
            do {
                requestModel.setId(new GenerateUUID().getUuid36l());
                tempWork = workMapper.selectById(requestModel.getId());
            } while (tempWork != null);
        }
        // 存在用户自定义的ID
        else {
            tempWork = workMapper.selectById(requestModel.getId());
            if (tempWork != null) return ResponseEntity.ok(new ErrorR().normal("您的自定义ID号已经冲突！"));
        }
        // 作品是号主自有
        if (requestModel.getIs_claim()) requestModel.setAuthor(uuid);
        // 作品是搬运收录
        else {
            if (requestModel.getAuthor_nickname() == null)
                return ResponseEntity.ok(new ErrorR().normal("搬运收录的作品，请标注该作品作者在其他平台常用昵称！"));
            requestModel.setUploader(uuid);
            requestModel.setAuthor("707B0FBF6AAA35B788069B07AEFEA12B");
        }
        boolean insert = workMapper.insert(requestModel);
        if (insert) {
            try {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    String finalFileName = requestModel.getId() + "_" + file.getOriginalFilename();
                    WorkImageModel workImage = new WorkImageModel();
                    workImage.setId(new GenerateUUID().getUuid36l());
                    workImage.setSort(i);
                    workImage.setWork_id(requestModel.getId());
                    workImage.setImage_name(file.getOriginalFilename());
                    String imageUrl = minioObjects.putObject("barctemp", "uploads/" + finalFileName, file);
                    if (imageUrl == null) continue;
                    workImage.setImage_url(imageUrl);
                    workImageMapper.insert(workImage);
                    return ResponseEntity.ok(new ChangeR().udu(true, 1));
                }
            } catch (Exception e) {
                return ResponseEntity.ok(new ChangeR().udu(false, 1));
            }
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 1));
    }
    @RequireSelfOrPermissionAnno(identity = UserIdentityEnum.MANAGER, targetPermission = PermissionConst.SEC_MAINTAINER, isHasElseUpper = true)
    public ResponseEntity<J> updateWorkService(String uuid, WorkModel requestModel) {
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
