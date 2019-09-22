package com.mybaties.v1;

public interface Executor {
    <T> T selectOne(String statement, String parameter);
}
