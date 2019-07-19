package com.example.template.jdbc;

import com.example.template.jdbc.dao.MemberDao;

import java.util.List;

/**
 * lingfan
 * 2019-07-19 16:50
 */
public class JdbcTemplateTest {

    public static void main(String[] args) {
        MemberDao memberDao = new MemberDao(null);
        List<?> list = memberDao.selectALl();
        System.out.println(list);
    }
}
