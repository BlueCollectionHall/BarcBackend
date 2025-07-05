package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.UserArchiveModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserArchiveMapper {
    @Select("SELECT * FROM user_archive WHERE uuid = #{uuid}")
    UserArchiveModel selectByUuid(@Param("uuid") String uuid);
    @Select("SELECT ua.* FROM user_archive ua JOIN user_basic ub ON ua.uuid = ub.uuid WHERE ub.username = #{username}")
    UserArchiveModel selectByUsername(@Param("username") String username);
    @Insert("INSERT INTO user_archive (uuid, nickname, avatar) VALUES (#{uuid}, #{nickname}, #{avatar})")
    boolean insert(UserArchiveModel model);
    @Update("UPDATE user_archive SET nickname = #{nickname}, avatar = #{avatar}, gender = #{gender}, birthday = #{birthday}, age = #{age}, permission = #{permission}, updated_at = #{updated_at} WHERE uuid = #{uuid}")
    boolean update(UserArchiveModel model);

}
