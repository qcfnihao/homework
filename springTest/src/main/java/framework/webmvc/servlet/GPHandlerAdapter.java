package framework.webmvc.servlet;

public class GPHandlerAdapter {

    public boolean supports(Object handler){ return (handler instanceof GPHandlerMapping);}
}
