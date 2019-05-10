package demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;


import demo.aspect.LogAspect;
import demo.service.IQueryService;
import framework.annotation.GPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查询业务
 *
 *
 */
@GPService
public class QueryService implements IQueryService {

	//private Logger log = LoggerFactory.getLogger(QueryService.class);

	/**
	 * 查询
	 */
	public String query(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
	//	log.info("这是在业务方法中打印的：" + json);
		return json;
	}

}
