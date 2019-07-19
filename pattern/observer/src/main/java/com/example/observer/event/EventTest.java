package com.example.observer.event;

import java.awt.event.MouseEvent;

/**
 * lingfan
 * 2019-07-19 16:43
 */
public class EventTest {

    public static void main(String[] args) {
        MouseEventCallback callback = new MouseEventCallback();

        Mouse mouse = new Mouse();

        mouse.addListener(MouseEvenType.on_click,callback);

        mouse.addListener(MouseEvenType.on_double_clieck,callback);

        mouse.click();

        mouse.doubleClick();
    }

}
