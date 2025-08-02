package com.miaoyu.barc.user.service;

import com.miaoyu.barc.email.utils.SendEmailUtils;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.mapper.UserBasicMapper;
import com.miaoyu.barc.user.mapper.UserPermissionMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserPermissionService {
    @Autowired
    private PermissionConst permissionConst;
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;
    @Autowired
    private SendEmailUtils sendEmailUtils;
    @Autowired
    private UserBasicMapper userBasicMapper;

    public ResponseEntity<J> getUserPermissionsByIdentityService(UserIdentityEnum identity) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, permissionConst.permissionByIdentity(identity)));
    }

    public ResponseEntity<J> getUserPermissionCnService(UserIdentityEnum identity, Integer permission) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, permissionConst.toString(identity, permission)));
    }

    public ResponseEntity<J> changeUserPermissionService(
            String operatorUUID, String uuid, UserIdentityEnum identity, Integer permission
    ) {
        UserArchiveModel opArchive = userArchiveMapper.selectByUuid(operatorUUID);
        if (opArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (!opArchive.getIdentity().equals(UserIdentityEnum.MANAGER)) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (userArchive.getIdentity().equals(UserIdentityEnum.MANAGER) && opArchive.getPermission() <= userArchive.getPermission()) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        if (Objects.equals(opArchive.getPermission(), permission)) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        userArchive.setPermission(permission);
        userArchive.setIdentity(identity);
        boolean update = userPermissionMapper.update(userArchive);
        if (update) {
            UserBasicModel userBasic = userBasicMapper.selectByUuid(userArchive.getUuid());
            sendEmailUtils.customEmail(
                    userBasic.getEmail(),
                    "账号权限变更",
                    userArchive.getNickname() + "，您的账号已经变更为【" + identity.getDisplayName() + "：" + permissionConst.toString(identity, permission) + "】！"
            );
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
}
