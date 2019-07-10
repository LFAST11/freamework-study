
# 代理模式

## 静态代理

代理对象直接注入目标对象，对目标对象的方法进行包装

* 优点：实现简单
* 缺点：不符合开闭原则，如果目标类增加方法时，代理类也需要修改
```
public class Father {

    private Boy boy;

    public Father(Boy boy){
        this.boy = boy;
    }

    public void findLove(){
        System.out.println("给儿子找对象");
        boy.findLove();
        System.out.println("办事情");
    }
}
```

## 动态代理

### jdk动态代理

通过实现jdk自带的InvacationHandle接口,完成动态代理

* 缺点：目标类必须实现接口

```
public class ZhenAiProxy implements InvocationHandler {

    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        Class<?> targetClass = target.getClass();
        return Proxy.newProxyInstance(targetClass.getClassLoader(),targetClass.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object = method.invoke(target, args);
        after();
        return object;
    }


    public void before(){
        System.out.println("先自我介绍一下吧");
    }

    public void after(){
        System.out.println("交换微信吧");
    }
}
```

### 手动实现代理模式

自定义代理类，动态生成扩展之后的类的代码，生成java文件，编译成class文件，加载类到内存。调用对应的方法

代理类
```
 public static Object newProxyInstance(DiyClassLoader loader,
                                          Class<?>[] interfaces,
                                          DiyInvationHandle h)
            throws Exception{

        //动态生成源码
        String code = generateCode(interfaces);

        //写入到文件
        String path = DiyProxy.class.getResource("").getPath();

        File file = new File(path,"$Proxy0.java");
        FileWriter fw = new FileWriter(file);
        fw.write(code);
        fw.flush();
        fw.close();

        //将java文件编译成class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
        Iterable<? extends JavaFileObject> fileObjects = manager.getJavaFileObjects(file);

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, fileObjects);
        task.call();
        manager.close();

        //将class文件加载到内存
        Class<?> proxyClass = loader.findClass("$Proxy0");
        Constructor<?> constructor = proxyClass.getConstructor(DiyInvationHandle.class);
        file.delete();
        return constructor.newInstance(h);
    }
```

类加载器
```
 @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = DiyClassLoader.class.getPackage().getName() + "." + name;

        if (classPathFile != null){
            File file = new File(classPathFile,name.replace("\\,","/") + ".class");

            if(file.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream bios = null;
                try{
                    fis  = new FileInputStream(file);
                    bios = new ByteArrayOutputStream();

                    byte[] bytes = new byte[1024];
                    int len;
                    while((len = fis.read(bytes)) !=-1){
                        bios.write(bytes,0,len);
                    }

                    return defineClass(className,bios.toByteArray(),0,bios.size());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }
```


### cglib代理

原理：对目标类动态生成子类，对目标类的方法进行重写操作，增强对应方法

* 优点：除了final类型的类，其他所有类型的类都能被代理

```
public class CglibProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz){
        //代理工具类
        Enhancer eh = new Enhancer();
        eh.setSuperclass(clazz);
        eh.setCallback(this);
        return eh.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object Obj = methodProxy.invokeSuper(o, objects);
        after();
        return Obj;
    }

    public void before(){
        System.out.println("进来之前要洗手");
    }

    public void after(){
        System.out.println("出去之后要擦鞋");
    }

}
```

### jdk代理和cglib代理的比较
1. jdk代理是实现了目标对象的接口，cglib代理是继承了目标对象
2. jdk代理和cglib代理都是在运行期生成字节码，Jdk代理是直接生成class字节码，cglib是通过asm生成字节码。
cglib的实现原理更加复杂，生成代理类的效率比jdk更低
3. jdk调用的代理方法，是通过反射机制调用，cglib是通过FastClass机制直接调用方法，cglib执行效率更高


  
## 代理模式在spring中的体现

spring中的AopProxy的两个实现类CglibAopProxy 和JdkDynamicAopProxy 来完成spring中的代理功能
![image_text](https://github.com/LFAST11/freamework-study/blob/master/pattern/proxy/企业微信截图_d14ee5ce-2a29-4871-b801-2cdea2537a57.png)
选择：
如果目标类实现了接口，优先使用jdk代理
如果目标类没有实现接口，使用cglib代理



## 代理模式的优缺点
* 优点
  1. 代理模式能够将代理对象和目标对象隔壁
  2. 一定程度上降低系统的耦合性
  3. 保护目标对象
  4.增强目标对象的功能
* 缺点
  1. 代理对象增加了类的数量
  2. 增加了系统的复杂度
  3. 在客户端和目标对象中增加代理对象，一定程度下影响速度
   
   
 ## 为什么代理对象的个数不能超过65535
 
 1. jvm虚拟机限制实现接口的数量最多不能超过65535
 2. 由于java 使用unicode(ucs-2)标准字符集，为16位，因此一共能表示2的16次方个字符，即65535
 3. 类和接口的子类数量被ClassFIle结构的interface_count项大小限制为65535
 4. java类或接口最多可以有65535中方法，java中构造函数的代码限制为65535字节

 
 
 
 
