package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.WorkModel;
import com.miaoyu.barc.api.model.entity.WorkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WorkMapper {
    @Select("SELECT * FROM work")
    List<WorkEntity> selectAll();
    @Select("SELECT w.* FROM work w JOIN student stu ON w.student = stu.id WHERE stu.school = #{school_id}")
    List<WorkEntity> selectBySchoolId(@Param("school_id") String schoolId);
    @Select("SELECT w.* FROM work w JOIN student stu ON w.student = stu.club WHERE stu.club = #{club_id}")
    List<WorkEntity> selectByClubId(@Param("club_id") String clubId);
    @Select("SELECT * FROM work WHERE student = #{student_id}")
    List<WorkEntity> selectByStudentId(@Param("student_id") String studentId);
    @Select("SELECT * FROM work WHERE author = #{uuid}")
    List<WorkEntity> selectByUuid(@Param("uuid") String uuid);
    // 获取当前分类ID下属所有分类中的内容
    @Select("WITH RECURSIVE category_tree AS " +
            "( SELECT c1.* FROM category c1 WHERE c1.id = #{id} UNION ALL " +
            "SELECT c2.* FROM category c2 JOIN category_tree ct ON c2.parent_id = ct.id)" +
            "SELECT DISTINCT w.* FROM work w JOIN work_category wc ON w.id = wc.work_id " +
            "JOIN category_tree ct ON wc.category_id = ct.id")
    List<WorkEntity> selectByCategoryId(@Param("id") String id);
    @Select("SELECT * FROM work WHERE id = #{work_id}")
    WorkModel selectById(@Param("work_id") String workId);
    @Insert("INSERT INTO work " +
            "(id, title, description, content, banner_image, cover_image, author, author_nickname, source, student) VALUES " +
            "(#{id}, #{title}, #{description}, #{content}, #{banner_image}, #{cover_iamge}, #{author}, #{author_nickname}, #{source}, #{student})")
    boolean insert(WorkModel workModel);
    @Update("UPDATE work SET title = #{title}, description = #{description}, content = #{content}, banner_image = #{banner_image}, cover_image = #{cover_image}, author = #{author}, author_nickname = #{author_nickname}, is_claim = #{is_claim}, source = #{source}, student = #{student} WHERE id = #{id}")
    boolean update(WorkModel workModel);

}
