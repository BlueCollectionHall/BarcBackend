package com.miaoyu.barc.api.work.mapper;

import com.miaoyu.barc.api.work.model.WorkClaimModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WorkClaimMapper {
    @Select("SELECT * FROM work_claim WHERE initiator_uuid = #{uuid}")
    List<WorkClaimModel> selectByInitiatorUuid(String uuid);
    @Select("SELECT * FROM work_claim WHERE recipient_uuid = #{uuid}")
    List<WorkClaimModel> selectByRecipientUuid(String uuid);
    @Select("SELECT * FROM work_claim WHERE work_id = #{work_id}")
    WorkClaimModel selectById(String work_id);
    @Insert("INSERT INTO work_claim (work_id, initiator_uuid, recipient_uuid) VALUES (#{work_id}, #{initiator_uuid}, #{recipient_id})")
    boolean insert(WorkClaimModel workClaimModel);
}
