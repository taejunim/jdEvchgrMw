package jdEvchgrMw.websocket;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static jdEvchgrMw.websocket.jdEvChgrMwMain.sessionList;

/**
 * @ Class Name  : JsonDataParsing.java
 * @ Description : JSON DATA PARSING
 * @ Modification Information
 * @
 * @ 수정일      		  수정자                 			         수정 내용
 * @ ----------   ----------   -------------------------------
 * @ 2018.07.02              고재훈    				 	최초 생성
 * @ since
 * @ version
 * @ see
 * <p>
 * Copyright (C) by MOPAS All right reserved.
 */

public class JsonDataParsingForControl {

    CollectServiceBean csb = new CollectServiceBean();
    SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월 dd일 HH시 mm분 ss초");

    /**
     * JSON DATA PARSING MAIN
     *
     * @param commonVO - 전송 정보가 담겨있는 VO
     * @throws Exception
     */
    public void jsonDataParsingMainForControl(CommonVO commonVO) {

        JSONParser jParser = new JSONParser();
        JSONObject jObject = new JSONObject();

        try {

            jObject = (JSONObject) jParser.parse(commonVO.getRcvMsg());

            /*COMMON PARSING*/
            Date dt = new Date();
            String time = sdf.format(dt);

            System.out.println(time + " - [ 관제 명령 : " + commonVO.getActionType() + " ]");

            String send_type = jObject.get("send_type").toString();
            String action_type = jObject.get("action_type").toString();
            String data = jObject.get("data").toString();

            commonVO.setUuid(UUID.randomUUID().toString());
            commonVO.setSendType(send_type);
            commonVO.setActionType(action_type);
            commonVO.setData(data);

            commonVO.setResponseReceive("1");
            commonVO.setResponseReason("");

            //데이터 공백 체크
            if (send_type.equals("") || send_type == null || action_type.equals("") || action_type == null || data.equals("") || data == null) {

                protocolErrorMsgSend(commonVO);
                return;

            }

            //데이터 정상 -> 파싱 시작
            else {

                if (commonVO.getActionType().equals("reset")) {
                    commonVO = resetParsingData(commonVO);                        //충전기 RESET 요청(관제 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("prices")) {
                    commonVO = pricesParsingData(commonVO);                        //단가정보(관제 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("changeMode")) {
                    commonVO = changeModeParsingData(commonVO);                        //충전기모드변경(관제 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("displayBrightness")) {
                    commonVO = displayBrightnessParsingData(commonVO);                        //충전기 화면밝기정보(관제 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("sound")) {
                    commonVO = soundParsingData(commonVO);                        //충전기 소리정보(관제 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("askVer")) {
                    commonVO = askVerParsingData(commonVO);                        //펌웨어 버전 정보 확인(관제 -> 충전기) CALL

                } else {
                    System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");

                    protocolErrorMsgSend(commonVO);
                    return;
                }
            }

            System.out.println("[ responseDate : " + commonVO.getResponseDate() + " ]");

        } catch (ParseException e) {
            e.printStackTrace();

            protocolErrorMsgSend(commonVO);
            return;

        } catch (Exception e) {
            e.printStackTrace();

            unknownErrorMsgSend(commonVO, "알 수 없는 오류입니다. 담당자에게 문의주세요.");
            return;

        }

        //응답 보내기
        sendMessage(commonVO);

        if (commonVO.getStationId().length() <= 6) {
            commonVO.setStationId(commonVO.getProviderId() + commonVO.getStationId());
        }

        controlMw(commonVO);

    }

