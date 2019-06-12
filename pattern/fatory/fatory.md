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


## 工厂方法模式

 ![image_text](https://github.com/LFAST11/freamework-study/blob/master/pattern/fatory/fatoryMethod.jpg)
 
 不同的子类工厂生成不同的子类
 
 
## 抽象工厂模式
 ![image_text](https://github.com/LFAST11/freamework-study/blob/master/pattern/fatory/abstratFatory.jpg)

一个工厂生成一个产品族，一次性创建多个属性或者对象


