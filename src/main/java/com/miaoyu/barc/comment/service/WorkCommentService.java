package com.miaoyu.barc.comment.service;

import com.miaoyu.barc.annotation.RequireSelfOrManagerAnno;
import com.miaoyu.barc.comment.mapper.WorkCommentMapper;
import com.miaoyu.barc.comment.mapper.WorkCommentReplyMapper;
import com.miaoyu.barc.comment.model.WorkCommentModel;
import com.miaoyu.barc.comment.model.WorkCommentReplyModel;
import com.miaoyu.barc.comment.pojo.WorkCommentPojo;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.utils.GenerateUUID;
import com.miaoyu.barc.utils.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkCommentService {
    @Autowired
    private WorkCommentMapper workCommentMapper;
    @Autowired
    private WorkCommentReplyMapper workCommentReplyMapper;
    @Autowired
    private UserArchiveMapper userArchiveMapper;

    public ResponseEntity<J> getCommentsByWorkService(String workId) {
        List<WorkCommentPojo> commentPojos = workCommentMapper.selectByWorkId(workId);
        for (WorkCommentPojo commentPojo : commentPojos) {
            List<WorkCommentReplyModel> commentReplies = workCommentReplyMapper.selectByParentId(commentPojo.getId());
            commentPojo.setReplies(commentReplies);
        }
        return ResponseEntity.ok(new ResourceR().resourceSuch(true, commentPojos));
    }

    public ResponseEntity<J> uploadCommentAndReplyService(String type, String uuid, WorkCommentModel commentModel, WorkCommentReplyModel replyModel) {
        switch (type) {
            case "comment": {
                commentModel.setAuthor(uuid);
                commentModel.setId(new GenerateUUID().getUuid36l());
                boolean insert = workCommentMapper.insert(commentModel);
                if (insert) {
                    return ResponseEntity.ok(new ChangeR().udu(true, 1));
                }
                return ResponseEntity.ok(new ChangeR().udu(false, 1));
            } case "reply": {
                replyModel.setId(new GenerateUUID().getUuid36l());
                replyModel.setAuthor(uuid);
                boolean insert = workCommentReplyMapper.insert(replyModel);
                if (insert) {
                    return ResponseEntity.ok(new ChangeR().udu(true, 1));
                }
                return ResponseEntity.ok(new ChangeR().udu(false, 1));
            } default: {
                return ResponseEntity.ok(new ChangeR().udu(false, 1));
            }
        }
    }

    public ResponseEntity<J> deleteCommentService(String uuid, String commentId) {
        WorkCommentPojo workCommentPojo = workCommentMapper.selectById(commentId);
        System.out.println("uuid:"+uuid + " author:"+workCommentPojo.getAuthor());
        return this.deleteCommentServiceSub(uuid, workCommentPojo.getAuthor(), commentId);
    }

    @RequireSelfOrManagerAnno(managerPermission = PermissionConst.FIR_MAINTAINER, isHasElseUpper = true)
    private ResponseEntity<J> deleteCommentServiceSub(String uuid, String authorUuid, String commentId) {
        return ResponseEntity.ok(new ChangeR().udu(workCommentMapper.delete(commentId), 2));
    }

    public ResponseEntity<J> deleteReplyService(String uuid, String replyId) {
        WorkCommentReplyModel reply = workCommentReplyMapper.selectById(replyId);
        if (reply.getAuthor().equals(uuid) || PermissionConst.FIR_MAINTAINER <= userArchiveMapper.selectByUuid(uuid).getPermission()) {
            boolean delete = workCommentReplyMapper.deleteById(replyId);
            if (delete) {
                return ResponseEntity.ok(new ChangeR().udu(true, 2));
            }
            return ResponseEntity.ok(new ChangeR().udu(false, 2));
        }
        return ResponseEntity.ok(new ChangeR().udu(false, 2));
    }
}
