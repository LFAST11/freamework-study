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

  
