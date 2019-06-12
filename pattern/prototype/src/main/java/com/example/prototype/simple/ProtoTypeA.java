package com.example.prototype.simple;

import lombok.Data;

/**
 * lingfan
 * 2019-06-06 11:12
 */
@Data
public class ProtoTypeA  implements  Prototype{

    private String name;

    private int age;

    private String sex;

    @Override
    public Prototype clone() {
        ProtoTypeA protoTypeA = new ProtoTypeA();
        protoTypeA.setAge(this.age);
        protoTypeA.setName(this.name);
        protoTypeA.setSex(this.sex);
        return protoTypeA;
    }
}
