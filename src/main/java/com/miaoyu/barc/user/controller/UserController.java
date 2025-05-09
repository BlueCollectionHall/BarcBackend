package com.miaoyu.barc.user.controller;

import com.miaoyu.barc.annotation.UserPath;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.user.service.UserService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@UserPath
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/current_me")
    public ResponseEntity<J> getMeCurrentControl(HttpServletRequest request) {
        return userService.getMeCurrentService(request.getAttribute("uuid").toString());
    }
}
