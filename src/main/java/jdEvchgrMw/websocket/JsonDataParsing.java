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

public class JsonDataParsing {

    CollectServiceBean csb = new CollectServiceBean();

    /**
     * JSON DATA PARSING MAIN
     *
     * @param commonVO - 전송 정보가 담겨있는 VO
     * @throws Exception
     */
    public void jsonDataParsingMain(CommonVO commonVO) {

        JSONParser jParser = new JSONParser();
        JSONObject jObject = new JSONObject();

        try {

            jObject = (JSONObject) jParser.parse(commonVO.getRcvMsg());

            /*COMMON PARSING*/

            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월 dd일 HH시 mm분 ss초");
            SimpleDateFormat responseDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss");

            Date dt = new Date();
            String time = sdf.format(dt);

            System.out.println(time + " - [ 충전소 : " + commonVO.getStationId() + " - 충전기 : "+ commonVO.getChgrId() +" ]");

            String uuid = jObject.get("uuid").toString();
            String send_type = jObject.get("send_type").toString();
            String action_type = jObject.get("action_type").toString();
            String data = jObject.get("data").toString();

            commonVO.setUuid(uuid);
            commonVO.setSendType(send_type);
            commonVO.setActionType(action_type);
            commonVO.setData(data);
            commonVO.setResponseDate(responseDateFormat.format(dt));

            System.out.println("[ responseDate : " + commonVO.getResponseDate() + " ]");

            //데이터 공백 체크
            if (uuid.equals("") || uuid == null || send_type.equals("") || send_type == null || action_type.equals("") || action_type == null || data.equals("") || data == null) {

                protocolErrorMsgSend(commonVO);

            }

            //데이터 정상 -> 파싱 시작
            else {

                if (commonVO.getActionType().equals("chgrInfo")) {
                    chgrInfoParsingData(commonVO);                        //충전기 설치정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chgrStatus")) {
                    chgrStatusParsingData(commonVO);                        //충전기 상태정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("user")) {
                    commonVO = userParsingData(commonVO);                        //사용자 인증요청(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingStart")) {
                    chargingStartParsingData(commonVO);                        //충전 시작정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingInfo")) {
                    chargingInfoParsingData(commonVO);                        //충전 진행정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingEnd")) {
                    chargingEndParsingData(commonVO);                        //충전 완료정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargePayment")) {
                    chargePaymentParsingData(commonVO);                        //충전 결제정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("sendSms")) {
                    sendSmsParsingData(commonVO);                        //문자 전송(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("alarmHistory")) {
                    alarmHistoryParsingData(commonVO);                        //경보 이력(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("reportUpdate")) {
                    reportUpdateParsingData(commonVO);                        //펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargingStart")) {
                    dChargingStartParsingData(commonVO);                        //덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargingEnd")) {
                    dChargingEndParsingData(commonVO);                        //덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargePayment")) {
                    dChargePaymentParsingData(commonVO);                        //덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dAlarmHistory")) {
                    dAlarmHistoryParsingData(commonVO);                        //덤프_경보이력(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dReportUpdate")) {
                    dReportUpdateParsingData(commonVO);                        //덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("reset")) {
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

                    protocolErrorMsgSend(commonVO);
                    return;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();

            protocolErrorMsgSend(commonVO);
            return;

        } catch (Exception e) {
            e.printStackTrace();

            unknownErrorMsgSend(commonVO, "알 수 없는 오류입니다. 담당자에게 문의주세요.");
            return;

        }

        // req Data DB Insert
        try {
            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
            chgrInfoVO.setMsgSendType(commonVO.getSendType());
            chgrInfoVO.setMsgActionType(commonVO.getActionType());
            chgrInfoVO.setMsgData(commonVO.getData());

            //csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

        } catch (Exception e) {
            e.printStackTrace();

            unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
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

            sendJsonObject.put("uuid", commonVO.getUuid());
            sendJsonObject.put("send_type", "res");
            sendJsonObject.put("action_type", commonVO.getActionType());

            JSONObject sendData = new JSONObject();

            sendData.put("station_id", commonVO.getStationId());
            sendData.put("chgr_id", commonVO.getChgrId());
            sendData.put("response_date", commonVO.getResponseDate());
            //sendData.put("req_create_date", commonVO.getReqCreateDate());
            sendData.put("response_receive", commonVO.getResponseReceive());
            sendData.put("response_reason", commonVO.getResponseReason());

            //응답 성공 -> data Json으로 만들어서 보내줌 실패시 data 없이 보냄
            if (commonVO.getResponseReceive().equals("1")) {

                if (commonVO.getActionType().equals("chgrInfo")) {

                    //sendData.put("price_apply_yn", "1");

                    JSONArray unit_prices_array = new JSONArray();

                    JSONObject unit_prices_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;
                        String value = String.valueOf(i);

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        unit_prices_data.put(key, value);
                    }

                    unit_prices_array.add(unit_prices_data);

                    sendData.put("unit_prices", unit_prices_array);


                    JSONArray display_brightness_array = new JSONArray();

                    JSONObject display_brightness_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        display_brightness_data.put(key, "100");
                    }

                    display_brightness_array.add(display_brightness_data);

                    sendData.put("display_brightness", display_brightness_array);


                    JSONArray sound_array = new JSONArray();

                    JSONObject sound_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;
                        String value = String.valueOf(i);

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        sound_data.put(key, value);
                    }

                    sound_array.add(sound_data);

                    sendData.put("sound", sound_array);

                } else if (commonVO.getActionType().equals("chgrStatus")    || commonVO.getActionType().equals("chargingStart") || commonVO.getActionType().equals("chargingInfo")
                        || commonVO.getActionType().equals("chargingEnd")   || commonVO.getActionType().equals("chargePayment") || commonVO.getActionType().equals("sendSms")
                        || commonVO.getActionType().equals("alarmHistory")  || commonVO.getActionType().equals("reportUpdate")   || commonVO.getActionType().equals("dChargingStart")
                        || commonVO.getActionType().equals("dChargingEnd")  || commonVO.getActionType().equals("dChargePayment")|| commonVO.getActionType().equals("dAlarmHistory")
                        || commonVO.getActionType().equals("dReportUpdate") || commonVO.getActionType().equals("chgrStatus")    || commonVO.getActionType().equals("chgrStatus")) {


                } else if (commonVO.getActionType().equals("user")) {        //사용자 인증요청(충전기 -> 충전기정보시스템) CALL

                    sendData.put("card_num", commonVO.getUserVO().getMemAuthInputNo());
                    sendData.put("valid", commonVO.getUserVO().getValidYn());
                    sendData.put("member_company", commonVO.getUserVO().getBId());
                    sendData.put("current_unit_cost", commonVO.getUserVO().getCurrentUnitCost());

                } else if (commonVO.getActionType().equals("reset")) {
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
            }

            sendJsonObject.put("data", sendData);

            System.out.println("보낼 JSON : " + sendJsonObject);

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

    /*충전기 설치정보(충전기 -> 충전기정보시스템)*/
    private void chgrInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String gps_xpos = (String) data.get("gps_xpos");
            String gps_ypos = (String) data.get("gps_ypos");
            String charger_type = (String) data.get("charger_type");
            String charger_plug_type = (String) data.get("charger_plug_type");
            String charger_mf = (String) data.get("charger_mf");
            String m2m_mf = (String) data.get("m2m_mf");
            String rf_mf = (String) data.get("rf_mf");
            String m2m_tel = (String) data.get("m2m_tel");
            String van_ip = (String) data.get("van_ip");
            int van_port = Integer.parseInt((String) data.get("van_port"));
            int offline_free_charge_W = Integer.parseInt((String) data.get("offline_free_charge_W"));
            String charger_pay_yn = (String) data.get("charger_pay_yn");

            ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();
            ArrayList<PlugDetlInfoVO> plugDetlInfoVOList = new ArrayList<>();

            JSONArray fwVerInfoArr = (JSONArray) data.get("fw_ver_info");
            JSONArray plugDetlInfoArr = (JSONArray) data.get("plug_detl_info");

            for (int i = 0; i < fwVerInfoArr.size(); i++) {
                JSONObject tmp = (JSONObject) fwVerInfoArr.get(i);

                String curr_ver = (String) tmp.get("curr_ver");
                String fw_type = (String) tmp.get("fw_type");

                FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
                fwVerInfoVO.setCurrVer(curr_ver);
                fwVerInfoVO.setFwType(fw_type);
                fwVerInfoVOList.add(fwVerInfoVO);
            }

            for (int i = 0; i < plugDetlInfoArr.size(); i++) {
                JSONObject tmp = (JSONObject) plugDetlInfoArr.get(i);

                String plug_id = (String) tmp.get("plug_id");
                String plug_type = (String) tmp.get("plug_type");

                PlugDetlInfoVO plugDetlInfoVO = new PlugDetlInfoVO();
                plugDetlInfoVO.setPlug_id(plug_id);
                plugDetlInfoVO.setPlug_type(plug_type);
                plugDetlInfoVOList.add(plugDetlInfoVO);
            }

            System.out.println("<----------- 충전기 설치정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("gps_xpos : " + gps_xpos);
            System.out.println("gps_ypos : " + gps_ypos);
            System.out.println("charger_type : " + charger_type);
            System.out.println("charger_mf : " + charger_mf);
            System.out.println("m2m_mf : " + m2m_mf);
            System.out.println("rf_mf : " + rf_mf);
            System.out.println("m2m_tel : " + m2m_tel);
            System.out.println("van_ip : " + van_ip);
            System.out.println("van_port : " + van_port);
            System.out.println("offline_free_charge_W : " + offline_free_charge_W);
            System.out.println("charger_pay_yn : " + charger_pay_yn);
            System.out.println("fwVerInfoVOList : " + fwVerInfoVOList);
            System.out.println("plugDetlInfoVOList : " + plugDetlInfoVOList);
            System.out.println("<------------------------------------------------>");

            // req Data DB Insert

            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

            //추후 PROVIDER_ID 하드코딩한거 수정해야 함
            //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
            chgrInfoVO.setProviderId("JD");
            chgrInfoVO.setStId(commonVO.getStationId());
            chgrInfoVO.setChgrId(commonVO.getChgrId());
            chgrInfoVO.setMwKindCd("WS");
            chgrInfoVO.setSpeedTpCd(charger_type);
            chgrInfoVO.setGpsXpos(gps_xpos.equals("") ? "0.0" : gps_xpos);
            chgrInfoVO.setGpsYpos(gps_ypos.equals("") ? "0.0" : gps_ypos);
            chgrInfoVO.setChgrTypeCd(charger_plug_type);
            chgrInfoVO.setMfCd(charger_mf);
            chgrInfoVO.setM2mMfCd(m2m_mf);
            chgrInfoVO.setRfMfCd(rf_mf);
            chgrInfoVO.setM2mTel(m2m_tel);
            chgrInfoVO.setVanIp(van_ip);
            chgrInfoVO.setVanPort(van_port);
            chgrInfoVO.setOfflineFreeWh(offline_free_charge_W);
            chgrInfoVO.setMwSession(commonVO.getStationId() + commonVO.getChgrId());
            chgrInfoVO.setModUid("MW");
            chgrInfoVO.setPayingYn(charger_pay_yn);
            chgrInfoVO.setChQty(String.valueOf(plugDetlInfoVOList.size()));

            System.out.println("<----------------------- 설치 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrInfoUpdate(chgrInfoVO));

            for (int i=0; i<plugDetlInfoVOList.size(); i++) {
                chgrInfoVO.setChId(Integer.parseInt(plugDetlInfoVOList.get(i).getPlug_id()));
                chgrInfoVO.setPlugTypeCd(plugDetlInfoVOList.get(i).getPlug_type());
                System.out.println("<----------------------- 채널 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrChInfoInsUpdate(chgrInfoVO));
            }

            for (int i=0; i<fwVerInfoVOList.size(); i++) {
                FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
                fwVerInfoVO.setFwType(fwVerInfoVOList.get(i).getFwType());
                fwVerInfoVO.setCurrVer(fwVerInfoVOList.get(i).getCurrVer());
                chgrInfoVO.setFwVerInfoVO(fwVerInfoVO);
                System.out.println("<----------------------- 펌웨 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrChInfoInsUpdate(chgrInfoVO));
            }

        } catch (ParseException e) {
            e.printStackTrace();

            parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("<----------------------- DB Insert 오류 ------------------------->");
            unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            return;
        }
    }

    //충전기 상태정보(충전기 -> 충전기정보시스템) CALL
    private void chgrStatusParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String mode = (String) data.get("mode");
            String integrated_power = (String) data.get("integrated_power");
            String powerbox = (String) data.get("powerbox");

            ArrayList<ChargerPlugVO> chargerPlugList = new ArrayList<>();

            JSONArray chargerPlugArr = (JSONArray) data.get("charger_plug");

            for (int i = 0; i < chargerPlugArr.size(); i++) {
                JSONObject tmp = (JSONObject) chargerPlugArr.get(i);

                String plug_id = (String) tmp.get("plug_id");
                String charging_state = (String) tmp.get("charging_state");
                String plug_door = (String) tmp.get("plug_door");
                String plug_state = (String) tmp.get("plug_state");

                ChargerPlugVO chargerPlugVO = new ChargerPlugVO();
                chargerPlugVO.setPlug_id(plug_id);
                chargerPlugVO.setCharging_state(charging_state);
                chargerPlugVO.setPlug_door(plug_door);
                chargerPlugVO.setPlug_state(plug_state);
                chargerPlugList.add(chargerPlugVO);
            }

            System.out.println("<----------- 충전기 상태정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("mode : " + mode);
            System.out.println("integrated_power : " + integrated_power);
            System.out.println("powerbox : " + powerbox);
            System.out.println("chargerPlugList : " + chargerPlugList);
            System.out.println("<------------------------------------------------>");

            // req Data DB Insert


            ChgrStatusVO chgrStatusVO = new ChgrStatusVO();
            chgrStatusVO.setProviderId("JD");
            chgrStatusVO.setStId(commonVO.getStationId());
            chgrStatusVO.setChgrId(commonVO.getChgrId());
            chgrStatusVO.setOpModeCd(mode);
            chgrStatusVO.setIntegratedKwh(integrated_power);
            chgrStatusVO.setPowerboxState(powerbox);
            chgrStatusVO.setStateDt(create_date);
            chgrStatusVO.setMwKindCd("WS");
            chgrStatusVO.setRTimeYn("Y");
            chgrStatusVO.setChgrTxDt(create_date);

            for (int i=0; i < chargerPlugList.size(); i++) {

                if (chargerPlugList.get(i).getPlug_id().equals("1")) {

                    chgrStatusVO.setCh1RechgStateCd(chargerPlugList.get(i).getCharging_state());
                    chgrStatusVO.setCh1DoorStateCd(chargerPlugList.get(i).getPlug_door());
                    chgrStatusVO.setCh1PlugStateCd(chargerPlugList.get(i).getPlug_state());

                } else if (chargerPlugList.get(i).getPlug_id().equals("2")) {

                    chgrStatusVO.setCh2RechgStateCd(chargerPlugList.get(i).getCharging_state());
                    chgrStatusVO.setCh2DoorStateCd(chargerPlugList.get(i).getPlug_door());
                    chgrStatusVO.setCh2PlugStateCd(chargerPlugList.get(i).getPlug_state());

                } else if (chargerPlugList.get(i).getPlug_id().equals("3")) {

                    chgrStatusVO.setCh3RechgStateCd(chargerPlugList.get(i).getCharging_state());
                    chgrStatusVO.setCh3DoorStateCd(chargerPlugList.get(i).getPlug_door());
                    chgrStatusVO.setCh3PlugStateCd(chargerPlugList.get(i).getPlug_state());

                }
            }

            System.out.println("<----------------------- Insert OK -------------------------> : " + csb.chgrStateService().chgrStateInsert(chgrStatusVO));
            System.out.println("<----------------------- Update OK -------------------------> : " + csb.chgrStateService().chgrCurrStateUpdate(chgrStatusVO));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("<----------------------- DB Insert 오류 ------------------------->");
            unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            return;
        }
    }

    //사용자 인증요청(충전기 -> 충전기정보시스템) CALL
    private CommonVO userParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String card_num = (String) data.get("card_num");

            System.out.println("<----------- 사용자 인증요청 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("card_num : " + card_num);
            System.out.println("<------------------------------------------------>");

            UserVO userVO = new UserVO();
            userVO.setMemAuthInputNo(card_num);

            UserVO resultUserVO = csb.userService().userAuthSelect(userVO);

            System.out.println("<----------------------- 사용자 인증 OK -------------------------> : " + resultUserVO);
            System.out.println("<----------------------- 회원 인증 이력 등록 OK -------------------------> : " + csb.userService().userAuthListInert(resultUserVO));

            commonVO.setUserVO(resultUserVO);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("<----------------------- DB Insert 오류 ------------------------->");
            unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
        }

        return commonVO;
    }

    //충전 시작정보(충전기 -> 충전기정보시스템) CALL
    private void chargingStartParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String start_date = (String) data.get("start_date");
            String card_num = (String) data.get("card_num");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String pay_type = (String) data.get("pay_type");
            String charge_plug_type = (String) data.get("charge_plug_type");
            int plug_id = Integer.parseInt((String) data.get("plug_id"));
            int integrated_power = Integer.parseInt((String) data.get("integrated_power"));
            int prepayment = Integer.parseInt((String) data.get("prepayment"));
            String current_V = (String) data.get("current_V");
            String current_A = (String) data.get("current_A");
            String estimated_charge_time = (String) data.get("estimated_charge_time");


            System.out.println("<----------- 충전 시작정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("start_date : " + start_date);
            System.out.println("card_num : " + card_num);
            System.out.println("credit_trx_no : " + credit_trx_no);
            System.out.println("credit_trx_date : " + credit_trx_date);
            System.out.println("pay_type : " + pay_type);
            System.out.println("charge_plug_type : " + charge_plug_type);
            System.out.println("plug_id : " + plug_id);
            System.out.println("integrated_power : " + integrated_power);
            System.out.println("prepayment : " + prepayment);
            System.out.println("current_V : " + current_V);
            System.out.println("current_A : " + current_A);
            System.out.println("estimated_charge_time : " + estimated_charge_time);
            System.out.println("<------------------------------------------------>");

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId("JD");
            chargeVO.setStId(commonVO.getStationId());
            chargeVO.setChgrId(commonVO.getChgrId());
            chargeVO.setMwKindCd("WS");
            chargeVO.setRTimeYn("Y");
            chargeVO.setChId(plug_id);
            chargeVO.setPlugTypeCd(charge_plug_type);
            chargeVO.setMemAuthInputNo(card_num);
            chargeVO.setRechgSdt(start_date);
            chargeVO.setRechgDemandAmt(prepayment);
            chargeVO.setRechgEstTime(estimated_charge_time);
            chargeVO.setPayTypeCd(pay_type);
            chargeVO.setCreditPPayTrxNo(credit_trx_no);
            chargeVO.setCreditPPayTrxDt(credit_trx_date);
            chargeVO.setIntegratedWh(integrated_power);
            chargeVO.setCurrVolt(current_V);
            chargeVO.setCurrC(current_A);
            chargeVO.setDataCreateDt(create_date);
            chargeVO.setChgrTxDt(send_date);

            System.out.println("<----------------------- 충전 시작정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgSttInsert(chargeVO));


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("<----------------------- DB Insert 오류 ------------------------->");
            unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            return;
        }
    }

    //충전 진행정보(충전기 -> 충전기정보시스템) CALL
    private void chargingInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String start_date = (String) data.get("start_date");
            String card_num = (String) data.get("card_num");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String pay_type = (String) data.get("pay_type");
            String charger_pay_yn = (String) data.get("charger_pay_yn");
            String charge_plug_type = (String) data.get("charge_plug_type");
            String plug_id = (String) data.get("plug_id");
            String integrated_power = (String) data.get("integrated_power");
            String prepayment = (String) data.get("prepayment");
            String current_V = (String) data.get("current_V");
            String current_A = (String) data.get("current_A");
            String estimated_charge_time = (String) data.get("estimated_charge_time");
            String charge_W = (String) data.get("charge_W");
            String charge_cost = (String) data.get("charge_cost");


            System.out.println("<----------- 충전 진행정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("start_date : " + start_date);
            System.out.println("card_num : " + card_num);
            System.out.println("credit_trx_no : " + credit_trx_no);
            System.out.println("credit_trx_date : " + credit_trx_date);
            System.out.println("pay_type : " + pay_type);
            System.out.println("charger_pay_yn : " + charger_pay_yn);
            System.out.println("charge_plug_type : " + charge_plug_type);
            System.out.println("plug_id : " + plug_id);
            System.out.println("integrated_power : " + integrated_power);
            System.out.println("prepayment : " + prepayment);
            System.out.println("current_V : " + current_V);
            System.out.println("current_A : " + current_A);
            System.out.println("estimated_charge_time : " + estimated_charge_time);
            System.out.println("charge_W : " + charge_W);
            System.out.println("charge_cost : " + charge_cost);
            System.out.println("<------------------------------------------------>");


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //충전 완료정보(충전기 -> 충전기정보시스템) CALL
    private void chargingEndParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String start_date = (String) data.get("start_date");
            String card_num = (String) data.get("card_num");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String pay_type = (String) data.get("pay_type");
            String charger_pay_yn = (String) data.get("charger_pay_yn");
            String charge_plug_type = (String) data.get("charge_plug_type");
            String plug_id = (String) data.get("plug_id");
            String integrated_power = (String) data.get("integrated_power");
            String prepayment = (String) data.get("prepayment");
            String current_V = (String) data.get("current_V");
            String current_A = (String) data.get("current_A");
            String charge_time = (String) data.get("charge_time");
            String charge_W = (String) data.get("charge_W");
            String charge_end_type = (String) data.get("charge_end_type");
            String end_date = (String) data.get("end_date");
            String charge_cost = (String) data.get("charge_cost");
            String cancel_cost = (String) data.get("cancel_cost");


            System.out.println("<----------- 충전 완료정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("start_date : " + start_date);
            System.out.println("card_num : " + card_num);
            System.out.println("credit_trx_no : " + credit_trx_no);
            System.out.println("credit_trx_date : " + credit_trx_date);
            System.out.println("pay_type : " + pay_type);
            System.out.println("charger_pay_yn : " + charger_pay_yn);
            System.out.println("charge_plug_type : " + charge_plug_type);
            System.out.println("plug_id : " + plug_id);
            System.out.println("integrated_power : " + integrated_power);
            System.out.println("prepayment : " + prepayment);
            System.out.println("current_V : " + current_V);
            System.out.println("current_A : " + current_A);
            System.out.println("charge_time : " + charge_time);
            System.out.println("charge_W : " + charge_W);
            System.out.println("charge_end_type : " + charge_end_type);
            System.out.println("end_date : " + end_date);
            System.out.println("charge_cost : " + charge_cost);
            System.out.println("cancel_cost : " + cancel_cost);
            System.out.println("<------------------------------------------------>");


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //충전 결제정보(충전기 -> 충전기정보시스템) CALL
    private void chargePaymentParsingData(CommonVO commonVO) {
        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String prepayment_trx_no = (String) data.get("prepayment_trx_no");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String payment_type = (String) data.get("payment_type");
            String charge_plug_type = (String) data.get("charge_plug_type");
            String plug_id = (String) data.get("plug_id");
            String payment_amount = (String) data.get("payment_amount");

            System.out.println("<----------- 충전 결제정보 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("prepayment_trx_no : " + prepayment_trx_no);
            System.out.println("credit_trx_no : " + credit_trx_no);
            System.out.println("credit_trx_date : " + credit_trx_date);
            System.out.println("payment_type : " + payment_type);
            System.out.println("charge_plug_type : " + charge_plug_type);
            System.out.println("plug_id : " + plug_id);
            System.out.println("payment_amount : " + payment_amount);
            System.out.println("<------------------------------------------------>");


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //문자 전송(충전기 -> 충전기정보시스템) CALL
    private void sendSmsParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String plug_id = (String) data.get("plug_id");
            String charge_cost = (String) data.get("charge_cost");
            String charge_time = (String) data.get("charge_time");
            String charge_W = (String) data.get("charge_W");
            String msg_kind = (String) data.get("msg_kind");
            String phone_no = (String) data.get("phone_no");

            System.out.println("<----------- 문자 전송 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("plug_id : " + plug_id);
            System.out.println("charge_cost : " + charge_cost);
            System.out.println("charge_time : " + charge_time);
            System.out.println("charge_W : " + charge_W);
            System.out.println("msg_kind : " + msg_kind);
            System.out.println("phone_no : " + phone_no);
            System.out.println("<------------------------------------------------>");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //경보 이력(충전기 -> 충전기정보시스템) CALL
    private void alarmHistoryParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String alarm_plug = (String) data.get("alarm_plug");
            String alarm_type = (String) data.get("alarm_type");
            String alarm_date = (String) data.get("alarm_date");
            String alarm_code = (String) data.get("alarm_code");

            System.out.println("<----------- 경보 이력 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("alarm_plug : " + alarm_plug);
            System.out.println("alarm_type : " + alarm_type);
            System.out.println("alarm_date : " + alarm_date);
            System.out.println("alarm_code : " + alarm_code);
            System.out.println("<------------------------------------------------>");

            // req Data DB Insert
            try {
                AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

                //추후 PROVIDER_ID 하드코딩한거 수정해야 함
                //chgrInfoVO.setProviderId(commonVO.getStationId().substring(0,2));
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setProviderId("JD");
                alarmHistoryVO.setStId(commonVO.getStationId());
                alarmHistoryVO.setChId(alarm_plug);
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
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
    private void reportUpdateParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String update_result = (String) data.get("update_result");

            ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

            JSONArray fwVerInfoArr = (JSONArray) data.get("fw_ver_info");

            for (int i = 0; i < fwVerInfoArr.size(); i++) {
                JSONObject tmp = (JSONObject) fwVerInfoArr.get(i);

                String curr_ver = (String) tmp.get("curr_ver");
                String fw_type = (String) tmp.get("fw_type");

                FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
                fwVerInfoVO.setCurrVer(curr_ver);
                fwVerInfoVO.setFwType(fw_type);
                fwVerInfoVOList.add(fwVerInfoVO);
            }

            System.out.println("<----------- 펌웨어 업데이트 결과 알림 Parsing Data ----------->");
            System.out.println("send_date : " + send_date);
            System.out.println("create_date : " + create_date);
            System.out.println("update_result : " + update_result);
            System.out.println("fwVerInfoVOList : " + fwVerInfoVOList);
            System.out.println("<------------------------------------------------>");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL
    private void dChargingStartParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String start_date = (String) tmp.get("start_date");
                String card_num = (String) tmp.get("card_num");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String pay_type = (String) tmp.get("pay_type");
                String charger_pay_yn = (String) tmp.get("charger_pay_yn");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                String plug_id = (String) tmp.get("plug_id");
                String integrated_power = (String) tmp.get("integrated_power");
                String prepayment = (String) tmp.get("prepayment");
                String current_V = (String) tmp.get("current_V");
                String current_A = (String) tmp.get("current_A");
                String estimated_charge_time = (String) tmp.get("estimated_charge_time");
                String charge_W = (String) tmp.get("charge_W");
                String charge_cost = (String) tmp.get("charge_cost");


                System.out.println("<----------- 덤프_충전 시작정보 Parsing Data " + (i + 1) + " ----------->");
                System.out.println("send_date : " + send_date);
                System.out.println("create_date : " + create_date);
                System.out.println("start_date : " + start_date);
                System.out.println("card_num : " + card_num);
                System.out.println("credit_trx_no : " + credit_trx_no);
                System.out.println("credit_trx_date : " + credit_trx_date);
                System.out.println("pay_type : " + pay_type);
                System.out.println("charger_pay_yn : " + charger_pay_yn);
                System.out.println("charge_plug_type : " + charge_plug_type);
                System.out.println("plug_id : " + plug_id);
                System.out.println("integrated_power : " + integrated_power);
                System.out.println("prepayment : " + prepayment);
                System.out.println("current_V : " + current_V);
                System.out.println("current_A : " + current_A);
                System.out.println("estimated_charge_time : " + estimated_charge_time);
                System.out.println("charge_W : " + charge_W);
                System.out.println("charge_cost : " + charge_cost);
                System.out.println("<------------------------------------------------>");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL
    private void dChargingEndParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String start_date = (String) tmp.get("start_date");
                String card_num = (String) tmp.get("card_num");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String pay_type = (String) tmp.get("pay_type");
                String charger_pay_yn = (String) tmp.get("charger_pay_yn");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                String plug_id = (String) tmp.get("plug_id");
                String integrated_power = (String) tmp.get("integrated_power");
                String prepayment = (String) tmp.get("prepayment");
                String current_V = (String) tmp.get("current_V");
                String current_A = (String) tmp.get("current_A");
                String charge_time = (String) tmp.get("charge_time");
                String charge_W = (String) tmp.get("charge_W");
                String charge_end_type = (String) tmp.get("charge_end_type");
                String end_date = (String) tmp.get("end_date");
                String charge_cost = (String) tmp.get("charge_cost");
                String cancel_cost = (String) tmp.get("cancel_cost");

                System.out.println("<----------- 덤프_충전 완료정보 Parsing Data " + (i + 1) + " ----------->");
                System.out.println("send_date : " + send_date);
                System.out.println("create_date : " + create_date);
                System.out.println("start_date : " + start_date);
                System.out.println("card_num : " + card_num);
                System.out.println("credit_trx_no : " + credit_trx_no);
                System.out.println("credit_trx_date : " + credit_trx_date);
                System.out.println("pay_type : " + pay_type);
                System.out.println("charger_pay_yn : " + charger_pay_yn);
                System.out.println("charge_plug_type : " + charge_plug_type);
                System.out.println("plug_id : " + plug_id);
                System.out.println("integrated_power : " + integrated_power);
                System.out.println("prepayment : " + prepayment);
                System.out.println("current_V : " + current_V);
                System.out.println("current_A : " + current_A);
                System.out.println("charge_time : " + charge_time);
                System.out.println("charge_W : " + charge_W);
                System.out.println("charge_end_type : " + charge_end_type);
                System.out.println("end_date : " + end_date);
                System.out.println("charge_cost : " + charge_cost);
                System.out.println("cancel_cost : " + cancel_cost);
                System.out.println("<------------------------------------------------>");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL
    private void dChargePaymentParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String prepayment_trx_no = (String) tmp.get("prepayment_trx_no");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String payment_type = (String) tmp.get("payment_type");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                String plug_id = (String) tmp.get("plug_id");
                String payment_amount = (String) tmp.get("payment_amount");

                System.out.println("<----------- 덤프_충전 결제정보 Parsing Data " + (i + 1) + " ----------->");
                System.out.println("send_date : " + send_date);
                System.out.println("create_date : " + create_date);
                System.out.println("prepayment_trx_no : " + prepayment_trx_no);
                System.out.println("credit_trx_no : " + credit_trx_no);
                System.out.println("credit_trx_date : " + credit_trx_date);
                System.out.println("payment_type : " + payment_type);
                System.out.println("charge_plug_type : " + charge_plug_type);
                System.out.println("plug_id : " + plug_id);
                System.out.println("payment_amount : " + payment_amount);
                System.out.println("<------------------------------------------------>");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //덤프_경보이력(충전기 -> 충전기정보시스템) CALL
    private void dAlarmHistoryParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String alarm_type = (String) tmp.get("alarm_type");
                String alarm_date = (String) tmp.get("alarm_date");
                String alarm_code = (String) tmp.get("alarm_code");

                System.out.println("<----------- 덤프_경보이력 Parsing Data " + (i + 1) + "  ----------->");
                System.out.println("send_date : " + send_date);
                System.out.println("create_date : " + create_date);
                System.out.println("alarm_type : " + alarm_type);
                System.out.println("alarm_date : " + alarm_date);
                System.out.println("alarm_code : " + alarm_code);
                System.out.println("<------------------------------------------------>");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    //덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
    private void dReportUpdateParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            System.out.println("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String update_result = (String) tmp.get("update_result");

                ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

                JSONArray fwVerInfoArr = (JSONArray) tmp.get("fw_ver_info");

                for (int j = 0; j < fwVerInfoArr.size(); j++) {
                    JSONObject jTmp = (JSONObject) fwVerInfoArr.get(j);

                    String curr_ver = (String) jTmp.get("curr_ver");
                    String fw_type = (String) jTmp.get("fw_type");

                    FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
                    fwVerInfoVO.setCurrVer(curr_ver);
                    fwVerInfoVO.setFwType(fw_type);
                    fwVerInfoVOList.add(fwVerInfoVO);
                }

                System.out.println("<----------- 덤프_펌웨어 업데이트 결과 알림 Parsing Data " + (i + 1) + " ----------->");
                System.out.println("send_date : " + send_date);
                System.out.println("create_date : " + create_date);
                System.out.println("update_result : " + update_result);
                System.out.println("fwVerInfoVOList : " + fwVerInfoVOList);
                System.out.println("<------------------------------------------------>");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            parameterErrorMsgSend(commonVO);
        }
    }

    private void resetParsingData(CommonVO commonVO) {

    }

    private void pricesParsingData(CommonVO commonVO) {

    }

    private void changeModeParsingData(CommonVO commonVO) {

    }

    private void displayBrightnessParsingData(CommonVO commonVO) {

    }

    private void soundParsingData(CommonVO commonVO) {

    }

    private void askVerParsingData(CommonVO commonVO) {

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
