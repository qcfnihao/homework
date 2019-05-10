package framework.aop;

import framework.aop.support.GPAdvisedSupport;

public class GPCglibAopProxy implements  GPAopProxy{

    private GPAdvisedSupport config;

    public GPCglibAopProxy(GPAdvisedSupport config) {
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
