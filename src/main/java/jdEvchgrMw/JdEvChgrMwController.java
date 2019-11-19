package jdEvchgrMw;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.ChgrInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

		// req Data DB Insert
		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			//추후 PROVIDER_ID 하드코딩한거 수정해야 함
			//chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
			chgrInfoVO.setProviderId("JD");
			chgrInfoVO.setStId("110001");
			chgrInfoVO.setChgrId("01");
			chgrInfoVO.setSpeedTpCd("1");
			chgrInfoVO.setGpsXpos("33.491474153838820");
			chgrInfoVO.setGpsYpos("126.535281442280250");
			chgrInfoVO.setMfCd("");
			chgrInfoVO.setM2mMfCd("");
			chgrInfoVO.setRfMfCd("");
			chgrInfoVO.setM2mTel("");
			chgrInfoVO.setVanIp("");
			chgrInfoVO.setVanPort(8080);
			chgrInfoVO.setMwSession("11000101");
			chgrInfoVO.setModUid("MW");

			CollectServiceBean csb = new CollectServiceBean();
			csb.beanChgrInfoService().chgrInfoUpdate(chgrInfoVO);
			System.out.println("<----------------------- Update OK ------------------------->");

		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("<----------------------- DB Insert 오류 ------------------------->");

		}

		return "jdEvchgrMw/main";
	}
	
}
