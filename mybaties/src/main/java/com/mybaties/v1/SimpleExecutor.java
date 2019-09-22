package com.mybaties.v1;

import java.sql.*;

/**
 * lingfan
 * 2019-09-20 14:29
 */
public class SimpleExecutor implements Executor {

    public <T> T selectOne(String statement, String parameter) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(String.format(statement, String.valueOf(parameter)));
            ResultSet rs = pstmt.executeQuery();
            Score score = new Score();
            while (rs.next()) {
                score.setClass_name(rs.getString("class_name"));
                score.setScore(rs.getInt("score"));
                score.setStudent_name(rs.getString("student_name"));
            }
            return (T) score;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url ="jdbc:mysql://10.243.5.32:3306/budget_db?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String username = "budget";
        String password = "gZ2H5ISKWC";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
