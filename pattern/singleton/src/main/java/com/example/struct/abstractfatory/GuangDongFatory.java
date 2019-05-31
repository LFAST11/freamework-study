package com.example.struct.abstractfatory;

import com.example.struct.bean.Dog;
import com.example.struct.bean.GuangDongPig;
import com.example.struct.bean.JinMaoDog;
import com.example.struct.bean.Pig;

/**
 * lingfan
 * 2019-05-28 17:29
 */
public class GuangDongFatory implements IZooFatory {
    @Override
    public Pig createPig() {
        return new GuangDongPig();
    }

    @Override
    public Dog createDog() {
        return new JinMaoDog();
    }
}
