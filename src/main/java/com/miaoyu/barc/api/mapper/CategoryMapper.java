package com.miaoyu.barc.api.mapper;

import com.miaoyu.barc.api.model.data.CategoryData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    @Select("SELECT * FROM category")
    List<CategoryData> selectAll();
}
