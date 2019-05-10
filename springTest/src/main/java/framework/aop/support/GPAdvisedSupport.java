package framework.aop.support;

import framework.aop.aspect.GPAfterReturningAdviceInterceptor;
import framework.aop.aspect.GPAfterThrowingAdviceInterceptor;
import framework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import framework.aop.config.GPAopConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Method;
import java.util.Map;


public class GPAdvisedSupport {

    private Class targetClass;

    private Object target;

    private Pattern pointCutClassPattern;

    private transient Map<Method, List<Object>> methodCache;

    private GPAopConfig config;

    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        //pointCut 表达式
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));
        try {
            methodCache = new HashMap<>();
            Pattern pattern = Pattern.compile(pointCut);
            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(), m);
            }
            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    //执行器链
                    List<Object> advices = new LinkedList<>();
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        // 创建一个Advivce
                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),
                                aspectClass.newInstance()));
                    }
// after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        // 创建一个Advivce
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),
                                aspectClass.newInstance()));
                    }
                    // afterThrowing
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        // 创建一个Advivce
                        GPAfterThrowingAdviceInterceptor throwingAdvice = new GPAfterThrowingAdviceInterceptor(
                                aspectMethods.get(config.getAspectAfterThrow()), aspectClass.newInstance());
                        throwingAdvice.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(m, advices);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {

        List<Object> cache = methodCache.get(method);

        if (cache == null) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cache = methodCache.get(m);
            this.methodCache.put(m, cache);
        }
        return cache;
    }


    public boolean pointCutMathch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
