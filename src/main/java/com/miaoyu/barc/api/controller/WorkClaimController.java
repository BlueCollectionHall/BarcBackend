package com.miaoyu.barc.api.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiPath
public class WorkClaimController {
    @GetMapping("/need_claims")
    public ResponseEntity<J> getNeedClaims(HttpServletRequest request) {
        return null;
    }
}
