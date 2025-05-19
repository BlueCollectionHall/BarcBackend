package com.miaoyu.barc.feedback.mapper;

import com.miaoyu.barc.feedback.model.FeedbackOptionModel;
import com.miaoyu.barc.feedback.model.FeedbackTypeModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FeedbackMapper {
    @Select("SELECT * FROM feedback_option WHERE parent_id = #{parent_id}")
    List<FeedbackOptionModel> selectOptionsByParentId(@Param("parent_id") String parentId);
    @Select("SELECT * FROM feedback_type")
    List<FeedbackTypeModel> selectAllTypes();
}
