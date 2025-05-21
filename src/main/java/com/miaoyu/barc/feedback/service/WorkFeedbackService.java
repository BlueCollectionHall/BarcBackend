package com.miaoyu.barc.feedback.service;

import com.miaoyu.barc.api.mapper.WorkMapper;
import com.miaoyu.barc.api.model.WorkModel;
import com.miaoyu.barc.email.utils.SendEmailUtils;
import com.miaoyu.barc.feedback.mapper.FeedbackMapper;
import com.miaoyu.barc.feedback.mapper.WorkFeedbackMapper;
import com.miaoyu.barc.feedback.model.WorkFeedbackModel;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.utils.GenerateUUID;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WorkFeedbackService {
    @Autowired
    private WorkFeedbackMapper workFeedbackMapper;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private SendEmailUtils sendEmailUtils;
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;

    public ResponseEntity<J> getAllWorkFeedbackReasonOptionsService() {
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, feedbackMapper.selectOptionsByParentId("work")));
    }

    public ResponseEntity<J> uploadWorkFeedbackService(WorkFeedbackModel request) {
        WorkModel work = workMapper.selectById(request.getWork_id());
        if (Objects.isNull(work)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        request.setId(new GenerateUUID().getUuid36l());
        boolean insert = workFeedbackMapper.insert(request);
        if (insert) {
            sendEmailUtils.customEmail(request.getEmail(),
                    "投诉反馈已受理",
                    "您对蔚蓝收录馆中收录作品：《" + work.getTitle() + "》的投诉反馈文件，已经送达风纪委员会受理，处理结果会以电子邮箱的方式通知给您！感谢您对蔚蓝收录馆的支持！");
            return ResponseEntity.ok(new ChangeR().udu(true, 1));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 1));
    }

    public ResponseEntity<J> updateWorkFeedbackService(String uuid, WorkFeedbackModel requestModel) {
        if (userArchiveMapper.selectByUuid(uuid).getPermission() < PermissionConst.SEC_MAINTAINER) {
            return ResponseEntity.ok(new UserR().insufficientAccountPermission());
        }
        WorkFeedbackModel workFeedbackDB = workFeedbackMapper.selectById(requestModel.getId());
        if (Objects.isNull(workFeedbackDB)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        WorkModel work = workMapper.selectById(requestModel.getWork_id());
        if (Objects.isNull(work)) {
            return ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        }
        boolean b = workFeedbackMapper.update(requestModel.getId(), requestModel.getNote());
        if (b) {
            sendEmailUtils.customEmail(workFeedbackDB.getEmail(),
                    "投诉反馈已处理完成",
                    "您对蔚蓝收录馆中收录作品：《" + work.getTitle() + "》的投诉反馈已经完成了处理，处理反馈：" + requestModel.getNote());
            return ResponseEntity.ok(new ChangeR().udu(true, 3));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 3));
    }
}
