package jdEvchgrMw;

import jdEvchgrMw.vo.SessionVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

		sessionList2.clear();

		SessionVO sessionVO = new SessionVO();

		sessionVO.setStationChgrId("01");
		//sessionVO.setTemp("중앙");

		sessionList2.add(sessionVO);


		SessionVO sessionVO2= new SessionVO();

		sessionVO2.setStationChgrId("02");
		//sessionVO2.setTemp("시그넷");

		sessionList2.add(sessionVO2);


		SessionVO sessionVO3= new SessionVO();

		sessionVO3.setStationChgrId("03");
//		sessionVO3.setTemp("메티스");

		sessionList2.add(sessionVO3);


		SessionVO sessionVO4 = new SessionVO();

		sessionVO4.setStationChgrId("02");
//		sessionVO4.setTemp("진우");

		sessionList2.add(sessionVO4);

		System.out.println("before size:"+ sessionList2.size());
		System.out.println("before :"+ sessionList2);

		List<SessionVO> sliet = new ArrayList<SessionVO>(new HashSet<SessionVO>(sessionList2));
		System.out.println("after size:"+ sliet.size());
		System.out.println("after :"+ sliet);

		sessionList2 = sliet;
		//sliet.clear();
		System.out.println("final  sessionList2 :"+ sessionList2);




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
