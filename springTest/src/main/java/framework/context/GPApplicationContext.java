package framework.context;

import framework.annotation.GPAutowired;
import framework.annotation.GPController;
import framework.annotation.GPService;
import framework.beans.GPBeanFactory;
import framework.beans.config.GPBeanDefinition;
import framework.beans.support.GPBeanDefinitionReader;
import framework.beans.support.GPDefaultListableBeanFactory;
import framework.beans.GPBeanWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 按之前源码分析的套路 IOC、DI、MVC、AOP
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader;

    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    private Map<String, GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public GPApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    public Object getBean(String beanName) {
        //初始化
        GPBeanDefinition gpBeanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        instance = instantiateBean(beanName, gpBeanDefinition);
        //拿到beanwrapper之后，把beanwrapper存到容器
        GPBeanWrapper beanWrapper = new GPBeanWrapper(instance);
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);
        //注入
        populateBean(beanName, gpBeanDefinition, beanWrapper);
        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, GPBeanDefinition gpBeanDefinition, GPBeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();
        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if (!(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class))) {
            return;
        }
        Field[] fileds = clazz.getDeclaredFields();
        for (Field filed : fileds) {
            if (!filed.isAnnotationPresent(GPAutowired.class)) {
                continue;
            }
            GPAutowired gpAutowired = filed.getAnnotation(GPAutowired.class);
            String fieldName = gpAutowired.value();
            if ("".equals(fieldName)) {
                fieldName = filed.getType().getName();
            }
            filed.setAccessible(true);
            try {
                GPBeanWrapper fieldBeanWrapper = this.factoryBeanInstanceCache.get(fieldName);
                if(fieldBeanWrapper!=null){
                    filed.set(instance, fieldBeanWrapper.getWrappedInstance());
                }
                System.out.println("fieldName:" + fieldName + ",Object=" + fieldBeanWrapper);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, GPBeanDefinition gpBeanDefinition) {
        //拿到要实例化的类名，
        String className = gpBeanDefinition.getBeanClassName();
        // 反射实例化，实例化之后得到一个对象，
        Object instance = null;
        try {
            if (this.factoryBeanObjectCache.containsKey(className)) {
                instance = this.factoryBeanObjectCache.get(className);
            } else {
                instance = Class.forName(className).newInstance();
                this.factoryBeanObjectCache.put(className, instance);
                this.factoryBeanObjectCache.put(gpBeanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public void refresh() throws Exception {
        //1.定位，定位配置文件
        reader = new GPBeanDefinitionReader(this.configLocations);
        //2.加载，加载配置文件，扫描相关的类，把它封装成beanDefinition
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3.注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //4.把不是延时加载的类提前初始化
        doAutoWired();
    }

    //只处理非延时加载的情况
    private void doAutoWired() {

        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) throws Exception {

        for (GPBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    public String[] getDeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }
}





