package jdEvchgrMw.websocket;

import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.SessionVO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@ServerEndpoint(value="/station/{stationId}/{chgrId}")
public class jdEvChgrMwMain {

    //세션 집합 리스트
    static List<SessionVO> sessionList= Collections.synchronizedList(new ArrayList<SessionVO>());

	/**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, Session session){

        SessionVO sessionVO = new SessionVO();
        sessionVO.setStationChgrId(stationId + chgrId);
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);

        //세션 중복 제거 작업 -> 세션은 달라질 수 있는데 충전소ID + 충전기ID 값은 고유하므로 List에서 같은 stationChgrId 면 중복 제거함.
        List<SessionVO> tempList = new ArrayList<SessionVO>(new HashSet<SessionVO>(sessionList));

        sessionList = tempList;

        System.out.println("[ 충전소 : " + stationId + " - 충전기  "+ chgrId +"(가)이 접속 하였습니다. ]");
        System.out.println("[ 접속한 충전기 : " + stationId + chgrId + ", 현재 sessionList : " + sessionList + " ]");

    }
    
    /**
     * MSG RECIEVE CALL EVENET
     * @param message
     */
    @OnMessage
    public void handleMessage(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, String message, Session session) {

    	JsonDataParsing jdp         = new JsonDataParsing();
    	//CommonFunction  function 	= new CommonFunction();

        CommonVO commonVO = new CommonVO();

        if (stationId.length() > 6) {
            commonVO.setProviderId(stationId.substring(0,2));
            commonVO.setStationId(stationId.substring(2));
        } else {
            commonVO.setProviderId("JD");
            commonVO.setStationId(stationId);
        }

    	//충전기ID는 두자리
        if (chgrId.length() > 2) {
            commonVO.setChgrId(chgrId.substring(0,2));
        } else {
            commonVO.setChgrId(chgrId);
        }

    	commonVO.setUserSession(session);
    	commonVO.setRcvMsg(message);

        //function.clientSetting(commonVO);						//CHGR 및 SESSION SETTING CALL

        System.out.println("[ MSG Start.. 현재 session 수 : " + sessionList.size() + " ]");
    	System.out.println("[ MSG -> M/W : "+ commonVO.getRcvMsg() +" ]");
    	
    	jdp.jsonDataParsingMain(commonVO);
    }
    
    /**
     * WEBSOCKET DISCONNECTED CALL EVENT
     */
    @OnClose
    public void handleClose(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, Session session) {

    	System.out.println("[ 클라이언트가 접속을 종료 하였습니다. 종료 충전기 :  " + stationId + chgrId + " ]");

        for (int i=0; i < sessionList.size(); i++) {

            System.out.println("[" + i + "]");

            if (sessionList.get(i).getStationChgrId().equals(stationId + chgrId)) {

                System.out.println("[ 종료된 충전기 : " + sessionList.get(i).getStationChgrId() + " ]");

                sessionList.remove(i);

                break;
            }
        }

        System.out.println("[ 현재 session 수 : " + sessionList.size() + ", 현재 sessionList : " + sessionList + " ]");
    }
    
    /**
     * WEBSOCKET ERROR CALL EVENT
     * @param
     */
    @OnError
    public void handleError(Throwable t) {
    	
    	System.out.println("error...");
        t.printStackTrace();
    }
    


}
