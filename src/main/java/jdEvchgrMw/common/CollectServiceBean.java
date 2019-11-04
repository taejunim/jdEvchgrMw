package jdEvchgrMw.common;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
/**
 * 
/**
 * @ Class Name  : CollectServiceBean.java
 * @ Description : SERVICE BEAN CLASS
 * @ Modification Information
 * @
 * @    수정일      		  수정자                 				수정 내용
 * @ ----------   ----------   -------------------------------
 * @ 2018.07.16             신호정    				         최초 생성
 *
 * @ since 
 * @ version 
 * @ see
 */
public class CollectServiceBean {
	
	/**
	 * ChgrInfoService Bean 생성
	 * @return
	 * @throws Exception
	 */
	public ChgrInfoService beanChgrInfoService()throws Exception{
		
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		
		return (ChgrInfoService)context.getBean("ChgrInfoService");
	}
	
}
