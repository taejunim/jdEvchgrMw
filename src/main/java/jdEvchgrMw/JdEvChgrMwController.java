package jdEvchgrMw;

import jdEvchgrMw.common.CollectServiceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static jdEvchgrMw.common.ServerUtils.chgrList;
import static jdEvchgrMw.websocket.JdEvChgrMwMain.sessionList;

@Controller
public class JdEvChgrMwController {

	Logger logger = LogManager.getLogger(JdEvChgrMwController.class);

	/**
	 * MAIN PAGE
	 * @param 
	 * @param 
	 * @return "Main"
	 */
	@RequestMapping(value = "/main.do")
	public String main() throws Exception {
		
		logger.info("MAIN");

		String profile = System.getProperty("spring.profiles.active");

		logger.info("profile : " + profile);

		return "jdEvchgrMw/main";
	}

	/**
	 * DB에 등록된 충전기 목록
	 */
	@RequestMapping(value = "/selectChgrList.do")
	public void selectChgrList() throws Exception {

		logger.info("------------------ /selectChgrList.do ------------------ ");

		CollectServiceBean csb = new CollectServiceBean();

		try {
			chgrList.clear();
			chgrList = csb.beanChgrInfoService().chgrList();

			for (int i=0; i<chgrList.size(); i++) {
				logger.info("[" + (i+1) + "] : " + chgrList.get(i));
			}

		} catch (Exception e) {
			logger.error("selectChgrList Exception : " + e);
		}
	}

	/**
	 * 현재 세션 목록
	 */
	@RequestMapping(value = "/currentSessionList.do")
	public void currentSessionList() throws Exception {

		logger.info("------------------ /currentSessionList.do ------------------");

		logger.info("[ 총 session 수 : " + sessionList.size() + " ]");
		logger.info("  현재 session 목록 -> ");

		for (int i=0; i<sessionList.size(); i++) {
			logger.info("[" + (i+1) + "] : " + sessionList.get(i).getStationChgrId() + " / 세션 : " + sessionList.get(i).getUserSession()
					+ " / sessionId : " + sessionList.get(i).getUserSession().getId());
		}

		logger.info("------------------------------------------------------------");
	}
}