    /**
     * WEBSOCKET SEND MESSAGE
     *
     * @param commonVO - SEND MESSAGE
     */
    public void sendMessage(CommonVO commonVO) {

        JSONObject sendJsonObject = new JSONObject();

        try {

            sendJsonObject.put("send_type", "res");
            sendJsonObject.put("action_type", commonVO.getActionType());

            JSONObject sendData = new JSONObject();

            sendData.put("response_date", commonVO.getResponseDate());
            sendData.put("response_receive", commonVO.getResponseReceive());
            sendData.put("response_reason", commonVO.getResponseReason());

            if (commonVO.getActionType().equals("prices")) {

                JSONArray pricesArray = new JSONArray();

                for (int i = 0; i < commonVO.getControlChgrVOArrayList().size(); i++) {

                    JSONObject pricesData = new JSONObject();
                    pricesData.put("ctrl_list_id", commonVO.getControlChgrVOArrayList().get(i).getCtrlListId());
                    pricesData.put("station_id", commonVO.getControlChgrVOArrayList().get(i).getStationId());
                    pricesData.put("chgr_id", commonVO.getControlChgrVOArrayList().get(i).getChgrId());

                    pricesArray.add(pricesData);
                }

                sendData.put("req_chgr", pricesArray);

            } else {
                sendData.put("ctrl_list_id", commonVO.getCtrlListId());
                sendData.put("station_id", commonVO.getStationId());
                sendData.put("chgr_id", commonVO.getChgrId());
            }

            sendJsonObject.put("data", sendData);

            System.out.println("관제로 보낼 JSON : " + sendJsonObject);

            //응답 데이터 DB에 저장
            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
            chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
            chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
            chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

            //csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

            commonVO.getUserSession().getAsyncRemote().sendText(sendJsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();

            unknownErrorMsgSend(commonVO, "예외 발생 : 클라이언트가 종료되어 MSG를 전송하지 못하였습니다.");
            return;
        }
    }

    //관제에서 받은 제어 명령을 M/W가 충전기로 보냄
    private void controlMw(CommonVO commonVO) {

        JSONObject sendJsonObject = new JSONObject();

        try {

            sendJsonObject.put("uuid", commonVO.getUuid());
            sendJsonObject.put("send_type", "req");
            sendJsonObject.put("action_type", commonVO.getActionType());


            //단가 제외한 제어 명령
            if (!commonVO.getActionType().equals("prices")) {

                JSONObject sendData = new JSONObject();

                sendData.put("station_id", commonVO.getStationId());
                sendData.put("chgr_id", commonVO.getChgrId());
                sendData.put("send_date", commonVO.getResponseDate());

                if (commonVO.getActionType().equals("changeMode")) {
                    sendData.put("change_mode", commonVO.getChangeModeVO().getChangeMode()); //충전기모드변경(충전기정보시스템 -> 충전기) CALL or 충전기 소리정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("displayBrightness") || commonVO.getActionType().equals("sound")) {

                    sendData.put("h00", commonVO.getHourVO().getH00());
                    sendData.put("h01", commonVO.getHourVO().getH01());
                    sendData.put("h02", commonVO.getHourVO().getH02());
                    sendData.put("h03", commonVO.getHourVO().getH03());
                    sendData.put("h04", commonVO.getHourVO().getH04());
                    sendData.put("h05", commonVO.getHourVO().getH05());
                    sendData.put("h06", commonVO.getHourVO().getH06());
                    sendData.put("h07", commonVO.getHourVO().getH07());
                    sendData.put("h08", commonVO.getHourVO().getH08());
                    sendData.put("h09", commonVO.getHourVO().getH09());
                    sendData.put("h10", commonVO.getHourVO().getH10());
                    sendData.put("h11", commonVO.getHourVO().getH11());
                    sendData.put("h12", commonVO.getHourVO().getH12());
                    sendData.put("h13", commonVO.getHourVO().getH13());
                    sendData.put("h14", commonVO.getHourVO().getH14());
                    sendData.put("h15", commonVO.getHourVO().getH15());
                    sendData.put("h16", commonVO.getHourVO().getH16());
                    sendData.put("h17", commonVO.getHourVO().getH17());
                    sendData.put("h18", commonVO.getHourVO().getH18());
                    sendData.put("h19", commonVO.getHourVO().getH19());
                    sendData.put("h20", commonVO.getHourVO().getH20());
                    sendData.put("h21", commonVO.getHourVO().getH21());
                    sendData.put("h22", commonVO.getHourVO().getH22());
                    sendData.put("h23", commonVO.getHourVO().getH23());

                } else if (commonVO.getActionType().equals("askVer")) {
                    sendData.put("fw_type", commonVO.getFwVerInfoVO().getFwType()); //펌웨어 버전 정보 확인(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("reset")) {

                    System.out.println("reset data : " + sendData.toString());

                } else {
                    System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");
                    //undefinedActionErrorMsgSend(commonVO);
                    protocolErrorMsgSend(commonVO);
                    return;
                }

                sendJsonObject.put("data", sendData);

                for (int i=0; i<sessionList.size(); i++) {

                    System.out.println("충전기로 보낼 JSON : " + sendJsonObject);

                    if (sessionList.get(i).getStationChgrId().equals(commonVO.getStationId() + commonVO.getChgrId())) {
                        System.out.println("세션 리스트의 stationChgrId  : " + sessionList.get(i).getStationChgrId() + " / commonVO 세션의 stationChgrId : " + commonVO.getStationId() + commonVO.getChgrId());
                        sessionList.get(i).getUserSession().getAsyncRemote().sendText(sendJsonObject.toString());
                        break;
                    }
                }
            }

            //단가 제어
            else {

                for (int i=0; i<sessionList.size(); i++) {

                    for (int j=0; j<commonVO.getControlChgrVOArrayList().size(); j++) {

                        if (sessionList.get(i).getStationChgrId().equals(commonVO.getControlChgrVOArrayList().get(j).getStationId() + commonVO.getControlChgrVOArrayList().get(j).getChgrId())) {

                            JSONObject pricesData = new JSONObject();

                            pricesData.put("send_date", commonVO.getResponseDate());
                            pricesData.put("cost_sdd", commonVO.getCostSdd());

                            pricesData.put("h00", commonVO.getHourVO().getH00());
                            pricesData.put("h01", commonVO.getHourVO().getH01());
                            pricesData.put("h02", commonVO.getHourVO().getH02());
                            pricesData.put("h03", commonVO.getHourVO().getH03());
                            pricesData.put("h04", commonVO.getHourVO().getH04());
                            pricesData.put("h05", commonVO.getHourVO().getH05());
                            pricesData.put("h06", commonVO.getHourVO().getH06());
                            pricesData.put("h07", commonVO.getHourVO().getH07());
                            pricesData.put("h08", commonVO.getHourVO().getH08());
                            pricesData.put("h09", commonVO.getHourVO().getH09());
                            pricesData.put("h10", commonVO.getHourVO().getH10());
                            pricesData.put("h11", commonVO.getHourVO().getH11());
                            pricesData.put("h12", commonVO.getHourVO().getH12());
                            pricesData.put("h13", commonVO.getHourVO().getH13());
                            pricesData.put("h14", commonVO.getHourVO().getH14());
                            pricesData.put("h15", commonVO.getHourVO().getH15());
                            pricesData.put("h16", commonVO.getHourVO().getH16());
                            pricesData.put("h17", commonVO.getHourVO().getH17());
                            pricesData.put("h18", commonVO.getHourVO().getH18());
                            pricesData.put("h19", commonVO.getHourVO().getH19());
                            pricesData.put("h20", commonVO.getHourVO().getH20());
                            pricesData.put("h21", commonVO.getHourVO().getH21());
                            pricesData.put("h22", commonVO.getHourVO().getH22());
                            pricesData.put("h23", commonVO.getHourVO().getH23());

                            pricesData.put("station_id", commonVO.getControlChgrVOArrayList().get(j).getStationId());
                            pricesData.put("chgr_id", commonVO.getControlChgrVOArrayList().get(j).getChgrId());

                            sendJsonObject.put("data", pricesData);

                            System.out.println("세션 리스트의 stationChgrId  : " + sessionList.get(i).getStationChgrId() + " / commonVO 세션의 stationChgrId : " + commonVO.getStationId() + commonVO.getChgrId());
                            System.out.println("충전기로 보낼 JSON : " + sendJsonObject);
                            sessionList.get(i).getUserSession().getAsyncRemote().sendText(sendJsonObject.toString());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            unknownErrorMsgSend(commonVO, "예외 발생 : 클라이언트가 종료되어 MSG를 전송하지 못하였습니다.");
            return;
        }
    }

    private CommonVO resetParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");

            if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null ||
                    chgr_id.equals("") || chgr_id == null || send_date.equals("") || send_date == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }

            System.out.println("<----------- 리셋 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);
            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);

            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    private CommonVO pricesParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            ArrayList<ControlChgrVO> controlChgrVOArrayList = new ArrayList<>();

            JSONArray reqChgrArr = (JSONArray) data.get("req_chgr");

            for (int i = 0; i < reqChgrArr.size(); i++) {
                JSONObject tmp = (JSONObject) reqChgrArr.get(i);

                String ctrl_list_id = (String) tmp.get("ctrl_list_id");
                String station_id = (String) tmp.get("station_id");
                String chgr_id = (String) tmp.get("chgr_id");

                if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null || chgr_id.equals("") || chgr_id == null) {
                    commonVO.setResponseReceive("0");   //실패
                    commonVO.setResponseReason("12");   //파라미터 오류
                    return commonVO;
                }

                System.out.println("<----------- 단가 변경 Parsing Data [" + (i+1) + "]----------->");
                System.out.println("ctrl_list_id : " + ctrl_list_id);
                System.out.println("station_id : " + station_id);
                System.out.println("chgr_id : " + chgr_id);


                ControlChgrVO controlChgrVO = new ControlChgrVO();
                controlChgrVO.setCtrlListId(ctrl_list_id);
                controlChgrVO.setStationId(station_id);
                controlChgrVO.setChgrId(chgr_id);
                controlChgrVOArrayList.add(controlChgrVO);
            }

            String send_date = (String) data.get("send_date");
            String cost_sdd = (String) data.get("cost_sdd");

            String h00 = (String) data.get("h00");
            String h01 = (String) data.get("h01");
            String h02 = (String) data.get("h02");
            String h03 = (String) data.get("h03");
            String h04 = (String) data.get("h04");
            String h05 = (String) data.get("h05");
            String h06 = (String) data.get("h06");
            String h07 = (String) data.get("h07");
            String h08 = (String) data.get("h08");
            String h09 = (String) data.get("h09");
            String h10 = (String) data.get("h10");
            String h11 = (String) data.get("h11");
            String h12 = (String) data.get("h12");
            String h13 = (String) data.get("h13");
            String h14 = (String) data.get("h14");
            String h15 = (String) data.get("h15");
            String h16 = (String) data.get("h16");
            String h17 = (String) data.get("h17");
            String h18 = (String) data.get("h18");
            String h19 = (String) data.get("h19");
            String h20 = (String) data.get("h20");
            String h21 = (String) data.get("h21");
            String h22 = (String) data.get("h22");
            String h23 = (String) data.get("h23");

            HourVO hourVO = new HourVO();
            hourVO.setH00(h00);
            hourVO.setH01(h01);
            hourVO.setH02(h02);
            hourVO.setH03(h03);
            hourVO.setH04(h04);
            hourVO.setH05(h05);
            hourVO.setH06(h06);
            hourVO.setH07(h07);
            hourVO.setH08(h08);
            hourVO.setH09(h09);
            hourVO.setH10(h10);
            hourVO.setH11(h11);
            hourVO.setH12(h12);
            hourVO.setH13(h13);
            hourVO.setH14(h14);
            hourVO.setH15(h15);
            hourVO.setH16(h16);
            hourVO.setH17(h17);
            hourVO.setH18(h18);
            hourVO.setH19(h19);
            hourVO.setH20(h20);
            hourVO.setH21(h21);
            hourVO.setH22(h22);
            hourVO.setH23(h23);

            if (send_date.equals("") || send_date == null || cost_sdd.equals("") || cost_sdd == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }
            System.out.println("controlChgrVOArrayList : " + controlChgrVOArrayList);
            System.out.println("send_date : " + send_date);
            System.out.println("cost_sdd : " + cost_sdd);
            System.out.println("h00 : " + h00);
            System.out.println("h01 : " + h01);
            System.out.println("h02 : " + h02);
            System.out.println("h03 : " + h03);
            System.out.println("h04 : " + h04);
            System.out.println("h05 : " + h05);
            System.out.println("h06 : " + h06);
            System.out.println("h07 : " + h07);
            System.out.println("h08 : " + h08);
            System.out.println("h09 : " + h09);
            System.out.println("h10 : " + h10);
            System.out.println("h11 : " + h11);
            System.out.println("h12 : " + h12);
            System.out.println("h13 : " + h13);
            System.out.println("h14 : " + h14);
            System.out.println("h15 : " + h15);
            System.out.println("h16 : " + h16);
            System.out.println("h17 : " + h17);
            System.out.println("h18 : " + h18);
            System.out.println("h19 : " + h19);
            System.out.println("h20 : " + h20);
            System.out.println("h21 : " + h21);
            System.out.println("h22 : " + h22);
            System.out.println("h23 : " + h23);
            System.out.println("<------------------------------------------------>");

            commonVO.setCostSdd(cost_sdd);
            commonVO.setControlChgrVOArrayList(controlChgrVOArrayList);

            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    private CommonVO changeModeParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");
            String change_mode = (String) data.get("change_mode");

            if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null ||
                    chgr_id.equals("") || chgr_id == null || send_date.equals("") || send_date == null || change_mode.equals("") || change_mode == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }

            System.out.println("<----------- 충전기 모드 변경 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);
            System.out.println("change_mode : " + change_mode);
            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);

            ChangeModeVO changeModeVO = new ChangeModeVO();
            changeModeVO.setChangeMode(change_mode);
            commonVO.setChangeModeVO(changeModeVO);

            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    private CommonVO displayBrightnessParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");

            String h00 = (String) data.get("h00");
            String h01 = (String) data.get("h01");
            String h02 = (String) data.get("h02");
            String h03 = (String) data.get("h03");
            String h04 = (String) data.get("h04");
            String h05 = (String) data.get("h05");
            String h06 = (String) data.get("h06");
            String h07 = (String) data.get("h07");
            String h08 = (String) data.get("h08");
            String h09 = (String) data.get("h09");
            String h10 = (String) data.get("h10");
            String h11 = (String) data.get("h11");
            String h12 = (String) data.get("h12");
            String h13 = (String) data.get("h13");
            String h14 = (String) data.get("h14");
            String h15 = (String) data.get("h15");
            String h16 = (String) data.get("h16");
            String h17 = (String) data.get("h17");
            String h18 = (String) data.get("h18");
            String h19 = (String) data.get("h19");
            String h20 = (String) data.get("h20");
            String h21 = (String) data.get("h21");
            String h22 = (String) data.get("h22");
            String h23 = (String) data.get("h23");

            HourVO hourVO = new HourVO();
            hourVO.setH00(h00);
            hourVO.setH01(h01);
            hourVO.setH02(h02);
            hourVO.setH03(h03);
            hourVO.setH04(h04);
            hourVO.setH05(h05);
            hourVO.setH06(h06);
            hourVO.setH07(h07);
            hourVO.setH08(h08);
            hourVO.setH09(h09);
            hourVO.setH10(h10);
            hourVO.setH11(h11);
            hourVO.setH12(h12);
            hourVO.setH13(h13);
            hourVO.setH14(h14);
            hourVO.setH15(h15);
            hourVO.setH16(h16);
            hourVO.setH17(h17);
            hourVO.setH18(h18);
            hourVO.setH19(h19);
            hourVO.setH20(h20);
            hourVO.setH21(h21);
            hourVO.setH22(h22);
            hourVO.setH23(h23);

            if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null ||
                    chgr_id.equals("") || chgr_id == null || send_date.equals("") || send_date == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }

            System.out.println("<----------- 충전기 밝기 정보 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);

            System.out.println("h00 : " + h00);
            System.out.println("h01 : " + h01);
            System.out.println("h02 : " + h02);
            System.out.println("h03 : " + h03);
            System.out.println("h04 : " + h04);
            System.out.println("h05 : " + h05);
            System.out.println("h06 : " + h06);
            System.out.println("h07 : " + h07);
            System.out.println("h08 : " + h08);
            System.out.println("h09 : " + h09);
            System.out.println("h10 : " + h10);
            System.out.println("h11 : " + h11);
            System.out.println("h12 : " + h12);
            System.out.println("h13 : " + h13);
            System.out.println("h14 : " + h14);
            System.out.println("h15 : " + h15);
            System.out.println("h16 : " + h16);
            System.out.println("h17 : " + h17);
            System.out.println("h18 : " + h18);
            System.out.println("h19 : " + h19);
            System.out.println("h20 : " + h20);
            System.out.println("h21 : " + h21);
            System.out.println("h22 : " + h22);
            System.out.println("h23 : " + h23);
            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);
            commonVO.setHourVO(hourVO);



            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    private CommonVO soundParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");

            String h00 = (String) data.get("h00");
            String h01 = (String) data.get("h01");
            String h02 = (String) data.get("h02");
            String h03 = (String) data.get("h03");
            String h04 = (String) data.get("h04");
            String h05 = (String) data.get("h05");
            String h06 = (String) data.get("h06");
            String h07 = (String) data.get("h07");
            String h08 = (String) data.get("h08");
            String h09 = (String) data.get("h09");
            String h10 = (String) data.get("h10");
            String h11 = (String) data.get("h11");
            String h12 = (String) data.get("h12");
            String h13 = (String) data.get("h13");
            String h14 = (String) data.get("h14");
            String h15 = (String) data.get("h15");
            String h16 = (String) data.get("h16");
            String h17 = (String) data.get("h17");
            String h18 = (String) data.get("h18");
            String h19 = (String) data.get("h19");
            String h20 = (String) data.get("h20");
            String h21 = (String) data.get("h21");
            String h22 = (String) data.get("h22");
            String h23 = (String) data.get("h23");

            HourVO hourVO = new HourVO();
            hourVO.setH00(h00);
            hourVO.setH01(h01);
            hourVO.setH02(h02);
            hourVO.setH03(h03);
            hourVO.setH04(h04);
            hourVO.setH05(h05);
            hourVO.setH06(h06);
            hourVO.setH07(h07);
            hourVO.setH08(h08);
            hourVO.setH09(h09);
            hourVO.setH10(h10);
            hourVO.setH11(h11);
            hourVO.setH12(h12);
            hourVO.setH13(h13);
            hourVO.setH14(h14);
            hourVO.setH15(h15);
            hourVO.setH16(h16);
            hourVO.setH17(h17);
            hourVO.setH18(h18);
            hourVO.setH19(h19);
            hourVO.setH20(h20);
            hourVO.setH21(h21);
            hourVO.setH22(h22);
            hourVO.setH23(h23);

            if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null ||
                    chgr_id.equals("") || chgr_id == null || send_date.equals("") || send_date == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }

