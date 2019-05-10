package framework.aop.aspect;

import framework.aop.intercept.GPMethodInterceptor;
import framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPMethodAroundAdviceInterceptor extends  GPAbstractAspectAdvice implements GPMethodInterceptor {


    public GPMethodAroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation invocation) throws Throwable {
        return super.invokeAdviceMethod(invocation,null,null);
    }
}
