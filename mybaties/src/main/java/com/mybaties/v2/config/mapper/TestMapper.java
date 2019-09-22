package com.mybaties.v2.config.mapper;

import com.mybaties.v1.Score;
import com.mybaties.v2.config.annotation.Select;

import java.util.List;

/**
 * lingfan
 * 2019-09-20 14:17
 */
public interface TestMapper {

    @Select("select * from score_info where class_name = '%s'")
     Score selectByPrimary(String id);


    @Select("select * from score_info where class_name   = '%s'")
    List<Score> selectList(String id);


}
