package com.example.struct.simplefatory;

import com.example.struct.bean.Animal;
import com.example.struct.bean.Dog;
import com.example.struct.bean.Pig;

/**
 * lingfan
 * 2019-05-28 16:11
 */
public class AnimaFatory {

    public static Animal getAnimal(String name){
        switch (name){
            case "dog":
                //do something
                return  new Dog();
            case "pig":
                //do something
                return new Pig();
            default :
                    return null;
        }
    }
}
