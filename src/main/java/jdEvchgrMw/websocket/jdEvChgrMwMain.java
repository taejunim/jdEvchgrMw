package jdEvchgrMw.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.SessionVO;

import java.util.ArrayList;
import java.util.Collections;
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

        System.out.println("[ 충전소 : " + stationId + " - 충전기  "+ chgrId +"(가)이 접속 하였습니다. ]");
        System.out.println("[ session.getId() : " + session.getId() + " ]");
        System.out.println("[ session : " + session + " ]");

        SessionVO sessionVO = new SessionVO();
        sessionVO.setSessionId(session.getId());
        sessionVO.setStationChgrId(stationId + chgrId);
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);
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

    	commonVO.setStationId(stationId);
    	commonVO.setChgrId(chgrId);
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

                System.out.println("[ sessionList.get(i).getSessionId() : " + sessionList.get(i).getSessionId() + " / session.getId() : " + session.getId() + " ]");
                System.out.println("[ sessionList.get(i).getStationChgrId() : " + sessionList.get(i).getStationChgrId() + " / stationId + chgrId : " + stationId + chgrId + " ]");
                System.out.println("[ 삭제된 충전기 : " + sessionList.get(i).getStationChgrId() + " ]");

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
