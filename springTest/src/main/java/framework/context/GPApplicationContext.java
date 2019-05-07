package framework.context;

import framework.beans.GPBeanFactory;
import framework.beans.config.GPBeanDefinition;
import framework.beans.support.GPBeanDefinitionReader;
import framework.beans.support.GPDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

/**
 * 按之前源码分析的套路 IOC、DI、MVC、AOP
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader;

    public GPApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
    }

    public Object getBean(String beanName) {
        //初始化

        //注入

        return null;
    }

    @Override
    public void refresh() {
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
            if (beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) {

        for (GPBeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }
}





