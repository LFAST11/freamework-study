package com.mybaties.demo;

import com.mybaties.v1.Score;

/**
 * lingfan
 * 2019-09-20 14:17
 */
public interface TestMapper {

     User selectByPrimary(Integer id);

     void updateUser(User user);
}
