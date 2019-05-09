package framework.webmvc.servlet;


import framework.annotation.GPController;
import framework.annotation.GPRequestMapping;
import framework.context.GPApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class GPDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    private List<GPHandlerMapping> handlerMappings;

    private Map<GPHandlerMapping,GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping,GPHandlerAdapter>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = new GPApplicationContext(CONTEXT_CONFIG_LOCATION);
        //初始化9大组件
        initStrategies(context);
    }

    private void initStrategies(GPApplicationContext context) {
        //多文件上传
        initMultipartResolver(context);
        //本地语言
        initLocaleResolver(context);
        //主题模板
        initThemeResolver(context);
        //handleMapping，必须实现
        initHandlerMappings(context);
        //参数适配器，必须实现
        initHandlerAdapters(context);
        //异常拦截器
        initHandlerExceptionResolvers(context);
        //视图预处理
        initRequestToViewNameTranslator(context);
        //视图转换器，必须实现
        initViewResolvers(context);
        //参数缓存器、闪存
        initFlashMapManager(context);
    }

    private void initHandlerAdapters(GPApplicationContext context) {
       for(GPHandlerMapping handlerMapping:handlerMappings){
           this.handlerAdapters.put(handlerMapping,new GPHandlerAdapter());
       }
    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {
    }

    private void initViewResolvers(GPApplicationContext context) {
    }

    private void initFlashMapManager(GPApplicationContext context) {
    }

    private void initHandlerMappings(GPApplicationContext context) {

        String[] beanNames = context.getDeanDefinitionNames();

        try{
            for (String beanName:beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz =controller.getClass();
                if(!clazz.isAnnotationPresent(GPController.class)){continue;}
                String baseUrl ="";
                if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                    GPRequestMapping requestMapping =clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }
               Method[] methods = clazz.getMethods();
                for (Method method: methods) {
                    if(!method.isAnnotationPresent(GPRequestMapping.class)){continue;}
                    GPRequestMapping requestMapping =method.getAnnotation(GPRequestMapping.class);
                    String regex = ("/"+baseUrl+"/"+requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new GPHandlerMapping(controller,method,pattern));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initThemeResolver(GPApplicationContext context) {
    }

    private void initMultipartResolver(GPApplicationContext context) {
    }
    private void initLocaleResolver(GPApplicationContext context) {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
