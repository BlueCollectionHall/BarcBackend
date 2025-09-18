package com.miaoyu.barc.feedback.mapper;

import com.miaoyu.barc.feedback.enumeration.FeedbackTypeEnum;
import com.miaoyu.barc.feedback.model.FeedbackFormModel;
import com.miaoyu.barc.feedback.model.FeedbackOptionModel;
import com.miaoyu.barc.feedback.model.FeedbackTypeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FeedbackMapper {
    @Select("SELECT * FROM feedback_option WHERE parent_id = #{parent_id}")
    List<FeedbackOptionModel> selectOptionsByParentId(@Param("parent_id") String parentId);
    @Select("SELECT * FROM feedback_type")
    List<FeedbackTypeModel> selectAllTypes();

    @Select("SELECT * FROM feedback WHERE type = #{type} ORDER BY updated_at DESC")
    List<FeedbackFormModel> selectByType(@Param("type")FeedbackTypeEnum typeEnum);
    @Select("SELECT * FROM feedback WHERE id = #{id}")
    FeedbackFormModel selectById(@Param("id") String id);

    @Insert("INSERT INTO feedback (id, target_id, ipv4, ipv6, author, email, content, echo, type) VALUES (#{id}, #{target_id}, #{ipv4}, #{ipv6}, #{author}, #{email}, #{content}, #{echo}, #{type})")
    boolean insert(FeedbackFormModel requestModel);
    @Update("UPDATE feedback SET echo = #{echo}, status = #{status} WHERE id = #{id}")
    boolean update(FeedbackFormModel requestModel);
}
