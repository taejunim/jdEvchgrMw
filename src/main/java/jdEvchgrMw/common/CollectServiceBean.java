package jdEvchgrMw.common;

import jdEvchgrMw.alarmHistory.service.AlarmHistoryService;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.chgrState.service.ChgrStateService;
import jdEvchgrMw.revMsg.service.RevMsgService;
import jdEvchgrMw.user.service.UserService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
/**
 * 
/**
 * @ Class Name  : CollectServiceBean.java
 * @ Description : SERVICE BEAN CLASS
 * @ Modification Information
 * @
 * @    수정일      	수정자               수정 내용
 * @ ----------   ----------   -------------------------------
 * @ 2019.11.04     고재훈    			최초 생성
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
		
		return (ChgrInfoService)context.getBean("chgrInfoService");
	}

	/**
	 * AlarmHistoryService Bean 생성
	 * @return
	 * @throws Exception
	 */
	public AlarmHistoryService alarmHistoryService()throws Exception{

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		return (AlarmHistoryService)context.getBean("alarmHistoryService");
	}

	/**
	 * RevMsgService Bean 생성
	 * @return
	 * @throws Exception
	 */
	public RevMsgService revMsgService()throws Exception{

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		return (RevMsgService)context.getBean("revMsgService");
	}

	/**
	 * ChgrStateService Bean 생성
	 * @return
	 * @throws Exception
	 */
	public ChgrStateService chgrStateService()throws Exception{

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		return (ChgrStateService)context.getBean("chgrStateService");
	}

	/**
	 * UserService Bean 생성
	 * @return
	 * @throws Exception
	 */
	public UserService userService()throws Exception{

		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		return (UserService)context.getBean("userService");
	}

}
