package com.miaoyu.barc.user.service;

import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserArchiveMapper userArchiveMapper;

    public ResponseEntity<J> getMeCurrentService(String uuid) {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (Objects.isNull(userArchive)) {
            return ResponseEntity.status(403).body(new UserR().noSuchUser());
        }
        return ResponseEntity.ok(new SuccessR().normal(userArchive));
    }
}
