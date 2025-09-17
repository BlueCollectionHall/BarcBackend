package com.miaoyu.barc.feedback.service;

import com.miaoyu.barc.api.work.mapper.WorkMapper;
import com.miaoyu.barc.api.work.model.WorkModel;
import com.miaoyu.barc.email.utils.SendEmailUtils;
import com.miaoyu.barc.feedback.enumeration.FeedbackTypeEnum;
import com.miaoyu.barc.feedback.model.FeedbackFormModel;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserBasicMapper;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private UserBasicMapper userBasicMapper;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private SendEmailUtils sendEmailUtils;

    public ResponseEntity<J> getFeedbacksByTypeService(Integer count, FeedbackTypeEnum typeEnum) {

    }

    public ResponseEntity<J> uploadFeedbackService(FeedbackFormModel requestModel) {
        if (requestModel.getAuthor() == null) {
            if (requestModel.getEmail() == null) {
                return ResponseEntity.ok(new SuccessR().normal("不发送Email"));
            }
        } else {
            UserBasicModel userBasic = userBasicMapper.selectByUuid(requestModel.getAuthor());
            if (userBasic == null) {
                return ResponseEntity.ok(new UserR().noSuchUser());
            }
            requestModel.setEmail(userBasic.getEmail());
        }
        WorkModel work = workMapper.selectById(requestModel.getTarget_id());
        if (work == null) {
            return ResponseEntity.ok(new ErrorR().normal("您投诉的作品已不存在！"));
        }
        boolean isSent = sendEmailUtils.customEmail(requestModel.getEmail(),
                "投诉反馈已受理",
                "您对B.A.R.C.中收录作品：《" + work.getTitle() + "》的投诉反馈文件，已经送达风纪委员会受理，处理结果会以电子邮箱的方式通知给您！");
        if (isSent) {
            return ResponseEntity.ok(new SuccessR().normal("邮件发送成功"));
        }
        return ResponseEntity.ok(new SuccessR().normal("邮件发送失败"));
    }
}
