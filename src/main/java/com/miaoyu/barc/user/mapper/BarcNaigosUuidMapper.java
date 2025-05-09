package com.miaoyu.barc.user.mapper;

import com.miaoyu.barc.user.model.BarcNaigosTokenModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BarcNaigosUuidMapper {
    @Select("SELECT * FROM barc_naigos_uuid WHERE naigos_uuid = #{naigos_uuid}")
    BarcNaigosTokenModel selectByNaigosUuid(@Param("naigos_uuid") String naigos_uuid);
    @Insert("INSERT INTO barc_naigos_uuid (uuid, naigos_uuid) VALUES (#{uuid}, #{naigos_uuid})")
    int insert(BarcNaigosTokenModel barcNaigosTokenModel);
}
