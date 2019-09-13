package com.example.spring2.formwork.aop.config;

import lombok.Data;

/**
 * lingfan
 * 2019-09-08 10:24
 */
@Data
public class AopConfig {

    private String pointCut;

    private String aspectBefore;

    private String aspectAfter;

    private String aspectClass;

    private String aspectAfterThrow;

    private String aspectAfterThrowingName;
}
