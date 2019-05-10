package framework.aop.aspect;

import framework.aop.intercept.GPMethodInterceptor;
import framework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

public class GPAfterReturningAdviceInterceptor extends  GPAbstractAspectAdvice implements GPMethodInterceptor {

    private GPJoinPoint joinPoint;

    public GPAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void afterReturning(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,args,null);

    }

    @Override
    public Object invoke(GPMethodInvocation invocation) throws Throwable {
        Object result =  invocation.proceed();
        this.joinPoint = invocation;
        afterReturning(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return result;
    }
}
