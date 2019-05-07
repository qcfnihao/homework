package framework.beans.config;

import lombok.Data;

@Data
public class GPBeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;


}
