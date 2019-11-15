package jdEvchgrMw;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.vo.ChgrInfoVO;

@Controller
public class JdEvChgrMwController {

	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * MAIN PAGE
	 * @param 
	 * @param 
	 * @return "Main"
	 */
	@RequestMapping(value = "/main.do")
	public String main() throws Exception {
		
		System.out.println("MAIN");

		return "jdEvchgrMw/main";
	}
	
}
