package jdEvchgrMw;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}
