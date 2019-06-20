package com.example.proxy.dynamicproxy.diyproxy;
import com.example.proxy.dynamicproxy.jdkproxy.Person;
import java.lang.reflect.*;
public final class $Proxy0 implements com.example.proxy.dynamicproxy.jdkproxy.Person{
    DiyInvationHandle h;
    public $Proxy0(DiyInvationHandle h){this.h = h ;
    }
    public void findLove ( ){
        try{
            Method m = com.example.proxy.dynamicproxy.jdkproxy.Person.class.getMethod("findLove",new Class[]{});
            this.h.invoke(this,m,new Object[]{});
        }catch(Exception e){
    }catch(Throwable e){
        throw new UndeclaredThrowableException(e);
    }}
    public java.lang.Object fallInLove (java.lang.Object object0,java.lang.Object object1 ){
        try{
            Method m = com.example.proxy.dynamicproxy.jdkproxy.Person.class.getMethod("fallInLove",new Class[]{java.lang.Object.class ,java.lang.Object.class });
            return this.h.invoke(this,m,new Object[]{object0,object1});
        }catch(Exception e){
    }catch(Throwable e){
        throw new UndeclaredThrowableException(e);
        }return null;}
        }
