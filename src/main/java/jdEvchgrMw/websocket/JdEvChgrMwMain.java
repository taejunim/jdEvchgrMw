package jdEvchgrMw.websocket;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint(value="/station/{stationId}/{chgrId}")
public class JdEvChgrMwMain {

    Logger logger = LogManager.getLogger(JdEvChgrMwMain.class);

    public static List<SessionVO> sessionList= Collections.synchronizedList(new ArrayList<SessionVO>());    //세션 집합 리스트
    public static Queue<String> qActionList = new LinkedList<String>();         //Action 리스트
    public static Queue<QueueVO> qDataObjectList = new LinkedList<QueueVO>();   //데이터 Object 리스트

	/**
     * WEBSOCKET CONNECTED CALL EVENT
     */
    @OnOpen
    public void handleOpen(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, Session session){

        String stationChgrId = stationId + (chgrId.length() > 2 ? chgrId.substring(0,2) : chgrId);

        SessionVO sessionVO = new SessionVO();
        sessionVO.setStationChgrId(stationChgrId);
        sessionVO.setUserSession(session);

        sessionList.add(sessionVO);

        //세션 중복 제거 작업 -> 세션은 달라질 수 있는데 충전소ID + 충전기ID 값은 고유하므로 List에서 같은 stationChgrId 면 중복 제거함.
        List<SessionVO> tempList = new ArrayList<SessionVO>(new HashSet<SessionVO>(sessionList));

        sessionList = tempList;

        logger.info("[ 충전소 : " + stationId + " - 충전기  "+ chgrId +"(가)이 접속 하였습니다. ]\n [ 접속중인 충전기 수 : " + sessionList.size() + " ]");
    }
    
