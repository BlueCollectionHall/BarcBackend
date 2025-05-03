package com.miaoyu.barc.user.controller;

import com.miaoyu.barc.annotation.UserPath;
import com.miaoyu.barc.response.SignR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@UserPath
@RequestMapping("/sign")
public class SignController {
    @PostMapping("/in")
    public ResponseEntity<J> signInUserControl() {
        return ResponseEntity.ok(new SignR().SignIn(false));
    }
    @PostMapping("/up")
    public ResponseEntity<J> signUpUserControl() {
        return ResponseEntity.ok(new SignR().SignUp(false));
    }
}
