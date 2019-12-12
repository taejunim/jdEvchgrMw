package jdEvchgrMw;

import jdEvchgrMw.vo.SessionVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class JdEvChgrMwController {

	static List<SessionVO> sessionList2= Collections.synchronizedList(new ArrayList<SessionVO>());

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
