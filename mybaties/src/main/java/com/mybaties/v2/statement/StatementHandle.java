package com.mybaties.v2.statement;

import com.mybaties.v1.Score;
import com.mybaties.v2.config.Configuration;
import com.mybaties.v2.config.MapperRegistory;
import com.mybaties.v2.result.ResultSetHandle;

import java.sql.*;
import java.util.List;

/**
 * lingfan
 * 2019-09-20 16:35
 */
public class StatementHandle<T> {

    private Configuration configuration;

    public StatementHandle(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T query(MapperRegistory.MapperData mapperData, Object parameter) {
        List<T> list = (List<T>) this.queryList(mapperData, parameter);
        return list.get(0);
    }

    public List<T>  queryList(MapperRegistory.MapperData mapperData, Object parameter) {
        try {
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement(String.format(mapperData.getSql(), String.valueOf(parameter)));
            ResultSet rs = pst.executeQuery();
            ResultSetHandle resultSetHandle = new ResultSetHandle(configuration);
            List<T> result = resultSetHandle.handle(rs, mapperData.getAClass());
            return result;
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
