package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.UserBasicModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserBasicMapper {
    @Insert("INSERT INTO user_basic (uuid, username, password, email, email_verified, telephone) VALUES (#{uuid}, #{username}, #{password}, #{email}, #{email_verified}, #{telephone})")
    boolean insert(UserBasicModel request);
    @Select("SELECT * FROM user_basic WHERE uuid = #{uuid}")
    UserBasicModel selectByUuid(@Param("uuid") String uuid);
    @Select("SELECT * FROM user_basic WHERE username = #{username}")
    UserBasicModel selectByUsername(@Param("username") String username);
    @Select("SELECT * FROM user_basic WHERE email = #{email}")
    UserBasicModel selectByEmail(@Param("email") String email);
}
