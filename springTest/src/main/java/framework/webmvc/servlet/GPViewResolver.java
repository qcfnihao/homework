package framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

public class GPViewResolver {

    private final String Default_Template_Suffx = ".html";

    private File templateRootDir;

    public GPViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public GPView resolveViewName(String viewName, Locale locale) {
        if(null == viewName ||"".equals(viewName.trim())){return null;}
        viewName =viewName.endsWith(Default_Template_Suffx)?viewName:(viewName+Default_Template_Suffx);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new GPView(templateFile);
    }
}
