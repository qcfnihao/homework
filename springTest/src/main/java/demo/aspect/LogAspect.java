package demo.aspect;


import framework.aop.aspect.GPJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;



public class LogAspect {

    //private Logger log = LoggerFactory.getLogger(LogAspect.class);

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object logAspect = Class.forName("demo.aspect.LogAspect").newInstance();
        System.out.println(logAspect.getClass());
    }

    //在调用一个方法之前，执行before方法
    public void before(GPJoinPoint joinPoint){
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(),System.currentTimeMillis());
        //这个方法中的逻辑，是由我们自己写的
        System.out.println("Invoker Before Method!!!" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
    }
    //在调用一个方法之后，执行after方法
    public void after(GPJoinPoint joinPoint){
        System.out.println("Invoker After Method!!!" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()));
        long startTime = (Long) joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        long endTime = System.currentTimeMillis();
        System.out.println("use time :" + (endTime - startTime));
    }

    public void afterThrowing(GPJoinPoint joinPoint, Throwable ex) {
        System.out.println("出现异常" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()) +
                "\nThrows:" + ex.getMessage());

    }

}
