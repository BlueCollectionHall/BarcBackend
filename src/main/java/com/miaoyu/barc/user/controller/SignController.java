package com.miaoyu.barc.user.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.annotation.UserPath;
import com.miaoyu.barc.response.SignR;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.user.service.SignService;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@UserPath
@IgnoreAuth
public class SignController {
    @Autowired
    private SignService signService;

    @GetMapping("/sign_in")
    public ResponseEntity<J> signInUserControl(
            @RequestParam("type") String type,
            @RequestParam("account") String account,
            @RequestParam("password") String password
    ) {
        return signService.signInUserService(type, account, password);
    }
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
    @GetMapping("/get_signup_code")
    public ResponseEntity<J> getSignupCodeControl(@RequestParam("email") String email) {
        return signService.getSignupCodeService(email);
    }
}
