package com.example.adapter;

/**
 * lingfan
 * 2019-07-16 18:32
 */
public class TransferAdaper implements TypeCTarget {


    @Override
    public void inputData(Device device,String data) {
        if(device.support(device)){
            device.inputData(data,device);
        }
    }

    @Override
    public void outputData(Device device,String data) {
        if(device.support(device)){
            device.outData(data,device);
        }
    }
}
