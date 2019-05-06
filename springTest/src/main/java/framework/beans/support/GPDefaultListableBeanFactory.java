package framework.beans.support;

import framework.beans.config.GPBeanDefinition;
import framework.context.support.GPAbstractApplicationContext;

import java.util.Map;

public class GPDefaultListableBeanFactory  extends GPAbstractApplicationContext {

    //存储注册信息的BeanDefinition
    private final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}