            System.out.println("<----------- 충전기 소리 정보 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);

            System.out.println("h00 : " + h00);
            System.out.println("h01 : " + h01);
            System.out.println("h02 : " + h02);
            System.out.println("h03 : " + h03);
            System.out.println("h04 : " + h04);
            System.out.println("h05 : " + h05);
            System.out.println("h06 : " + h06);
            System.out.println("h07 : " + h07);
            System.out.println("h08 : " + h08);
            System.out.println("h09 : " + h09);
            System.out.println("h10 : " + h10);
            System.out.println("h11 : " + h11);
            System.out.println("h12 : " + h12);
            System.out.println("h13 : " + h13);
            System.out.println("h14 : " + h14);
            System.out.println("h15 : " + h15);
            System.out.println("h16 : " + h16);
            System.out.println("h17 : " + h17);
            System.out.println("h18 : " + h18);
            System.out.println("h19 : " + h19);
            System.out.println("h20 : " + h20);
            System.out.println("h21 : " + h21);
            System.out.println("h22 : " + h22);
            System.out.println("h23 : " + h23);

            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);
            commonVO.setHourVO(hourVO);



            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    private CommonVO askVerParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");
            String fw_type = (String) data.get("fw_type");

            if (ctrl_list_id.equals("") || ctrl_list_id == null || station_id.equals("") || station_id == null ||
                    chgr_id.equals("") || chgr_id == null || send_date.equals("") || send_date == null  || fw_type.equals("") || fw_type == null) {

                commonVO.setResponseReceive("0");   //실패
                commonVO.setResponseReason("12");   //파라미터 오류
                return commonVO;
            }

