package jdEvchgrMw.websocket;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

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

            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월 dd일 HH시 mm분 ss초");

            Date dt = new Date();
            String time = sdf.format(dt);

            System.out.println(time + " - [ 관제 명령 : " + commonVO.getActionType() + " ]");

            String send_type = jObject.get("send_type").toString();
            String action_type = jObject.get("action_type").toString();
            String data = jObject.get("data").toString();

            commonVO.setSendType(send_type);
            commonVO.setActionType(action_type);
            commonVO.setData(data);

            SimpleDateFormat responseDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss");
            commonVO.setResponseDate(responseDateFormat.format(dt));

            //데이터 공백 체크
            if (send_type.equals("") || send_type == null || action_type.equals("") || action_type == null || data.equals("") || data == null) {

                protocolErrorMsgSend(commonVO);

            }

            //데이터 정상 -> 파싱 시작
            else {

                if (commonVO.getActionType().equals("reset")) {
                    commonVO = resetParsingData(commonVO);                        //충전기 RESET 요청(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("prices")) {
                    pricesParsingData(commonVO);                        //단가정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("changeMode")) {
                    commonVO = changeModeParsingData(commonVO);                        //충전기모드변경(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("displayBrightness")) {
                    commonVO = displayBrightnessParsingData(commonVO);                        //충전기 화면밝기정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("sound")) {
                    commonVO = soundParsingData(commonVO);                        //충전기 소리정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("askVer")) {
                    commonVO = askVerParsingData(commonVO);                        //펌웨어 버전 정보 확인(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("notifyVerUpgrade")) {
                    notifyVerUpgradeParsingData(commonVO);                        //펌웨어 버전 정보 업그레이드 알림(충전기정보시스템 -> 충전기) CALL

                } else {
                    System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");

                    protocolErrorMsgSend(commonVO);
                    return;
                }
            }

            System.out.println("[ responseDate : " + commonVO.getResponseDate() + " ]");
            System.out.println("[ reqCreateDate : " + commonVO.getReqCreateDate() + " ]");

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
        commonVO.setResponseReceive("1");
        commonVO.setResponseReason("");
        sendMessage(commonVO);

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

            sendData.put("ctrl_list_id", commonVO.getCtrlListId());
            sendData.put("station_id", commonVO.getStationId());
            sendData.put("chgr_id", commonVO.getChgrId());
            sendData.put("response_date", commonVO.getResponseDate());
            sendData.put("response_receive", commonVO.getResponseReceive());
            sendData.put("response_reason", commonVO.getResponseReason());

            //응답 성공 -> data Json으로 만들어서 보내줌 실패시 data 없이 보냄
            /*if (commonVO.getResponseReceive().equals("1")) {

                if (commonVO.getActionType().equals("reset")) {
                    resetParsingData(commonVO);                        //충전기 RESET 요청(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("prices")) {
                    pricesParsingData(commonVO);                        //단가정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("changeMode")) {
                    changeModeParsingData(commonVO);                        //충전기모드변경(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("displayBrightness")) {
                    displayBrightnessParsingData(commonVO);                        //충전기 화면밝기정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("sound")) {
                    soundParsingData(commonVO);                        //충전기 소리정보(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("askVer")) {
                    askVerParsingData(commonVO);                        //펌웨어 버전 정보 확인(충전기정보시스템 -> 충전기) CALL

                } else if (commonVO.getActionType().equals("notifyVerUpgrade")) {
                    notifyVerUpgradeParsingData(commonVO);                        //펌웨어 버전 정보 업그레이드 알림(충전기정보시스템 -> 충전기) CALL

                } else {
                    System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");
                    //undefinedActionErrorMsgSend(commonVO);
                    protocolErrorMsgSend(commonVO);
                    return;
                }
            }*/

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

    private CommonVO resetParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String ctrl_list_id = (String) data.get("ctrl_list_id");
            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String send_date = (String) data.get("send_date");

            System.out.println("<----------- 리셋 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);
            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);
            commonVO.setReqCreateDate(send_date);

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

    private void pricesParsingData(CommonVO commonVO) {

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
            commonVO.setReqCreateDate(send_date);

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


            System.out.println("<----------- 충전기 밝기 정보 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);

            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);
            commonVO.setReqCreateDate(send_date);
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


            System.out.println("<----------- 충전기 소리 정보 Parsing Data ----------->");
            System.out.println("ctrl_list_id : " + ctrl_list_id);
            System.out.println("station_id : " + station_id);
            System.out.println("chgr_id : " + chgr_id);
            System.out.println("send_date : " + send_date);

            System.out.println("<------------------------------------------------>");

            commonVO.setCtrlListId(ctrl_list_id);
            commonVO.setStationId(station_id);
            commonVO.setChgrId(chgr_id);
            commonVO.setReqCreateDate(send_date);
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
            commonVO.setReqCreateDate(send_date);

            FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
            fwVerInfoVO.setFw_type(fw_type);
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

    private void notifyVerUpgradeParsingData(CommonVO commonVO) {

    }

    public void protocolErrorMsgSend(CommonVO commonVO) {

        System.out.println("필수항목 누락으로 인한 전문 오류 입니다. 받은 uuid : " + commonVO.getUuid() + ", 받은 send_type : "
                + commonVO.getSendType() + ", 받은 action_type : " + commonVO.getActionType() + ", 받은 data : " + commonVO.getData());

        commonVO.setResponseReceive("0");
        commonVO.setResponseReason("11");
        sendMessage(commonVO);

        return;
    }

    //파리미터 오류
    public void parameterErrorMsgSend(CommonVO commonVO) {
        System.out.println("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());

        commonVO.setResponseReceive("0");   //실패
        commonVO.setResponseReason("12");   //파리미터 오류
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
