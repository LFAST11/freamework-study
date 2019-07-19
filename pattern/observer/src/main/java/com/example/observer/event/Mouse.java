package com.example.observer.event;

import com.example.observer.event.core.EventListener;

/**
 * lingfan
 * 2019-07-19 16:31
 */
public class Mouse extends EventListener {

    public void click(){
        System.out.println("调用单击方法");
        this.trigger(MouseEvenType.on_click);
    }

    public void doubleClick(){
        System.out.println("调用双击方法");
        this.trigger(MouseEvenType.on_double_clieck);
    }

    public void move(){
        System.out.println("调用鼠标移动方法");
        this.trigger(MouseEvenType.on_move);
    }

    public void up(){
        System.out.println("调用弹起方法");
        this.trigger(MouseEvenType.on_up);
    }

    public void down(){
        System.out.println("调用按下方法");
        this.trigger(MouseEvenType.on_down);
    }


}
