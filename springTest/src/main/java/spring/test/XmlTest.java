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
     *  BeanDefinition --bean的定义类，包换bean的class名称、范围、依赖关系等
     *  BeanFactory    --bean的超类工厂
     *  BeanWrapper    --bean的封装类
     *
     *
     */


    /**
     * ioc 容器初始化步骤  定位-加载(loadBeanDefinitions开始)-注册(registerBeanDefinitions开始)
     *   setConfigLocations(configLocations); -->处理一下configLocations  存到AbstractRefreshableConfigApplicationContext.configLocations 里
     *   AbstractApplicationContext.refresh() --> 容器的初始化方法
     *      obtainFreshBeanFactory -->创建 BeanFactory
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
     *
     *   DI运行步骤 (延时加载的对象)  实例化对象--依赖注入
     *   BeanFactory.getBean                        ->延时加载获取对象的入口方法
     *      AbstractBeanFactory.doGetBean           ->getBean方法执行
     *          AbstractAutowireCapableBeanFactory.createBean  -->创建Bean
     *              AbstractAutowireCapableBeanFactory.doCreateBean  --> 创建bean方法执行
     *                  AbstractAutowireCapableBeanFactory.instantiateBean  -->实例化bean
     *                      SimpleInstantiationStrategy.instantiate         -->策略模式-按照策略进行实例化 1.如果没有重写方法用cglib动态代理,有重写方法用jdk动态代理,
     *              `   AbstractAutowireCapableBeanFactory.populateBean     -->封装bean
     *                      AbstractAutowireCapableBeanFactory.applyPropertyValues  -->管理propertyValues
     *                          BeanDefinitionValueResolver.resolveValueIfNecessary   -->分解必要的propertyValue
     *                          AbstractPropertyAccessor.setPropertyValues                    -->设置propertyValues
     *                              AbstractNestablePropertyAccessor.setPropertyValue         -->设置单个propertyValue
     *                                  AbstractNestablePropertyAccessor.processLocalProperty  -->处理本地变量
     *                                      BeanPropertyHandler.setValue                       -->利用反射实现设置变量 BeanPropertyHandler是BeanWrapperImpl的内部类
     *
     *
     *
     *
     *
     *
     *
     *
     */

    }
}