            System.out.println("<----------- 충전기 펌웨어 버전 확인 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);
            System.out.println("fw_type : " + fw_type);
            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);

            FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
            fwVerInfoVO.setFwType(fw_type);
            commonVO.setFwVerInfoVO(fwVerInfoVO);

            // req Data DB Insert
            /*try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChgrId(commonVO.getChgrId());
                alarmHistoryVO.setChgrTxDt(create_date);
                alarmHistoryVO.setMwKindCd("WS");
                alarmHistoryVO.setRTimeYn("Y");

                System.out.println("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("<----------------------- DB Insert 오류 ------------------------->");
                unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
                return;
            }*/

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }

        return commonVO;
    }

    public void protocolErrorMsgSend(CommonVO commonVO) {

        System.out.println("필수항목 누락으로 인한 전문 오류 입니다. 받은 uuid : " + commonVO.getUuid() + ", 받은 send_type : "
                + commonVO.getSendType() + ", 받은 action_type : " + commonVO.getActionType() + ", 받은 data : " + commonVO.getData());

        commonVO.setResponseReceive("0");
        commonVO.setResponseReason("11");
        sendMessage(commonVO);

        return;
    }

    //파라미터 오류
    public void parameterErrorMsgSend(CommonVO commonVO) {
        System.out.println("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());

        commonVO.setResponseReceive("0");   //실패
        commonVO.setResponseReason("12");   //파라미터 오류
        sendMessage(commonVO);

        return;
    }

    //충전소(기)ID 오류
    public void invalidErrorMsgSend(CommonVO commonVO) {
        System.out.println("충전소(기)ID 중 오류 입니다. 받은 Data : " + commonVO.getData());

        commonVO.setResponseReceive("0");   //실패
        commonVO.setResponseReason("13");   //파라미터 오류
        sendMessage(commonVO);

        return;
    }

    //데이터형식 오류
    public void dataTypeErrorMsgSend(CommonVO commonVO) {
        commonVO.getUserSession().getAsyncRemote().sendText("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());

        commonVO.setResponseReceive("0");   //실패
        commonVO.setResponseReason("14");   //데이터형식 오류
        sendMessage(commonVO);

        System.out.println("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());
        return;
    }

    public void unknownErrorMsgSend(CommonVO commonVO, String msg) {

        System.out.println("내부 오류 입니다. 받은 uuid : " + commonVO.getUuid() + ", 받은 send_type : "
                + commonVO.getSendType() + ", 받은 action_type : " + commonVO.getActionType() + ", 받은 data : " + commonVO.getData());
        System.out.println("내부 오류 : " + msg);

        commonVO.setResponseReceive("0");
        commonVO.setResponseReason("15");
        sendMessage(commonVO);

        return;
    }
}
