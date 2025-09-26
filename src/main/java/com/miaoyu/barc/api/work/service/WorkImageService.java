package com.miaoyu.barc.api.work.service;

import com.miaoyu.barc.annotation.RequireSelfOrPermissionAnno;
import com.miaoyu.barc.api.work.mapper.WorkImageMapper;
import com.miaoyu.barc.api.work.model.WorkImageModel;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkImageService {
    @Autowired
    private WorkImageMapper workImageMapper;

    @RequireSelfOrPermissionAnno(identity = UserIdentityEnum.MANAGER, targetPermission = PermissionConst.SEC_MAINTAINER, isHasElseUpper = true)
    public ResponseEntity<J> deleteWorkImageService(String uuid, String authorUuid, String workImageId) {
        WorkImageModel workImage = workImageMapper.selectById(workImageId);
        int sort = workImage.getSort();
        boolean delete = workImageMapper.delete(workImageId);
        if (delete) {
            List<WorkImageModel> workImages = workImageMapper.selectByWorkId(workImage.getWork_id());
            for (WorkImageModel workImageModel : workImages) {
                if (sort < workImageModel.getSort()) {
                    workImageModel.setSort(workImageModel.getSort() - 1);
                    workImageMapper.update(workImageModel);
                }
            }
            return ResponseEntity.ok(new ChangeR().udu(true, 2));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 2));
    }
}
