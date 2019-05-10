package framework.aop;

import framework.aop.intercept.GPMethodInvocation;
import framework.aop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class GPJdkDynamicAopProxy implements  GPAopProxy, InvocationHandler {

    private GPAdvisedSupport config;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config) {
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.config.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.config.getInterceptorsAndDynamicInterceptionAdvice(method,this.config.getTargetClass());
        GPMethodInvocation invocation = new GPMethodInvocation(proxy,method,this.config.getTarget(),args,interceptorsAndDynamicMethodMatchers,this.config.getTargetClass());
        return invocation.proceed();
    }
}
