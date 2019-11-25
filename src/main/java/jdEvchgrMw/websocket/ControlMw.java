package jdEvchgrMw.websocket;

import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.SessionVO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static jdEvchgrMw.websocket.jdEvChgrMwMain.sessionList;

@ServerEndpoint(value="/chgr/{action_type}")
public class ControlMw {

    /**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("action_type") String actionType, Session session){

        System.out.println("[ 관제 접속 : " + session + " ]");
        SessionVO sessionVO = new SessionVO();
        sessionVO.setSessionId(session.getId());
        sessionVO.setStationChgrId("ctrl");
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);
        System.out.println("[ 관제 제어 명령 : " + actionType + ", 현재 sessionList : " + sessionList + " ]");

    }
    
    /**
     * MSG RECIEVE CALL EVENET
     * @param message
     */
    @OnMessage
    public void handleMessage(@PathParam("action_type") String actionType, String message, Session session) {

    	JsonDataParsingForControl jdp = new JsonDataParsingForControl();

        CommonVO commonVO = new CommonVO();

        //commonVO.setStationId(stationId);
        //commonVO.setChgrId(chgrId);
        commonVO.setUserSession(session);
        commonVO.setRcvMsg(message);
        commonVO.setActionType(actionType);

        System.out.println("[ MSG Start.. 현재 session 수 : " + sessionList.size() + " ]");
        System.out.println("[ MSG -> M/W : "+ commonVO.getRcvMsg() +" ]");

        jdp.jsonDataParsingMainForControl(commonVO);
    }
    
    /**
     * WEBSOCKET DISCONNECTED CALL EVENT
     */
    @OnClose
    public void handleClose(Session session) {

    	System.out.println("[ 관제 접속이 종료 되었습니다. ]");

        for (int i=0; i < sessionList.size(); i++) {

            System.out.println("[" + i + "]");

            if (sessionList.get(i).getStationChgrId().equals("ctrl")) {

                System.out.println("[ sessionList.get(i).getSessionId() : " + sessionList.get(i).getSessionId() + " / session.getId() : " + session.getId() + " ]");
                System.out.println("[ sessionList.get(i).getStationChgrId() : " + sessionList.get(i).getStationChgrId() + " / stationId + chgrId : ctrl ]");
                System.out.println("[ 삭제된 세션 : " + sessionList.get(i).getStationChgrId() + " ]");

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
