package com.example.proxy.staticproxy;

/**
 * lingfan
 * 2019-06-14 16:03
 */
public class Father {

    private Boy boy;

    public Father(Boy boy) {
        this.boy = boy;
    }

    public void findLove() {
        System.out.println("给儿子找对象");
        boy.findLove();
        System.out.println("办事情");
    }
}
