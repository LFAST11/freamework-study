package com.mybaties.v2.result;

import com.mybaties.v2.config.Configuration;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * lingfan
 * 2019-09-20 16:02
 */
public class ResultSetHandle<E> {

    private Configuration configuration;

    public ResultSetHandle(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<E>  handle(ResultSet rs,Class type) throws SQLException {
        List<E> resultList = new ArrayList();
        try {
            //TO-DO typeHandle
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()){
                Object result = type.newInstance();
                for(int i=1;i<columnCount;i++){

                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = type.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result,rs.getObject(columnName));
                }
                resultList.add((E) result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  resultList;
    }
}
