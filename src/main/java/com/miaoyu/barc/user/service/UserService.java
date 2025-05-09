package com.miaoyu.barc.user.service;

import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.mapper.UserBasicMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.utils.J;
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
}
