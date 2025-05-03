package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.SchoolModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolMapper {
    List<SchoolModel> selectAll();
    SchoolModel selectById(@Param("school_id") String schoolId);
}
