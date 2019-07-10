package com.example.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lingfan
 * 2019-07-08 16:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgResult {

    private int code;
    private Object data;
    private String msg;


    @Override
    public String toString() {
        return "支付状态:["+code+"],交易详情["+msg+"],返回数据:"+data;
    }
}
