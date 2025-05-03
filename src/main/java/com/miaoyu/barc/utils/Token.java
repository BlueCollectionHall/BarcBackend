package com.miaoyu.barc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Token {
    @Autowired
    private JwtService jwtService;
}
