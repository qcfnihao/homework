package framework.beans.support;

import framework.beans.config.GPBeanDefinition;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    //固定配置文件的key
    private final String SCAN_PACKAGE = "scanPackage";

    public GPBeanDefinitionReader(String... locations) {
        //通过URL定位找到其对应的文件，然后转换为文件流读取
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath", ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScan(config.getProperty(SCAN_PACKAGE));
    }

    public Properties getConfig() {
        return this.config;
    }

    private void doScan(String scanPackage) {
        URL url = this.getClass()//.getClassLoader()
                .getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                registryBeanClasses.add(className);
            }
        }

    }

    //把配置文件中扫描到的所有的配置信息转换为GPBeanDefinition 对象，以便于之后IOC 操作方便
    public List<GPBeanDefinition> loadBeanDefinitions() {
        List<GPBeanDefinition> beanDefinitions = new ArrayList<GPBeanDefinition>();
        try {
            for (String registryBeanClass : registryBeanClasses) {
                Class<?> beanClass = Class.forName(registryBeanClass);
                if (beanClass.isInterface()) {
                    continue;
                }
                beanDefinitions.add(doCreateBeanDefinition(registryBeanClass, toLowerFirstCase(beanClass.getSimpleName())));

                Class<?>[] interfaces =  beanClass.getInterfaces();
                for (Class<?>  i : interfaces) {
                    beanDefinitions.add(doCreateBeanDefinition(registryBeanClass,i.getName()));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }

    //把配置信息解析成BeanDefinition
    private GPBeanDefinition doCreateBeanDefinition(String registryBeanClass, String factoryBeanName) {
        GPBeanDefinition beanDefinition = new GPBeanDefinition();
        beanDefinition.setBeanClassName(registryBeanClass);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    //如果类名本身是小写字母，确实会出问题
    //但是我要说明的是：这个方法是我自己用，private 的
    //传值也是自己传，类也都遵循了驼峰命名法
    //默认传入的值，存在首字母小写的情况，也不可能出现非字母的情况
    //为了简化程序逻辑，就不做其他判断了，大家了解就OK
    //其实用写注释的时间都能够把逻辑写完了
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII 码相差32，
        // 而且大写字母的ASCII 码要小于小写字母的ASCII 码
        //在Java 中，对char 做算学运算，实际上就是对ASCII 码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
