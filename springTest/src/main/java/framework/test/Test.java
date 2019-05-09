package framework.test;

import demo.action.MyAction;
import demo.service.IModifyService;
import demo.service.impl.ModifyService;
import framework.context.GPApplicationContext;

public class Test {

    public static void main(String[] args) throws Exception {
        GPApplicationContext gpApplicationContext = new GPApplicationContext("application.properties");
        IModifyService iModifyService = (IModifyService) gpApplicationContext.getBean(IModifyService.class);
        System.out.println(iModifyService);
    }
}
