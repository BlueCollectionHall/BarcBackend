package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.StudentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<StudentModel> selectAll();
    List<StudentModel> selectBySchool(@Param("school_id") String schoolId);
    List<StudentModel> selectByClub(@Param("club_id") String clubId);
    StudentModel selectById(@Param("student_id") String studentId);
}
