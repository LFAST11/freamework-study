
# 单例模式

## 基本实现方法
1. 构造方式私有化
2. 实例私有化
3. 提供一个公有的反问方法


## 饿汉式
``` java
public class HungrySingleton {

    private volatile  static  HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton(){ }

    public static HungrySingleton getInstance(){
        return singleton;
    }

}
```
* 优点：简单粗暴，线程安全
* 缺点：浪费内存，造成不必要的浪费

## 懒汉式

### 简单懒汉式
```java
public class LazyEasySingleton {
    
    private static LazyEasySingleton singleton;
    
    private LazyEasySingleton(){};
    
    public LazyEasySingleton getInstance(){
        if(singleton == null){
            return new LazyEasySingleton();
        }
        return singleton;
    }
}
```

* 优点：第一次使用的时候创建，减少不惜要的浪费
* 缺点：线程不安全

### 双重锁懒汉式

```java
public class LazySingleton {

    private static LazySingleton singleton = new LazySingleton();

    private LazySingleton(){ }

    public LazySingleton getInstance(){
        if(singleton == null){
            synchronized (LazySingleton.class){
                if(singleton == null ){
                    singleton = new LazySingleton();
                }
            }
        }
        return singleton;
    }
}
```
* 优点：使用时加载，线程安全
* 缺点：加锁影响性能

### 内部类方式

```java
public class InnerClassSingleton {

    private InnerClassSingleton(){};

    public static InnerClassSingleton getInstance(){
        return LazyHoder.instance;
    }
    static class LazyHoder{
        public static final  InnerClassSingleton instance = new InnerClassSingleton();
    }
}

```
* 优点：天生线程安全
* 缺点：第一次加载速度慢

## 注册式

### 枚举类型
``` java
public enum  EnumSingleton {
    INSTANCE;

    private Object data;

    EnumSingleton(){ }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static EnumSingleton getInstance(){
        return INSTANCE;
    }

}
```
* 优点：线程安全，推荐使用
* 缺点：无

### 容器注册式
``` java
public class ContainerSingleton {

    private final Map<String,Object> ioc = new ConcurrentHashMap();
    
    private  synchronized Object getBean(String className){
        if(!ioc.containsKey(className)){
            Object obj = null;
            try {
                obj = Class.forName(className).newInstance();
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ioc.get(className);
    }

}
```
* 优点：使用于大量单例对象的创建
* 缺点：依赖于容器
    
    
 ## 破坏单例的方式
 * 反射
  通过类型强行生产类对象
  解决方式：在私有构造器上增加空对象判断
 * 序列化，反序列化
  在序列化对象然后反序列化时，会重新生成新的对象
  可以在类中增加强制操作
 ```java
 public class SerilizableSingleton implements Serializable {

    private static  SerilizableSingleton instance = new SerilizableSingleton();

    private SerilizableSingleton(){

    }


    public static SerilizableSingleton getInstance(){
        return instance;
    }


    public Object readResolve(){
        return instance;
    }

}
 ```
 增加readResolve方法。
 虽然可以保证单例，但是其实在反序列化的时候仍然会新建一个无用的对象
 
 
 ## 内部类的执行逻辑
 
 ![image_text](https://github.com/LFAST11/freamework-study/blob/master/pattern/singleton/inner.jpg)
 
