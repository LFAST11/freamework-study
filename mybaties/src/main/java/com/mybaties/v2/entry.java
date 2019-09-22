package com.mybaties.v2;

import com.mybaties.v1.Score;
import com.mybaties.v2.config.Configuration;
import com.mybaties.v2.config.mapper.TestMapper;
import com.mybaties.v2.executor.ExecutorFatory;
import com.mybaties.v2.session.SqlSession;

import java.util.List;

/**
 * lingfan
 * 2019-09-20 17:22
 */
public class entry {

    public static void main(String[] args) throws ClassNotFoundException {
        Configuration configuration  = new Configuration("com.mybaties.v2.config.mapper");
        configuration.build();

        SqlSession sqlSession = new SqlSession(configuration, ExecutorFatory.getExecute("cache",configuration));

        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        Score score = mapper.selectByPrimary("4班");
        System.out.println(score);

        List<Score> scores = mapper.selectList("2班");

        System.out.println(scores.toString());


    }
}
