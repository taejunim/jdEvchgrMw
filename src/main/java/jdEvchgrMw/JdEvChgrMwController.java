package jdEvchgrMw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JdEvChgrMwController {

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
		/*try {

			ChgrStatusVO chgrStatusVO = new ChgrStatusVO();
			chgrStatusVO.setProviderId("JD");
			chgrStatusVO.setStId("110001");
			chgrStatusVO.setChgrId("02");
			chgrStatusVO.setOpModeCd("1");
			chgrStatusVO.setIntegratedKwh("14562");
			chgrStatusVO.setPowerboxState("123");
			chgrStatusVO.setStateDt("20190529172029");
			chgrStatusVO.setMwKindCd("WS");
			chgrStatusVO.setRTimeYn("Y");
			chgrStatusVO.setChgrTxDt("20190529172029");

			chgrStatusVO.setCh1RechgStateCd("1");
			chgrStatusVO.setCh1DoorStateCd("1");
			chgrStatusVO.setCh1PlugStateCd("1");

			chgrStatusVO.setCh2RechgStateCd("2");
			chgrStatusVO.setCh2DoorStateCd("2");
			chgrStatusVO.setCh2PlugStateCd("2");

			chgrStatusVO.setCh3RechgStateCd("3");
			chgrStatusVO.setCh3DoorStateCd("3");
			chgrStatusVO.setCh3PlugStateCd("3");


			CollectServiceBean csb = new CollectServiceBean();

			System.out.println("<----------------------- Insert OK -------------------------> : " + csb.chgrStateService().chgrStateInsert(chgrStatusVO));

			System.out.println("<----------------------- Update OK -------------------------> : " + csb.chgrStateService().chgrCurrStateUpdate(chgrStatusVO));

		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("<----------------------- DB Insert 오류 ------------------------->");

		}*/

		return "jdEvchgrMw/main";
	}
	
}
