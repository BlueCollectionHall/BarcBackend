package com.miaoyu.barc.comment.controller;

import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment/work")
public class WorkCommentController {
    @GetMapping("/comments_by_work")
    public ResponseEntity<J> getCommentsByWork(
            @RequestParam("work_id") String workId
    ) {

    }
}
