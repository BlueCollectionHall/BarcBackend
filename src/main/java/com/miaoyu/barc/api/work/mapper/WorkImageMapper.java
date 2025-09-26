package com.miaoyu.barc.api.work.mapper;

import com.miaoyu.barc.api.work.model.WorkImageModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WorkImageMapper {
    @Select("SELECT * FROM work_image WHERE work_id = #{work_id}")
    List<WorkImageModel> selectByWorkId(@Param("work_id") String work_id);
    @Select("SELECT * FROM work_image WHERE id = #{id}")
    WorkImageModel selectById(@Param("id") String id);

    @Insert("INSERT INTO work_image (id, work_id, sort, image_url, image_name) VALUES (#{id}, #{work_id}, #{sort}, #{image_url}, #{image_name})")
    boolean insert(WorkImageModel workImageModel);
    @Update("UPDATE work_image SET sort = #{sort}, image_url = #{image_url}, image_name = #{image_name} WHERE id = #{id}")
    boolean update(WorkImageModel workImageModel);
    @Delete("DELETE FROM work_image WHERE id = #{id}")
    boolean delete(@Param("id") String id);
}
