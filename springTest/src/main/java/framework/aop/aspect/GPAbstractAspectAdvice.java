package framework.aop.aspect;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class GPAbstractAspectAdvice implements GPAdvice {

    private Method aspectMethod;
    private Object aspectTarget;

    public GPAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(GPJoinPoint joinPoint, Object returnValue, Throwable tx)throws Throwable{
        Class<?>[] paramTypes =this.aspectMethod.getParameterTypes();
        if(paramTypes==null || paramTypes.length ==0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            int i = 0;
            Object [] args = new Object[paramTypes.length];
            for (Class<?> clazz:paramTypes) {
                if(clazz == GPJoinPoint.class){
                    args[i] = joinPoint;
                }else if(clazz == Throwable.class){
                    args[i] = tx;
                }else if(clazz == Object.class) {
                    args[i] = returnValue;
                }
                i++;
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }


    }
}
