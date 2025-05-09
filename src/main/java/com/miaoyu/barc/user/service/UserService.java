package com.miaoyu.barc.user.service;

import com.miaoyu.barc.email.utils.SendEmailUtils;
import com.miaoyu.barc.response.*;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.mapper.UserBasicMapper;
import com.miaoyu.barc.user.mapper.VerificationCodeMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.user.model.VerificationCodeModel;
import com.miaoyu.barc.utils.GenerateCode;
import com.miaoyu.barc.utils.GenerateUUID;
import com.miaoyu.barc.utils.J;
import com.miaoyu.barc.utils.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private UserBasicMapper userBasicMapper;
    @Autowired
    private SendEmailUtils sendEmailUtils;
    @Autowired
    private PasswordHash passwordHash;
    @Autowired
    private VerificationCodeMapper verificationCodeMapper;

    public ResponseEntity<J> getMeCurrentService(String uuid) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (Objects.isNull(userArchive)) {
            return ResponseEntity.status(403).body(new UserR().noSuchUser());
        }
        return ResponseEntity.ok(new SuccessR().normal(userArchive));
    }
    public ResponseEntity<J> getMeBasicService(String uuid) {
        UserBasicModel userBasic = userBasicMapper.selectByUuid(uuid);
        if (Objects.isNull(userBasic)) {
            return ResponseEntity.status(403).body(new UserR().noSuchUser());
        }
        return ResponseEntity.ok(new SuccessR().normal(userBasic));
    }
    public ResponseEntity<J> editArchiveService(String uuid, UserArchiveModel requestModel) {
        if (!uuid.equals(requestModel.getUuid())) {
            return ResponseEntity.ok(new ErrorR().normal("登录账号与要修改的信息不匹配！"));
        }
        if (!Objects.isNull(requestModel.getBirthday())) {
            requestModel.setAge(Period.between(requestModel.getBirthday(), LocalDate.now()).getYears());
        }
        boolean b = userArchiveMapper.update(requestModel);
        if (b) {
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
    public ResponseEntity<J> editBasicService(String uuid, UserBasicModel requestModel) {
        if (!uuid.equals(requestModel.getUuid())) {
            return ResponseEntity.ok(new ErrorR().normal("登录账号与要修改的信息不匹配！"));
        }
        boolean b = userBasicMapper.update(requestModel);
        if (b) {
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
    public ResponseEntity<J> resetPasswordService(String uniqueId, String code, String email, String password) {
        VerificationCodeModel vcDB = verificationCodeMapper.selectByUniqueId(uniqueId);
        if (Objects.isNull(vcDB)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        if (!email.equals(vcDB.getUsername())) {
            return ResponseEntity.ok(new ErrorR().normal("验证码记录邮箱与要修改的邮箱不相同！"));
        }
        if (!code.equals(vcDB.getCode()) || !vcDB.getScenario().equals("ResetPassword")) {
            return ResponseEntity.ok(new ErrorR().normal("验证码不一致！"));
        }
        String s = passwordHash.passwordHash256(password);
        if (Objects.isNull(s)) {
            return ResponseEntity.ok(new ErrorR().normal("密码重置失败！"));
        }
        verificationCodeMapper.updateUsed(uniqueId);
        boolean b = userBasicMapper.updatePassword(uniqueId, password);
        if (b) {
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
}