    /**
     * MSG RECEIVE CALL EVENT
     * @param message
     */
    @OnMessage
    public void handleMessage(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, String message, Session session) {

        CommonVO commonVO = new CommonVO();

        commonVO.setProviderId(stationId.length() > 6 ? stationId.substring(0,2) : "JD");   //사업자ID -> 2자리
        commonVO.setStationId(stationId.length() > 6 ? stationId.substring(2) : stationId); //충전소ID -> 6자리인데 그 이상길면 2번째 자리부터
        commonVO.setChgrId(chgrId.length() > 2 ? chgrId.substring(0,2) : chgrId);           //충전기ID -> 2자리

    	commonVO.setUserSession(session);
    	commonVO.setRcvMsg(message);

    	//세션 로그 출력
        //sessionListLog();
        logger.info("[ 충전기(" + stationId + chgrId + ") -> M/W : "+ commonVO.getRcvMsg() +" ]");

        JsonDataParsing jdp         = new JsonDataParsing();
    	jdp.jsonDataParsingMain(commonVO);

        logger.info("[ q 데이터 처리 시작 SIZE : " + qDataObjectList.size() +" ]");
        if (qDataObjectList.size() > 0) {

            while(qDataObjectList.isEmpty()==false){

                QueueVO queueVO = qDataObjectList.poll();

                String rTimeYn = queueVO.getRTimeYn().equals("N") ? "덤프_" : "";
                String qActionType = queueVO.getActionType();
                Object object = queueVO.getObject();

                logger.info(rTimeYn + "qActionType : " + qActionType);
                logger.info("object : " + object);

                try {

                    CollectServiceBean csb = new CollectServiceBean();

                    //충전기 상태정보(충전기 -> 충전기정보시스템) CALL
                    if (qActionType.equals("chgrStatus")) {

                        logger.info("<----------------------- 충전기 상태 수정 OK -------------------------> : " + csb.chgrStateService().chgrStateUpdate( (ChgrStatusVO) object));
                    }

                    //충전 시작정보(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("chargingStart")) {

                        logger.info("<----------------------- " + rTimeYn + "충전 시작 정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgSttInsert( (ChargeVO) object));
                    }

                    //충전 진행정보(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("chargingInfo")) {

                        logger.info("<----------------------- 충전 진행정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgingInsert( (ChargeVO) object));
                    }

                    //충전 완료정보(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("chargingEnd")) {

                        logger.info("<----------------------- " + rTimeYn + "충전 완료정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgFnshInsert( (ChargeVO) object));
                    }

                    //충전 결제정보(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("chargePayment")) {

                        ChargePaymentVO chargePaymentVO = (ChargePaymentVO) object;

                        if (chargePaymentVO.getPaymentType().equals("0")) {

                            logger.info("<----------------------- " + rTimeYn + "충전 결제정보 - 실충전결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoInsert(chargePaymentVO));

                        } else if (chargePaymentVO.getPaymentType().equals("1")) {

                            logger.info("<----------------------- " + rTimeYn + "충전 결제정보 - 취소결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoUpdate(chargePaymentVO));
                        }
                    }

                    //문자 전송(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("sendSms")) {

                        logger.info("<----------------------- 문자 전송 이력 등록 OK -------------------------> : " + csb.sendSmsService().msgSndListInsert((SendSmsVO) object));
                    }

                    //경보 이력(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("alarmHistory")) {

                        logger.info("<----------------------- " + rTimeYn + "경보 이력 Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert( (AlarmHistoryVO) object));
                    }

                    //펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("reportUpdate")) {

                        logger.info("<----------------------- " + rTimeYn + "펌웨어 업데이트 결과 알림 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrFwVerInfoInsUpdate((ChgrInfoVO) object));
                    }

                    //신용승인 결제정보(충전기 -> 충전기정보시스템) CALL
                    else if (qActionType.equals("paymentInfo")) {

                        PaymentInfoVO paymentInfoVO = (PaymentInfoVO) object;

                        String payType = paymentInfoVO.getPaymentListVO().getPayType();

                        if (payType.equals("1")) {

                            logger.info("<----------------------- " + rTimeYn + "신용승인 결제정보 등록 OK -------------------------> : " + csb.chargePaymentService().paymentInfoInsert(paymentInfoVO));

                        } else if (payType.equals("2") || payType.equals("3")) {

                            logger.info("<----------------------- " + rTimeYn + "신용승인 결제정보 수정 OK -------------------------> : " + csb.chargePaymentService().paymentInfoUpdate(paymentInfoVO));
                        }
                    }

                    //수신 전문 이력 인서트&업데이트(충전기 <-> M/W)
                    else if (qActionType.equals("recvMsg")) {

                        logger.info("<----------------------- " + rTimeYn + "수신 전문 이력 Insert OK -------------------------> : " + csb.revMsgService().recvMsgInsert((RevMsgVO) object));
                    }

                    //전문 이력 업데이트(M/W <-> 충전기)
                    else if (qActionType.equals("txMsg")) {

                        logger.info("<----------------------- " + rTimeYn + "전문 이력 Update OK -------------------------> : " + csb.controlChgrService().txMsgListUpdate((ControlChgrVO) object));
                    }

                } catch (Exception e) {
                    logger.error("Data Exception : " + e);
                }
            }
        }

        logger.info("[ q 데이터 처리 완료 SIZE : " + qDataObjectList.size() +" ]");
    }

    /**
     * WEBSOCKET DISCONNECTED CALL EVENT
     */
    @OnClose
    public void handleClose(@PathParam("stationId") String stationId, @PathParam("chgrId") String chgrId, Session session) {

        String stationChgrId = stationId + (chgrId.length() > 2 ? chgrId.substring(0,2) : chgrId);

        for (int i=0; i < sessionList.size(); i++) {

            if (sessionList.get(i).getStationChgrId().equals(stationChgrId)) {

                logger.info("[ 충전기 (" + sessionList.get(i).getStationChgrId() + ") 연결이 끊어졌습니다. ]");

                sessionList.remove(i);

                break;
            }
        }

        logger.info("[ 현재 session 수 : " + sessionList.size() + " ]");
    }
    
    /**
     * WEBSOCKET ERROR CALL EVENT
     * @param
     */
    @OnError
    public void handleError(Throwable t) {
        logger.info("---------------------------------------------------");
    	logger.info("----------------- handleError ---------------------");
        logger.info(" t : " + t);
        logger.info(" cause : " + t.getCause());
        logger.info(" detailMessage : " + t.getMessage());
        logger.info("---------------------------------------------------");
        t.printStackTrace();
    }
}
