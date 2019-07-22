# 适配器模式

- 将一个接口转化成一个客户希望的接口，使接口不兼容的类能够一起工作，适配器模式既可以作为类结构模式，也可以作为对象结构模式

- 在程序的运行过程中，前期开发的功能需要兼容到后期开发里面，而由于设计的原因，可能两者之间的实现类互不相同。
这个时候可以通过适配器模式来达到转换功能


Target:目标调用类
Adaptee:被适配的类
adapter:适配器类



## 适配器模式和代理模式的区别

适配器模式属于结构性模式，用于结构转换

代理模式在于 在原有类的基础进行扩展增强。