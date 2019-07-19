package com.example.template.cource;

/**
 * lingfan
 * 2019-07-19 15:06
 */
public abstract class NetCource {

    public final void ceateCource(){

        //1.发布预习资料
        pushRreResource();

        //2.制作ppt
        createPPT();

        //3.在线直播
        liveVideo();

        //4.提交课堂笔记
        postNote();

        //5.提交源码
        postSource();

        //这里子类可以通过覆盖判断方法，决定知否走支线流程
        //钩子方法
        if(needHomeWork()){
            checkHomeWork();
        }

    }

    protected abstract void checkHomeWork();

    protected  boolean needHomeWork(){
        return false;
    }

    protected  void postSource(){
        System.out.println("上传源码");
    }

    protected  void postNote(){
        System.out.println("发布击毙");
    }

    protected  void liveVideo(){
        System.out.println("直播哦");
    }

    protected  void createPPT(){
        System.out.println("制作ppt");
    }

    protected  void pushRreResource(){
        System.out.println("发布预习资料");
    }
}
