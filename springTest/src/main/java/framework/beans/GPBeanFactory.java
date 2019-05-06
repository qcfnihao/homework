package framework.beans;

/**
 * 单例工厂的顶层设计
 */
public interface GPBeanFactory {

    /**
     * 根据beanName从容器中获取一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
