package com.mybaties.v1;

/**
 * lingfan
 * 2019-09-20 14:37
 */
public class Entry {

    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new Configuration(),new SimpleExecutor());

        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        Score score = mapper.selectByPrimary("4班");

        System.out.println(score);
    }
}
