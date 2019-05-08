package spring.test;

public class MyInterceptor {


    public void doAccessCheck(){
        System.out.println("这是一个前置通知");
    }

    public void doAfterReturing(){
        System.out.println("这是一个后置通知");
    }
}
