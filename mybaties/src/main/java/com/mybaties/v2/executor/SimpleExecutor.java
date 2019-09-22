package com.mybaties.v2.executor;

import com.mybaties.v1.Score;
import com.mybaties.v2.config.Configuration;
import com.mybaties.v2.config.MapperRegistory;
import com.mybaties.v2.statement.StatementHandle;

import java.sql.*;

/**
 * lingfan
 * 2019-09-20 14:29
 */
public class SimpleExecutor implements Executor {


    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T query(MapperRegistory.MapperData mapperData, Object parameter) {
        StatementHandle statementHandle = new StatementHandle(configuration);
        Object result = statementHandle.query(mapperData, parameter);
        return (T)result;
    }

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
