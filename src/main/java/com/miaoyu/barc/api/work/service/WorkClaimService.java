package com.miaoyu.barc.api.work.service;

import com.miaoyu.barc.api.work.mapper.WorkClaimMapper;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WorkClaimService {
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private WorkClaimMapper workClaimMapper;

    public ResponseEntity<J> getAllClaimsControl(String uuid) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (!(userArchive.getIdentity() == UserIdentityEnum.MANAGER) && !(userArchive.getPermission() == PermissionConst.DISCIPLINARY_COMMITTEE)) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workClaimMapper.selectAll()));
    }

    public ResponseEntity<J> getClaimsByWorkControl(String uuid, String workId) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (!(userArchive.getIdentity() == UserIdentityEnum.MANAGER) && !(userArchive.getPermission() == PermissionConst.DISCIPLINARY_COMMITTEE)) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workClaimMapper.selectByWorkId(workId)));
    }

    public ResponseEntity<J> getClaimsByUuidControl(String uuid) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, workClaimMapper.selectByUuid(uuid)));
    }
}
