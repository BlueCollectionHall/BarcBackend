package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.SchoolClubModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClubMapper {
    List<SchoolClubModel> selectAll();
    List<SchoolClubModel> selectBySchool(@Param("school_id") String schoolId);
    SchoolClubModel selectById(@Param("club_id") String clubId);
}
