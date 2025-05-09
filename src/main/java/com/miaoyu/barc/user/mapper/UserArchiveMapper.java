package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.UserArchiveModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserArchiveMapper {
    @Select("SELECT * FROM user_archive WHERE uuid = #{uuid}")
    UserArchiveModel selectByUuid(@Param("uuid") String uuid);
    @Insert("INSERT INTO user_archive (uuid, nickname, avatar) VALUES (#{uuid}, #{nickname}, #{avatar})")
    boolean insert(UserArchiveModel model);
}
