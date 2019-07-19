package com.example.observer.event.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-07-19 16:21
 */
public class EventListener  {

    public Map<String ,Event> eventMap = new HashMap<>();

    public void addListener(String evenType,Object target){
        try{
            this.addListener(evenType,target,target.getClass().getMethod("on"+toUpperFirstCase(evenType),Event.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String toUpperFirstCase(String evenType) {
        char[] chars = evenType.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }

    private  void addListener(String eventType, Object target, Method callback){
        eventMap.put(eventType,new Event(target,callback));
    }

    public void trigger(Event event){
        event.setSource("");
        event.setTime(System.currentTimeMillis());
        try{
            if(event.getCallback() != null){
                event.getCallback().invoke(event.getTarget(),event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void trigger(String trigger){
        if(eventMap.containsKey(trigger)){
            this.trigger(eventMap.get(trigger));
        }
    }


}
