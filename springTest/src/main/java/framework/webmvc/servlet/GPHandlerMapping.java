package framework.webmvc.servlet;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Data
public class GPHandlerMapping {

    private Object Controller;

    private Method method;

    private Pattern pattern;

    public GPHandlerMapping(Object controller, Method method, Pattern pattern) {
        Controller = controller;
        this.method = method;
        this.pattern = pattern;
    }
}
