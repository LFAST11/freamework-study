package com.example.spring2.formwork.context;

import com.example.spring2.formwork.annotation.DiyAutowired;
import com.example.spring2.formwork.annotation.DiyController;
import com.example.spring2.formwork.annotation.DiyService;
import com.example.spring2.formwork.aop.AopProxy;
import com.example.spring2.formwork.aop.CglibAopProxy;
import com.example.spring2.formwork.aop.JdkDynamicAopProxy;
import com.example.spring2.formwork.aop.config.AopConfig;
import com.example.spring2.formwork.aop.support.AdvisedSupport;
import com.example.spring2.formwork.beans.BeanWrapper;
import com.example.spring2.formwork.beans.config.BeanDefinition;
import com.example.spring2.formwork.beans.config.BeanPostProcessor;
import com.example.spring2.formwork.beans.factory.ObjectFactory;
import com.example.spring2.formwork.beans.support.BeanDefinitionReader;
import com.example.spring2.formwork.beans.support.DefaultListableBeanFactory;
import com.example.spring2.formwork.core.BeanFactory;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lingfan
 * 2019-08-19 15:20
 */
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private String[] locations;

    private BeanDefinitionReader beanDefinitionReader;


    //封装的代理对象
    private Map<String, BeanWrapper> factoryBeanInstanceCaches = new ConcurrentHashMap<String, BeanWrapper>();

    //单例的ioc容器
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** 二级缓存，提前曝光， 尚未填充属性 */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /** 单例对象的cache，存放bean工厂对象 Cache of singleton factories: bean name --> ObjectFactory */
    private final Map<String, ObjectFactory> singletonFactories = new HashMap<>(16);




    //正在创建的对象集合
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));



    public ApplicationContext(String ... configLocations){
        this.locations = configLocations;

        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void refresh() throws Exception {
        //定位
        beanDefinitionReader = new BeanDefinitionReader(locations);

        //加载
        List<BeanDefinition> beanDefinitions = beanDefinitionReader.loadBeanDefinitions();

        //注册，把bean的定义信息注册到伪ioc容器
        doRegistryBeanDefinition(beanDefinitions);


        //对于需要立即加载的bean，先行初始化
        doAutowired();
    }

    private void doAutowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doRegistryBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("this "+beanDefinition.getFactoryBeanName()+"is is exists ");
            }else{
                super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
            }
        }
    }

    /**
     * 这里是实现DI的功能部分
     * DI的实现步骤，主要为2个，初始化实例和注入属性
     * @param beanName
     * @return
     * @throws Exception
     */
    public Object getBean(String beanName) throws Exception {
        //先从缓存中拿数据
        Object shareInstance = getSingleton(beanName);

        if(shareInstance != null) return shareInstance;

        //放入到正在创建的缓存中
        markBeanAsCreated(beanName);


        Object bean = creatBean(beanName);


        bean = getSingleton(beanName,new ObjectFactory(bean));

        return bean;
    }


    public Object getSingleton(String beanName, ObjectFactory singletonFactory) {
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {

                boolean newSingleton = false;
                try {
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                }
                catch (IllegalStateException ex) {
                    // Has the singleton object implicitly appeared in the meantime ->
                    // if yes, proceed with it since the exception indicates that state.
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        throw ex;
                    }
                }

                if (newSingleton) {
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    //将二级缓存放入到一级缓存
    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    private Object creatBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = super.beanDefinitionMap.get(beanName);

        //初始化实例
        BeanWrapper instanceWrapper = createBeanInstance(beanName,beanDefinition);
        Object instance = instanceWrapper.getWrapperInstance();


        //添加到3级缓存中
        addSingletonFactory(beanName,new ObjectFactory(instance));

        //注入
        populateBean(beanName,new BeanDefinition(),instanceWrapper);

        Object exposedObject = initializeBean(beanName, instance);


        //移除正常创建的bean
        if (!this.alreadyCreated.contains(beanName)) {
            removeSingleton(beanName);
            return true;
        }
        return exposedObject;
    }


    protected void addSingletonFactory(String beanName, ObjectFactory singletonFactory) {
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
                //this.registeredSingletons.add(beanName);
            }
        }
    }


    protected void markBeanAsCreated(String beanName) {
        if (!this.alreadyCreated.contains(beanName)) {
            if (!this.alreadyCreated.contains(beanName)) {
                // Let the bean definition get re-merged now that we're actually creating
                // the bean... just in case some of its metadata changed in the meantime.
                this.alreadyCreated.add(beanName);
            }
        }
    }


    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            //this.registeredSingletons.remove(beanName);
        }
    }


    //返回代理对象
    private Object initializeBean(String beanName, Object instance) {
        BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

        beanPostProcessor.postPorcessorBeforeInitialization(instance,beanName);


        //this.factoryBeanInstanceCaches.put(beanName,beanWrapper);

        beanPostProcessor.postPorcessorAfterInitialization(instance,beanName);

        return instance;
    }




    private BeanWrapper createBeanInstance(String beanName,BeanDefinition beanDefinition) {
        BeanWrapper beanWrapper = instantiateBean(beanName, beanDefinition);
        return beanWrapper;
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) throws Exception {
        Object instance = beanWrapper.getWrapperInstance();


        Class<?> instanceClass = instance.getClass();
        //只要加了注解的才需要注入
        if(!(instanceClass.isAnnotationPresent(DiyController.class) || instanceClass.isAnnotationPresent(DiyService.class))){
            return ;
        }

        Field[] fields = instanceClass.getDeclaredFields();

        for (Field field : fields) {
            if(!field.isAnnotationPresent(DiyAutowired.class)) continue;

            DiyAutowired annotation = field.getAnnotation(DiyAutowired.class);

            String value = annotation.value().trim();

            if(value.equals("")){
                value = field.getType().getName();
            }

            field.setAccessible(true);


            Object bean = this.getBean(value);
            try {
                field.set(instance,bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    private BeanWrapper  instantiateBean(String beanName, BeanDefinition beanDefinition) {
        //1.得到类名
        String className = beanDefinition.getClassName();

        //2.反射实例化，获取对象
        Object instance = null;
        try{
            if(singletonObjects.containsKey(beanName)){
                instance = singletonObjects.get(beanName);
            }else{
                Class<?> aClass = Class.forName(className);
                instance = aClass.newInstance();

                //放入到ioc容器中
//                this.singletonObjects.put(beanName,instance);
//                this.singletonObjects.put(beanDefinition.getFactoryBeanName(),instance);

                //判断是否是否满足aop条件，
//                AdvisedSupport config = initAopConfig(beanDefinition);
//                config.setTarget(instance);
//                config.setTargetClass(aClass);
//                config.parse();
//
//
//                if(config.pointCutMatch()){
//                    instance = createProxy(config).getProxy();
//                }

            }
            BeanWrapper bw = new BeanWrapper(instance);

            return bw;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private AopProxy createProxy(AdvisedSupport config) {

        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }

    private AdvisedSupport initAopConfig(BeanDefinition beanDefinition) {

        AopConfig config = new AopConfig();
        config.setPointCut(this.beanDefinitionReader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.beanDefinitionReader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.beanDefinitionReader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.beanDefinitionReader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.beanDefinitionReader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.beanDefinitionReader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }


    @Nullable
    protected Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null ) {
                   Object bean = this.singletonFactories.get(beanName);
                    if (bean != null) {
                        singletonObject = bean;
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    private boolean isSingletonCurrentlyInCreation(String beanName) {

        return singletonsCurrentlyInCreation.contains(beanName);
    }


    public <T> T getBean(Class<T> requiredType) throws Exception {
        return null;
    }

    public String[] getBeanDefinitionName(){
        return super.beanDefinitionMap.keySet().toArray(new String[super.beanDefinitionMap.size()]);
    }

    public Properties getconfig(){
       return  this.beanDefinitionReader.getConfig();
    }
}
