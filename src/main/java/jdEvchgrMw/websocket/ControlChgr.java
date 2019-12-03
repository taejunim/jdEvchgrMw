package jdEvchgrMw.websocket;

import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.SessionVO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static jdEvchgrMw.websocket.jdEvChgrMwMain.sessionList;

@ServerEndpoint(value="/chgr/{action_type}")
public class ControlChgr {

    SimpleDateFormat responseDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss");

    /**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("action_type") String actionType, Session session){

        System.out.println("[ 관제 접속 : " + session + " ]");
        SessionVO sessionVO = new SessionVO();

        sessionVO.setStationChgrId("ctrl");
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);

        //세션 중복 제거 작업 -> 세션은 달라질 수 있는데 충전소ID + 충전기ID 값은 고유하므로 List에서 같은 stationChgrId 면 중복 제거함.
        List<SessionVO> tempList = new ArrayList<SessionVO>(new HashSet<SessionVO>(sessionList));

        sessionList = tempList;

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

        commonVO.setUserSession(session);
        commonVO.setRcvMsg(message);
        commonVO.setActionType(actionType);

        Date dt = new Date();
        commonVO.setResponseDate(responseDateFormat.format(dt));

        System.out.println("[ MSG Start.. 현재 session 수 : " + sessionList.size() + " ]");
        System.out.println("[ 관제 -> M/W : "+ commonVO.getRcvMsg() +" ]");

        if (actionType.equals("") || actionType == null || message.equals("") || message == null) {
            jdp.protocolErrorMsgSend(commonVO);
        } else {
            jdp.jsonDataParsingMainForControl(commonVO);
        }
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
