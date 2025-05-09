package com.miaoyu.barc.user.controller;

import com.miaoyu.barc.annotation.UserPath;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.user.model.UserArchiveModel;
import com.miaoyu.barc.user.service.UserService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**通过请求头中Token令牌获取本账号个人信息
     * @return 本账号个人信息*/
    @GetMapping("/current_me")
    public ResponseEntity<J> getMeCurrentControl(HttpServletRequest request) {
        return userService.getMeCurrentService(request.getAttribute("uuid").toString());
    }
    /**通过请求头中Token令牌获取本账号个人基础信息
     * @return 本账号个人基础信息*/
    @GetMapping("/basic_me")
    public ResponseEntity<J> getMeBasicControl(HttpServletRequest request) {
        return userService.getMeBasicService(request.getAttribute("uuid").toString());
    }
    /**更新个人信息*/
    @PostMapping("/edit_archive")
    public ResponseEntity<J> editArchiveControl(HttpServletRequest request, @RequestBody UserArchiveModel requestModel) {
        return userService.editArchiveService(request.getAttribute("uuid").toString(), requestModel);
    }
}
