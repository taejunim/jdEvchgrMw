package jdEvchgrMw.websocket;

import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.SessionVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static jdEvchgrMw.websocket.JdEvChgrMwMain.sessionList;

@ServerEndpoint(value="/chgr/{action_type}")
public class ControlChgr {

    Logger logger = LogManager.getLogger(ControlChgr.class);

    SimpleDateFormat responseDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss");

    /**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("action_type") String actionType, Session session){

        logger.info("[ 관제 접속 : " + session + " ]");
        SessionVO sessionVO = new SessionVO();

        sessionVO.setStationChgrId("ctrl");
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);

        //세션 중복 제거 작업 -> 세션은 달라질 수 있는데 충전소ID + 충전기ID 값은 고유하므로 List에서 같은 stationChgrId 면 중복 제거함.
        List<SessionVO> tempList = new ArrayList<SessionVO>(new HashSet<SessionVO>(sessionList));

        sessionList = tempList;

        logger.info("[ 관제 제어 명령 : " + actionType + ", 현재 sessionList : " + sessionList + " ]");
    }
    
    /**
     * MSG RECEIVE CALL EVENT
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

        logger.info("[ MSG Start.. 현재 session 수 : " + sessionList.size() + " ]");
        logger.info("[ 관제 -> M/W : "+ commonVO.getRcvMsg() +" ]");

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

    	logger.info("[ 관제 접속이 종료 되었습니다. ]");

        for (int i=0; i < sessionList.size(); i++) {

            logger.info("[" + i + "]");

            if (sessionList.get(i).getStationChgrId().equals("ctrl")) {

                logger.info("[ sessionList.get(i).getStationChgrId() : " + sessionList.get(i).getStationChgrId() + " / stationId + chgrId : ctrl ]");
                logger.info("[ 삭제된 세션 : " + sessionList.get(i).getStationChgrId() + " ]");

                sessionList.remove(i);

                break;
            }
        }

        logger.info("[ 현재 session 수 : " + sessionList.size() + ", 현재 sessionList : " + sessionList + " ]");

    }
    
    /**
     * WEBSOCKET ERROR CALL EVENT
     * @param
     */
    @OnError
    public void handleError(Throwable t) {
    	
    	logger.info("error...");
        t.printStackTrace();
    }
    


}
