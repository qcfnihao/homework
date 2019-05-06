package framework.beans.support;

import framework.beans.config.GPBeanDefinition;
import org.springframework.core.io.Resource;

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

    public GPBeanDefinitionReader(String... locations){
        //通过URL定位找到其对应的文件，然后转换为文件流读取
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScan(config.get(SCAN_PACKAGE));
    }

    public Properties getConfig(){
        return this.config;
    }

    private void doScan(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll(""))
    }

    public List<GPBeanDefinition> loadBeanDefinitions(){



        return null;
    }
}
