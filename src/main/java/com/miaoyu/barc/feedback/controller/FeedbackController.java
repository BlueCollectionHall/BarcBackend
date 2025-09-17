package com.miaoyu.barc.feedback.controller;

import com.miaoyu.barc.annotation.IgnoreAuth;
import com.miaoyu.barc.feedback.enumeration.FeedbackStatusEnum;
import com.miaoyu.barc.feedback.enumeration.FeedbackTypeEnum;
import com.miaoyu.barc.feedback.model.FeedbackFormModel;
import com.miaoyu.barc.feedback.service.FeedbackService;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**获取全部投诉反馈的类型
     * @return List类型中包含键值对*/
    @IgnoreAuth
    @GetMapping("/types")
    public ResponseEntity<J> getFeedbackTypesControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, FeedbackTypeEnum.getOptions()));
    }

    /**获取全部投诉反馈的状态
     * @return List类型中包含键值对*/
    @IgnoreAuth
    @GetMapping("/status")
    public ResponseEntity<J> getFeedbackStatusControl() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, FeedbackStatusEnum.getOptions()));
    }

    /**根据类型获取全部投诉反馈信息
     * @param count 获取数量，0为全部
     * @param typeEnum 类型
     * @return List类型中包含指定数量符合条件的Feedback实体*/
    @GetMapping("/feedbacks_by_type")
    public ResponseEntity<J> getFeedbacksByTypeControl(
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam("type") FeedbackTypeEnum typeEnum
    ) {
        return null;
    }

    /**上传投诉表单
     * @param requestModel Feedback实体
     * @return 上传是否成功*/
    @IgnoreAuth
    @PostMapping("/upload")
    public ResponseEntity<J> uploadFeedbackControl(@RequestBody FeedbackFormModel requestModel) {
        return feedbackService.uploadFeedbackService(requestModel);
    }
}
