package com.mybaties.demo;

import com.mybaties.v1.Score;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * lingfan
 * 2019-09-22 13:30
 */
public class MybatiesTest {

    /**
     * 通过接口形式调用
     */
    @Test
    public void Test() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("/Applications/study/java/freamework-study/mybaties/src/main/java/com/mybaties/demo/mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(fis);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        User user = mapper.selectByPrimary(2);

        System.out.println(user.toString());
    }


    /**
     * 通过接口形式调用
     */
    @Test
    public void TestUpdate() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("/Applications/study/java/freamework-study/mybaties/src/main/java/com/mybaties/demo/mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(fis);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        User updateUser = new User();
        updateUser.setId(1);
        updateUser.setName("天地三号");
        mapper.updateUser(updateUser);

        User user = mapper.selectByPrimary(1);
        System.out.println(user.toString());
        sqlSession.commit();
    }

    /**
     * 通过statement 直接调用方法
     * @throws FileNotFoundException
     */
    @Test
    public void Test2() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("/Applications/study/java/freamework-study/mybaties/src/main/java/com/mybaties/demo/mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(fis);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user  =  sqlSession.selectOne("com.mybaties.demo.TestMapper.selectByPrimary",1);

        System.out.println(user.toString());
    }
}
