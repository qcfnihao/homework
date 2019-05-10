package framework.aop.aspect;

import framework.aop.intercept.GPMethodInterceptor;
import framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPAfterThrowingAdviceInterceptor extends GPAbstractAspectAdvice implements GPMethodInterceptor {

    private String throwingName;

    public GPAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }

    @Override
    public Object invoke(GPMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(invocation,null,e.getCause());
            throw e.getCause();
        }
    }
}
