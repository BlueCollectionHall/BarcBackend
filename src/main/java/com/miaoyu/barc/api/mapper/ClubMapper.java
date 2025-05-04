package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.SchoolClubModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClubMapper {
    @Select("SELECT * FROM school_clubs")
    List<SchoolClubModel> selectAll();
    @Select("SELECT * FROM school_clubs WHERE school = #{school_id}")
    List<SchoolClubModel> selectBySchool(@Param("school_id") String schoolId);
    @Select("SELECT * FROM school_clubs WHERE id = #{club_id}")
    SchoolClubModel selectById(@Param("club_id") String clubId);
}
