package framework.context;

import framework.beans.GPBeanFactory;
import framework.beans.config.GPBeanDefinition;
import framework.beans.support.GPBeanDefinitionReader;
import framework.beans.support.GPDefaultListableBeanFactory;

import java.util.List;

/**
 * 按之前源码分析的套路 IOC、DI、MVC、AOP
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader ;

    public GPApplicationContext(String... configLocations){
        this.configLocations = configLocations;
    }

    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public void refresh() {
        //1.定位，定位配置文件
        reader =  new GPBeanDefinitionReader(this.configLocations);
        //2.加载，加载配置文件，扫描相关的类，把它封装成beanDefinition
        List<GPBeanDefinition> beanDefinitions =  reader.loadBeanDefinitions();
        //3.注册，把配置信息放到容器里面(伪IOC容器)
       doRegisterBeanDefinition(beanDefinitions);
        //4.把不是延时加载的类提前初始化
        doAutoWired();
    }

    private void doAutoWired() {
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) {
    }
}
