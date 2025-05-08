package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.BarcNaigosTokenModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BarcNaigosTokenMapper {
    @Select("SELECT * FROM barc_naigos_token WHERE naigos_id = #{naigos_id}")
    BarcNaigosTokenModel selectByNaigosUuid(@Param("naigos_id") String naigosId);
}
