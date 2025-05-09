package com.miaoyu.barc.user.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.annotation.UserPath;
import com.miaoyu.barc.response.SignR;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.user.service.SignService;
import com.miaoyu.barc.utils.J;
import com.miaoyu.barc.utils.NaigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@UserPath
@IgnoreAuth
public class SignController {
    @Autowired
    private SignService signService;

    /**登录
     * @param type 登录方式 "username"/"email"
     * @param account 账号
     * @param password 明文密码*/
    @GetMapping("/sign_in")
    public ResponseEntity<J> signInUserControl(
            @RequestParam("type") String type,
            @RequestParam("account") String account,
            @RequestParam("password") String password
    ) {
        return signService.signInUserService(type, account, password);
    }
    /**通过Naigos登录
     * @param type 登录方式 "uid"/"email"
     * @param account 账号
     * @param password 明文密码*/
    @GetMapping("/sign_in_by_naigos")
    public ResponseEntity<J> signInUserByNaigosControl(
            @RequestParam("type") String type,
            @RequestParam("account") String account,
            @RequestParam("password") String password
    ) {
        return signService.signInByNaigosService(type, account, password);
    }
    /**注册
     * @param email 电子邮箱
     * @param uniqueId 邮箱验证唯一id
     * @param code 邮箱验证码
     * @param userBasic 用户基础信息实体*/
    @PostMapping("/sign_up")
    public ResponseEntity<J> signUpUserControl(
            @RequestParam("email") String email,
            @RequestParam("unique_id") String uniqueId,
            @RequestParam("code") String code,
            @RequestBody UserBasicModel userBasic
    ) {
        J j = signService.checkSignupCode(uniqueId, code, email);
        if (j.getCode() == 1) {
            return ResponseEntity.ok(j);
        }
        return signService.signupUserService(userBasic, email);
    }
    /**获取注册验证码
     * @param email 电子邮箱*/
    @GetMapping("/get_signup_code")
    public ResponseEntity<J> getSignupCodeControl(@RequestParam("email") String email) {
        return signService.getSignupCodeService(email);
    }
}
