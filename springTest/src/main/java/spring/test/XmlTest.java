package spring.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlTest {

    public static void main(String[] args) {
        //ioc容器初始化
        ClassPathXmlApplicationContext pathXmlApplicationContext = new ClassPathXmlApplicationContext("${path:spring.xml}");
        //DI 自动注入
        Student student = (Student) pathXmlApplicationContext.getBean("student");
        System.out.println(student.getTeacher().toString());


        /**
         * ioc 容器初始化步骤
         *
         *  1>.setConfigLocations(configLocations); -->处理一下configLocations  存到AbstractRefreshableConfigApplicationContext.configLocations 里
         *  2>.AbstractApplicationContext.refresh() --> 容器的初始化方法
         *     2.1>obtainFreshBeanFactory -->创建 BeanFactory
     *          AbstractRefreshableApplicationContext.refreshBeanFactory      -->创建BeanFactory
     *              AbstractRefreshableApplicationContext.loadBeanDefinitions     -->加载BeanDefinitions
     *              AbstractXmlApplicationContext.loadBeanDefinitions             -->重写AbstractRefreshableApplicationContext的loadBeanDefinitions方法
     *                  XmlBeanDefinitionReader.doLoadBeanDefinitions                 -->开始进行加载操作
     *                      DefaultBeanDefinitionDocumentReader.registerBeanDefinitions   -->注册BeanDefinitions
     *                          DefaultBeanDefinitionDocumentReader.doRegisterBeanDefinitions -->开始进行注册BeanDefinitions
     *                              BeanDefinitionParserDelegate.parseBeanDefinitionElement       -->开始分析BeanDefinitions元素
     *                              BeanDefinitionReaderUtils.registerBeanDefinition              -->开始注册BeanDefinitions
     *                                  DefaultListableBeanFactory.registerBeanDefinition              -->注册BeanDefinitions到beanDefinitionMap
     *         AbstractRefreshableApplicationContext.getBeanFactory       -->获取BeanFactory
         */

    }
}
