package com.example.struct.fatorymethod;

import com.example.struct.bean.Animal;
import com.example.struct.bean.Dog;

/**
 * lingfan
 * 2019-05-28 17:14
 */
public class IDogFatory implements IAnimalFatory{
    @Override
    public Animal create() {
        //do something
        return  new Dog();
    }
}
