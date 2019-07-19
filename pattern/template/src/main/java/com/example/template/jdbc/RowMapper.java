package com.example.template.jdbc;

import java.sql.ResultSet;

/**
 * lingfan
 * 2019-07-19 15:17
 */
public interface RowMapper<T> {

    T mapRow(ResultSet rs,int rowNum) throws Exception;
}
