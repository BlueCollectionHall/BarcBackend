package com.miaoyu.barc.feedback.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.feedback.mapper.FeedbackMapper;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@IgnoreAuth
@RestController
@RequestMapping("/feedback")
public class FeedbackTypeOfController {
    private final FeedbackMapper feedbackMapper;

    public FeedbackTypeOfController(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    @GetMapping("/all_types")
    public ResponseEntity<J> getFeedbackAllTypesControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, feedbackMapper.selectAllTypes()));
    }

    @GetMapping("/options_by_type")
    public ResponseEntity<J> getFeedbackOptionsByTypeControl(
            @RequestParam("type_id") String typeId
    ) {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, feedbackMapper.selectOptionsByParentId(typeId)));
    }
}
