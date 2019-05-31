package com.example.struct.abstractfatory;

import com.example.struct.bean.Dog;
import com.example.struct.bean.HuNanPig;
import com.example.struct.bean.Pig;
import com.example.struct.bean.TaiDiDog;

/**
 * lingfan
 * 2019-05-28 17:28
 */
public class HuNanFatory implements IZooFatory {
    @Override
    public Pig createPig() {
        return new HuNanPig();
    }

    @Override
    public Dog createDog() {
        return new TaiDiDog();
    }
}
