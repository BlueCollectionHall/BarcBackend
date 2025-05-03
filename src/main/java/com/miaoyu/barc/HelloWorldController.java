package com.miaoyu.barc;

import com.miaoyu.barc.response.HelloR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public ResponseEntity<J> hello() {
        return ResponseEntity.ok(new HelloR().Hello());
    }
}
