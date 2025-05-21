package com.miaoyu.barc.feedback.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.feedback.model.WorkFeedbackModel;
import com.miaoyu.barc.feedback.service.WorkFeedbackService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback/work")
public class WorkFeedbackController {
    @Autowired
    private WorkFeedbackService workFeedbackService;

    @GetMapping("/all")
    public ResponseEntity<J> getAllWorkFeedbacksControl() {
        return null;
    }

    @GetMapping("/all_reason_options")
    public ResponseEntity<J> getAllWorkFeedbackReasonOptionsControl() {
        return workFeedbackService.getAllWorkFeedbackReasonOptionsService();
    }

    @IgnoreAuth
    @PostMapping("/upload")
    public ResponseEntity<J> uploadWorkFeedbackControl(
            @RequestBody WorkFeedbackModel request
            ) {
        return workFeedbackService.uploadWorkFeedbackService(request);
    }

    @PutMapping("/update")
    public ResponseEntity<J> updateWorkFeedbackControl(
            HttpServletRequest request,
            @RequestBody WorkFeedbackModel requestModel
    ) {
        return workFeedbackService.updateWorkFeedbackService(request.getAttribute("uuid").toString(), requestModel);
    }
}
