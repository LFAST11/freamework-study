
# 原型模式

## 定义
  用原型实例指定创建对象的种类，并且通过拷贝这些原型创建对象。
  
  简单理解就是，我定义了一个对象，并且进行了一些初始化，然后我想要同样的这样一个对象，又不能直接使用愿对象，那么可以通过clone的方式来实现。
  
## 克隆的类型

### 浅拷贝
  只复制对象的第一层属性，如果对象的属性是对象，那么会存在共用的情况，比如，Object的自带的clone方法
  
### 深拷贝
  对于对象里的每一层属性都会进行重新复制，生成一个新的对象。比如：序列化和反序列，Json，BeanUtils等
  
 