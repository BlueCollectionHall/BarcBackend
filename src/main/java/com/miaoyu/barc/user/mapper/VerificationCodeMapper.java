package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.VerificationCodeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface VerificationCodeMapper {
    @Insert("INSERT INTO verification_code (unique_id, code, username, scenario, create_at, expiration_at, used) VALUES (#{unique_id}, #{code}, #{username}, #{scenario}, NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE), false)")
    boolean insert(VerificationCodeModel request);
    @Select("SELECT * FROM verification_code WHERE unique_id = #{unique_id}")
    VerificationCodeModel selectByUniqueId(@Param("unique_id") String uniqueId);
    @Update("UPDATE verification_code SET used = true WHERE unique_id = #{unique_id}")
    boolean updateUsed(@Param("unique_id") String uniqueId);
}
