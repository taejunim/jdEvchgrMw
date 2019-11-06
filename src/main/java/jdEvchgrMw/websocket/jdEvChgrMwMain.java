package jdEvchgrMw.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import jdEvchgrMw.vo.CommonVO;

@ServerEndpoint(value="/station/{stationId}/{chgrId}")
public class jdEvChgrMwMain {

	/**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("chgrId") String chgrId, Session session){
    	
        System.out.println("[ 충전기  "+ chgrId +"(가)이 접속 하였습니다. ]");
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

    	System.out.println("[ MSG -> M/W : "+ commonVO.getRcvMsg() +" ]");
    	
    	jdp.jsonDataParsingMain(commonVO);
    }
    
    /**
     * WEBSOCKET DISCONNECTED CALL EVENT
     */
    @OnClose
    public void handleClose() {

    	System.out.println("[ 클라이언트가 접속을 종료 하였습니다. ]");
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
