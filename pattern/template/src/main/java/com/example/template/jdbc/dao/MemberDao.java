package com.example.template.jdbc.dao;

import com.example.template.jdbc.JdbcTemplate;
import com.example.template.jdbc.Member;
import com.example.template.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * lingfan
 * 2019-07-19 16:42
 */
public class MemberDao extends JdbcTemplate {
    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<?> selectALl(){
        String sql = "select * from Member";
        return super.executeQuery(sql, new RowMapper<Member>() {

            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                member.setUsername(rs.getString("username"));
                member.setPasswword(rs.getString("password"));
                member.setNickname(rs.getString("nickname"));
                return member;
            }
        },null);

    }
}
