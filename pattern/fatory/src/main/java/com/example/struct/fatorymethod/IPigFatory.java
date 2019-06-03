package com.example.struct.fatorymethod;

import com.example.struct.bean.Animal;
import com.example.struct.bean.Pig;

/**
 * lingfan
 * 2019-05-28 17:16
 */
public class IPigFatory implements IAnimalFatory {
    @Override
    public Animal create() {
        // do something
        return new Pig();
    }
}
