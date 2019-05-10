package framework.webmvc.servlet;


import framework.annotation.GPController;
import framework.annotation.GPRequestMapping;
import framework.context.GPApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    private List<GPHandlerMapping> handlerMappings = new ArrayList<GPHandlerMapping>();;

    private Map<GPHandlerMapping, GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping, GPHandlerAdapter>();

    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = new GPApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
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
        for (GPHandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapters.put(handlerMapping, new GPHandlerAdapter());
        }
    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {
    }

    private void initViewResolvers(GPApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File file = new File(templateRootPath);
        String[] files = file.list();
        for (String f : files) {
            this.viewResolvers.add(new GPViewResolver(templateRoot));
        }
    }

    private void initFlashMapManager(GPApplicationContext context) {
    }

    private void initHandlerMappings(GPApplicationContext context) {

        String[] beanNames = context.getDeanDefinitionNames();

        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if (!clazz.isAnnotationPresent(GPController.class)) {
                    continue;
                }
                String baseUrl = "";
                if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                    GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                        continue;
                    }
                    GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new GPHandlerMapping(controller, method, pattern));
                }
            }
        } catch (Exception e) {
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
        try {
            doDispatcher(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }

    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1.通过从request中拿到URL,去匹配一个HandlerMapping
        GPHandlerMapping handler = getHandle(req);
        if (handler == null) {
            //异常输出
            processDispatchResult(req, resp, new GPModelAndView("404"));
            return;
        }
        //2.准备调用前的参数
        GPHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        //3、真正的调用方法,返回ModelAndView存储了要传页面上值，和页面模板的名称
        GPModelAndView mv = handlerAdapter.handle(req, resp, handler);

        //4.这一步才是真正的输出
        processDispatchResult(req, resp, mv);
    }

    private GPHandlerAdapter getHandlerAdapter(GPHandlerMapping handler) {
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        GPHandlerAdapter adapter = this.handlerAdapters.get(handler);
        if (adapter.supports(handler)) {
            return adapter;
        }
        return null;
    }

    private GPHandlerMapping getHandle(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (GPHandlerMapping handlerMapping : handlerMappings) {
            Pattern p = handlerMapping.getPattern();
            Matcher matcher = p.matcher(url);
            if (!matcher.matches()) {
                continue;
            }
            return handlerMapping;
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, GPModelAndView mv) throws  Exception{
        if (mv == null) {
            return;
        }
        if (this.viewResolvers.isEmpty()) {
            return;
        }
        for (GPViewResolver viewResolver : viewResolvers) {
            GPView view = viewResolver.resolveViewName(mv.getViewName(),null);
            view.render(mv.getModel(),req,resp);
            return;
        }
    }
}
