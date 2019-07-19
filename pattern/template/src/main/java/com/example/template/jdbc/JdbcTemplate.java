package com.example.template.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * lingfan
 * 2019-07-19 15:16
 */
public abstract class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<?> executeQuery(String sql,RowMapper<?> rowMapper,Object[] values){
        try{
            //1.获取连接
            Connection connection = dataSource.getConnection();
            //创建语句集
            PreparedStatement ps = createPrepapreSatement(connection,sql);

            //3.执行语句集
            ResultSet rs = executeQuery(ps,values);

            //4.处理结果集
            List<?> result = paresResult(rs,rowMapper);

            //5.关闭结果集
            closeResultSet(rs);

            //6.关闭语句集
            closeStatement(ps);

            //7.关闭连接
            closeConnection(connection);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    private void closeStatement(PreparedStatement ps) throws SQLException {
        ps.close();
    }

    private void closeResultSet(ResultSet rs) throws SQLException {
        rs.close();
    }

    private List<?> paresResult(ResultSet rs, RowMapper<?> rowMapper) throws Exception {
        List<Object> result = new ArrayList<>();
        int rowNum = 1;
        while (rs.next()){
            result.add(rowMapper.mapRow(rs,rowNum++));
        }
        return null;
    }

    private ResultSet executeQuery(PreparedStatement ps, Object[] values) throws SQLException {
        for(int i=0;i<values.length;i++){
            ps.setObject(i,values[i]);
        }
        return ps.getResultSet();
    }

    private PreparedStatement createPrepapreSatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
