# 工厂模式

## 简单工厂模式

```java
public class AnimaFatory {

    public static Animal getAnimal(String name){
        switch (name){
            case "dog":
                //do something
                return  new Dog();
            case "pig":
                //do something
                return new Pig();
            default :
                    return null;
        }
    }
}
```
通过工厂类中的方法生成对应的类
结构简单，扩展性差，新增类型时，需要修改代码

## 工厂方法

  
