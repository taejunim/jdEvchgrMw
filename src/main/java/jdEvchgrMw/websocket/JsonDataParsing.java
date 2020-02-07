package jdEvchgrMw.websocket;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger(JsonDataParsing.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
    SimpleDateFormat responseDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

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

            /* PARSING START */

            Date dt = new Date();
            String time = sdf.format(dt);

            logger.info(time + " - [ 충전소 : " + commonVO.getStationId() + " - 충전기 : " + commonVO.getChgrId() + " ]");

            String uuid = jObject.get("uuid").toString();
            String send_type = jObject.get("send_type").toString();
            String action_type = jObject.get("action_type").toString();
            String data = jObject.get("data").toString();

            commonVO.setUuid(uuid);
            commonVO.setSendType(send_type);
            commonVO.setActionType(action_type);
            commonVO.setData(data);
            commonVO.setResponseDate(responseDateFormat.format(dt));

            commonVO.setResponseReceive("1");
            commonVO.setResponseReason("");
            commonVO.setRTimeYn("Y");

            String chgrCheckValidation;
            String chgrCheckValidationText;

            //충전기 유효 체크
            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
            chgrInfoVO.setProviderId(commonVO.getProviderId());
            chgrInfoVO.setStId(commonVO.getStationId());
            chgrInfoVO.setChgrId(commonVO.getChgrId());

            CollectServiceBean csb = new CollectServiceBean();
            chgrCheckValidation = csb.beanChgrInfoService().chgrCheckValidation(chgrInfoVO);
            chgrCheckValidationText = chgrCheckValidation.equals("1") ? "유효한" : "무효한";

            logger.info("[충전기 -> MW]");
            logger.info("providerId : " + commonVO.getProviderId());
            logger.info("stId : " + commonVO.getStationId());
            logger.info("chgrId : " + commonVO.getChgrId());
            logger.info(chgrCheckValidationText + " 충전기 [ responseDate : " + commonVO.getResponseDate() + " ]");

            //무효한 충전기 -> 요청실패 응답 : 13
            if (chgrCheckValidation.equals("0")) {

                commonVO.setRTimeYn(action_type.substring(0,1).equals("d") ? "N" : "Y");
                commonVO.setSendDate(responseDateFormat.format(dt));
                commonVO.setCreateDate(responseDateFormat.format(dt));
                invalidErrorMsgSend(commonVO);
                return;
            }

            //데이터 공백 체크
            if (uuid.equals("") || uuid == null || send_type.equals("") || send_type == null || action_type.equals("") || action_type == null || data.equals("") || data == null) {

                protocolErrorMsgSend(commonVO);
                return;
            }

            //데이터 정상 -> 파싱 시작
            else {

                if (commonVO.getActionType().equals("chgrInfo")) {
                    commonVO = chgrInfoParsingData(commonVO);                        //충전기 설치정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chgrStatus")) {
                    commonVO = chgrStatusParsingData(commonVO);                        //충전기 상태정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("user")) {
                    commonVO = userParsingData(commonVO);                        //사용자 인증요청(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingStart")) {
                    commonVO = chargingStartParsingData(commonVO);                        //충전 시작정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingInfo")) {
                    commonVO = chargingInfoParsingData(commonVO);                        //충전 진행정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargingEnd")) {
                    commonVO = chargingEndParsingData(commonVO);                        //충전 완료정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("chargePayment")) {
                    commonVO = chargePaymentParsingData(commonVO);                        //충전 결제정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("sendSms")) {
                    commonVO = sendSmsParsingData(commonVO);                        //문자 전송(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("alarmHistory")) {
                    commonVO = alarmHistoryParsingData(commonVO);                        //경보 이력(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("reportUpdate")) {
                    commonVO = reportUpdateParsingData(commonVO);                        //펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("paymentInfo")) {
                    commonVO = paymentInfoParsingData(commonVO);                        //신용승인 결제정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargingStart")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dChargingStartParsingData(commonVO);                        //덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargingEnd")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dChargingEndParsingData(commonVO);                        //덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dChargePayment")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dChargePaymentParsingData(commonVO);                        //덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dAlarmHistory")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dAlarmHistoryParsingData(commonVO);                        //덤프_경보이력(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dReportUpdate")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dReportUpdateParsingData(commonVO);                        //덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

                } else if (commonVO.getActionType().equals("dPaymentInfo")) {
                    commonVO.setRTimeYn("N");
                    commonVO = dPaymentInfoParsingData(commonVO);                        //덤프_신용승인 결제정보(충전기 -> 충전기정보시스템) CALL

                }

                //충전기 제어 응답(충전기 -> M/W) CALL
                else if (commonVO.getActionType().equals("reset") || commonVO.getActionType().equals("prices") || commonVO.getActionType().equals("changeMode")
                        || commonVO.getActionType().equals("displayBrightness") || commonVO.getActionType().equals("sound") || commonVO.getActionType().equals("askVer")) {

                    controlResponse(commonVO);
                    return;

                } else {
                    logger.info("[ 정의 되지 않은 PACKET 입니다. ]");

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
            commonVO = unknownErrorMsgSend(commonVO, "알 수 없는 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        //응답 보내기 - 응답시에는 다시 사업자ID + 충전소ID로 보내야함
        commonVO.setStationId(commonVO.getProviderId() + commonVO.getStationId());
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
            sendData.put("response_receive", commonVO.getResponseReceive());
            sendData.put("response_reason", commonVO.getResponseReason());

            //응답 성공 -> data Json으로 만들어서 보내줌 실패시 data 없이 보냄
            if (commonVO.getResponseReceive().equals("1")) {

                //충전기 설치 정보
                if (commonVO.getActionType().equals("chgrInfo")) {

                    JSONArray unit_prices_array = new JSONArray();

                    JSONObject unit_prices_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        unit_prices_data.put(key, commonVO.getPrice());
                    }

                    unit_prices_array.add(unit_prices_data);

                    sendData.put("unit_prices", unit_prices_array);


                    JSONArray display_brightness_array = new JSONArray();

                    JSONObject display_brightness_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;
                        String value = "";

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        if (i == 0) {
                            value = commonVO.getDeviceConfigVO().getBrightH00();
                        } else if (i == 1) {
                            value = commonVO.getDeviceConfigVO().getBrightH01();
                        } else if (i == 2) {
                            value = commonVO.getDeviceConfigVO().getBrightH02();
                        } else if (i == 3) {
                            value = commonVO.getDeviceConfigVO().getBrightH03();
                        } else if (i == 4) {
                            value = commonVO.getDeviceConfigVO().getBrightH04();
                        } else if (i == 5) {
                            value = commonVO.getDeviceConfigVO().getBrightH05();
                        } else if (i == 6) {
                            value = commonVO.getDeviceConfigVO().getBrightH06();
                        } else if (i == 7) {
                            value = commonVO.getDeviceConfigVO().getBrightH07();
                        } else if (i == 8) {
                            value = commonVO.getDeviceConfigVO().getBrightH08();
                        } else if (i == 9) {
                            value = commonVO.getDeviceConfigVO().getBrightH09();
                        } else if (i == 10) {
                            value = commonVO.getDeviceConfigVO().getBrightH10();
                        } else if (i == 11) {
                            value = commonVO.getDeviceConfigVO().getBrightH11();
                        } else if (i == 12) {
                            value = commonVO.getDeviceConfigVO().getBrightH12();
                        } else if (i == 13) {
                            value = commonVO.getDeviceConfigVO().getBrightH13();
                        } else if (i == 14) {
                            value = commonVO.getDeviceConfigVO().getBrightH14();
                        } else if (i == 15) {
                            value = commonVO.getDeviceConfigVO().getBrightH15();
                        } else if (i == 16) {
                            value = commonVO.getDeviceConfigVO().getBrightH16();
                        } else if (i == 17) {
                            value = commonVO.getDeviceConfigVO().getBrightH17();
                        } else if (i == 18) {
                            value = commonVO.getDeviceConfigVO().getBrightH18();
                        } else if (i == 19) {
                            value = commonVO.getDeviceConfigVO().getBrightH19();
                        } else if (i == 20) {
                            value = commonVO.getDeviceConfigVO().getBrightH20();
                        } else if (i == 21) {
                            value = commonVO.getDeviceConfigVO().getBrightH21();
                        } else if (i == 22) {
                            value = commonVO.getDeviceConfigVO().getBrightH22();
                        } else if (i == 23) {
                            value = commonVO.getDeviceConfigVO().getBrightH23();
                        }

                        display_brightness_data.put(key, value);
                    }

                    display_brightness_array.add(display_brightness_data);

                    sendData.put("display_brightness", display_brightness_array);


                    JSONArray sound_array = new JSONArray();

                    JSONObject sound_data = new JSONObject();

                    for (int i = 0; i < 24; i++) {

                        String key;
                        String value = "";

                        if (i < 10) {
                            key = "h0" + i;
                        } else {
                            key = "h" + i;
                        }

                        if (i == 0) {
                            value = commonVO.getDeviceConfigVO().getSoundH00();
                        } else if (i == 1) {
                            value = commonVO.getDeviceConfigVO().getSoundH01();
                        } else if (i == 2) {
                            value = commonVO.getDeviceConfigVO().getSoundH02();
                        } else if (i == 3) {
                            value = commonVO.getDeviceConfigVO().getSoundH03();
                        } else if (i == 4) {
                            value = commonVO.getDeviceConfigVO().getSoundH04();
                        } else if (i == 5) {
                            value = commonVO.getDeviceConfigVO().getSoundH05();
                        } else if (i == 6) {
                            value = commonVO.getDeviceConfigVO().getSoundH06();
                        } else if (i == 7) {
                            value = commonVO.getDeviceConfigVO().getSoundH07();
                        } else if (i == 8) {
                            value = commonVO.getDeviceConfigVO().getSoundH08();
                        } else if (i == 9) {
                            value = commonVO.getDeviceConfigVO().getSoundH09();
                        } else if (i == 10) {
                            value = commonVO.getDeviceConfigVO().getSoundH10();
                        } else if (i == 11) {
                            value = commonVO.getDeviceConfigVO().getSoundH11();
                        } else if (i == 12) {
                            value = commonVO.getDeviceConfigVO().getSoundH12();
                        } else if (i == 13) {
                            value = commonVO.getDeviceConfigVO().getSoundH13();
                        } else if (i == 14) {
                            value = commonVO.getDeviceConfigVO().getSoundH14();
                        } else if (i == 15) {
                            value = commonVO.getDeviceConfigVO().getSoundH15();
                        } else if (i == 16) {
                            value = commonVO.getDeviceConfigVO().getSoundH16();
                        } else if (i == 17) {
                            value = commonVO.getDeviceConfigVO().getSoundH17();
                        } else if (i == 18) {
                            value = commonVO.getDeviceConfigVO().getSoundH18();
                        } else if (i == 19) {
                            value = commonVO.getDeviceConfigVO().getSoundH19();
                        } else if (i == 20) {
                            value = commonVO.getDeviceConfigVO().getSoundH20();
                        } else if (i == 21) {
                            value = commonVO.getDeviceConfigVO().getSoundH21();
                        } else if (i == 22) {
                            value = commonVO.getDeviceConfigVO().getSoundH22();
                        } else if (i == 23) {
                            value = commonVO.getDeviceConfigVO().getSoundH23();
                        }

                        sound_data.put(key, value);
                    }

                    sound_array.add(sound_data);

                    sendData.put("sound", sound_array);

                } else if (commonVO.getActionType().equals("chgrStatus") || commonVO.getActionType().equals("chargingStart") || commonVO.getActionType().equals("chargingInfo")
                        || commonVO.getActionType().equals("chargingEnd") || commonVO.getActionType().equals("chargePayment") || commonVO.getActionType().equals("sendSms")
                        || commonVO.getActionType().equals("alarmHistory") || commonVO.getActionType().equals("reportUpdate") || commonVO.getActionType().equals("paymentInfo")
                        || commonVO.getActionType().equals("dChargingStart")|| commonVO.getActionType().equals("dChargingEnd") || commonVO.getActionType().equals("dChargePayment")
                        || commonVO.getActionType().equals("dAlarmHistory") || commonVO.getActionType().equals("dReportUpdate") ||commonVO.getActionType().equals("dPaymentInfo")
                        || commonVO.getActionType().equals("chgrStatus") || commonVO.getActionType().equals("chgrStatus")) {

                }

                //사용자 인증요청(충전기 -> 충전기정보시스템) CALL
                else if (commonVO.getActionType().equals("user")) {

                    sendData.put("card_num", commonVO.getUserVO().getMemAuthInputNo());
                    sendData.put("valid", commonVO.getUserVO().getAuthResult());
                    sendData.put("member_company", commonVO.getUserVO().getBId());
                    sendData.put("current_unit_cost", commonVO.getUserVO().getCurrentUnitCost());

                } else {
                    logger.info("[ 정의 되지 않은 PACKET 입니다. ]");
                    protocolErrorMsgSend(commonVO);
                    return;
                }
            }

            sendJsonObject.put("data", sendData);

            logger.info("보낼 JSON : " + sendJsonObject);

            commonVO.getUserSession().getAsyncRemote().sendText(sendJsonObject.toString());
            commonVO.setResMsg(sendJsonObject.toString());

            //수신 전문 이력 업데이트(M/W -> 충전기)
            commonVO = recvMsg(commonVO);

        } catch (Exception e) {
            e.printStackTrace();
            commonVO = unknownErrorMsgSend(commonVO, "예외 발생 : 클라이언트가 종료되어 MSG를 전송하지 못하였습니다.");
            recvMsg(commonVO);
            return;
        }
    }

    /*충전기 설치정보(충전기 -> 충전기정보시스템)*/
    private CommonVO chgrInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String gps_xpos = (String) data.get("gps_xpos");
            String gps_ypos = (String) data.get("gps_ypos");
            String charger_type = (String) data.get("charger_type");
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

            logger.info("<----------- 충전기 설치정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("gps_xpos : " + gps_xpos);
            logger.info("gps_ypos : " + gps_ypos);
            logger.info("charger_type : " + charger_type);
            logger.info("charger_mf : " + charger_mf);
            logger.info("m2m_mf : " + m2m_mf);
            logger.info("rf_mf : " + rf_mf);
            logger.info("m2m_tel : " + m2m_tel);
            logger.info("van_ip : " + van_ip);
            logger.info("van_port : " + van_port);
            logger.info("offline_free_charge_W : " + offline_free_charge_W);
            logger.info("charger_pay_yn : " + charger_pay_yn);
            logger.info("fwVerInfoVOList : " + fwVerInfoVOList);
            logger.info("plugDetlInfoVOList : " + plugDetlInfoVOList);
            logger.info("<------------------------------------------------>");

            // req Data DB Insert
            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
            chgrInfoVO.setProviderId(commonVO.getProviderId());
            chgrInfoVO.setStId(commonVO.getStationId());
            chgrInfoVO.setChgrId(commonVO.getChgrId());
            chgrInfoVO.setMwKindCd("WS");
            chgrInfoVO.setSpeedTpCd(charger_type);

            //좌표 체크 -> 좌표 정보가 없을 경우 xml 단에서 좌표 업데이트 안 함
            try {
                if (gps_xpos.equals("") || gps_xpos.equals("0.0") || gps_xpos.substring(0,3).indexOf("0.0") != -1 || gps_xpos.equals("0")
                || (int) Double.parseDouble(gps_xpos) == 0 || Double.parseDouble(gps_xpos) == 0.0) {

                    chgrInfoVO.setGpsXpos("0.0");

                } else {

                    chgrInfoVO.setGpsXpos(gps_xpos);
                }

                if (gps_ypos.equals("") || gps_ypos.equals("0.0") || gps_ypos.substring(0,3).indexOf("0.0") != -1 || gps_ypos.equals("0")
                || (int) Double.parseDouble(gps_ypos) == 0 || Double.parseDouble(gps_ypos) == 0.0) {

                    chgrInfoVO.setGpsYpos("0.0");

                } else {

                    chgrInfoVO.setGpsYpos(gps_ypos);
                }

            } catch (NumberFormatException e) {
                logger.info("NumberFormatException : " + e);
                chgrInfoVO.setGpsXpos("0.0");
                chgrInfoVO.setGpsYpos("0.0");
            }

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

            String plugTypeCd = "";

            CollectServiceBean csb = new CollectServiceBean();

            for (int i = 0; i < plugDetlInfoVOList.size(); i++) {

                chgrInfoVO.setChId(Integer.parseInt(plugDetlInfoVOList.get(i).getPlug_id()));
                chgrInfoVO.setPlugTypeCd(plugDetlInfoVOList.get(i).getPlug_type());

                logger.info("<----------------------- 채널 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrChInfoInsUpdate(chgrInfoVO));

                if (plugTypeCd.indexOf(plugDetlInfoVOList.get(i).getPlug_type()) == -1) {
                    plugTypeCd += plugDetlInfoVOList.get(i).getPlug_type();
                }
                logger.info("----------------------- 채널 : " + chgrInfoVO.getChId() + ", plugTypeCd : " + chgrInfoVO.getPlugTypeCd());
            }
            logger.info("<----------------------- 충전기 plugTypeCd -------------------------> : " + plugTypeCd);

            /** 채널 타입
             *  1:DC차데모
             *  2:AC완속
             *  3:DC차데모+AC3상
             *  4:DC콤보
             *  5:DC차데모+DC콤보
             *  6:DC차데모+AC3상+DC콤보
             *  7:AC3상
             *  8:DC콤보2(버스 충전기용)
             *  9:DC콤보2+DC콤보2(버스 충전기용)
             *  *환경부코드에 맞춰 변경됨.
             */

            // 1:DC차데모 + 7:AC3상 -> 3
            if (plugTypeCd.equals("17") || plugTypeCd.equals("71")) {

                chgrInfoVO.setChgrTypeCd("3");
            }

            // 1:DC차데모 + 4:DC콤보 -> 5
            else if (plugTypeCd.equals("14") || plugTypeCd.equals("41")) {

                chgrInfoVO.setChgrTypeCd("5");
            }

            // 1:DC차데모 + 4:DC콤보 -> 6
            else if (plugTypeCd.equals("147") || plugTypeCd.equals("174") || plugTypeCd.equals("417") || plugTypeCd.equals("471") || plugTypeCd.equals("714") || plugTypeCd.equals("741")) {

                chgrInfoVO.setChgrTypeCd("6");
            }

            //그 외 채널이 한 개일 경우
            else if (plugTypeCd.length() == 1) {

                chgrInfoVO.setChgrTypeCd(plugTypeCd);
            }

            //데이터 오류
            else {

                commonVO = parameterErrorMsgSend(commonVO);
                return commonVO;
            }

            chgrInfoVO.setPlugTypeCd(plugTypeCd);

            logger.info("<----------------------- 충전기 chgrTypeCd -------------------------> : " + chgrInfoVO.getChgrTypeCd());
            logger.info("<----------------------- 설치 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrInfoUpdate(chgrInfoVO));

            //단가 조회
            UserVO userVO = new UserVO();
            userVO.setProviderId(commonVO.getProviderId());
            commonVO.setPrice(csb.userService().userPriceSelect(userVO));

            //충전기 사운드 및 밝기 조회
            DeviceConfigVO deviceConfigVO = new DeviceConfigVO();
            deviceConfigVO.setProviderId(commonVO.getProviderId());
            deviceConfigVO.setStId(commonVO.getStationId());
            deviceConfigVO.setChgrId(commonVO.getChgrId());

            commonVO.setDeviceConfigVO(csb.beanChgrInfoService().deviceConfigSelect(deviceConfigVO));

            logger.info("<----------------------- deviceConfigSelect -------------------------> : " + commonVO.getDeviceConfigVO());

            for (int i = 0; i < fwVerInfoVOList.size(); i++) {

                chgrInfoVO.setFwType(fwVerInfoVOList.get(i).getFwType());
                chgrInfoVO.setCurrVer(fwVerInfoVOList.get(i).getCurrVer());
                logger.info("<----------------------- 펌웨어 정보 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrFwVerInfoInsUpdate(chgrInfoVO));
            }

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //충전기 상태정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO chgrStatusParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

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

            logger.info("<----------- 충전기 상태정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("mode : " + mode);
            logger.info("integrated_power : " + integrated_power);
            logger.info("powerbox : " + powerbox);
            logger.info("chargerPlugList : " + chargerPlugList);
            logger.info("<------------------------------------------------>");

            // req Data DB Insert
            ChgrStatusVO chgrStatusVO = new ChgrStatusVO();
            chgrStatusVO.setProviderId(commonVO.getProviderId());
            chgrStatusVO.setStId(commonVO.getStationId());
            chgrStatusVO.setChgrId(commonVO.getChgrId());
            chgrStatusVO.setStateDt(create_date);
            chgrStatusVO.setMwKindCd("WS");
            chgrStatusVO.setRTimeYn("Y");
            chgrStatusVO.setOpModeCd(mode);
            chgrStatusVO.setPowerboxState(powerbox);
            chgrStatusVO.setIntegratedKwh(integrated_power);
            chgrStatusVO.setChgrTxDt(send_date);

            for (int i = 0; i < chargerPlugList.size(); i++) {

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

            CollectServiceBean csb = new CollectServiceBean();
            logger.info("<----------------------- Insert OK -------------------------> : " + csb.chgrStateService().chgrStateInsert(chgrStatusVO));
            logger.info("<----------------------- Update OK -------------------------> : " + csb.chgrStateService().chgrCurrStateUpdate(chgrStatusVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //사용자 인증요청(충전기 -> 충전기정보시스템) CALL
    private CommonVO userParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String card_num = (String) data.get("card_num");
            int plug_id = Integer.parseInt((String) data.get("plug_id"));

            logger.info("<----------- 사용자 인증요청 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("card_num : " + card_num);
            logger.info("plug_id : " + plug_id);
            logger.info("<------------------------------------------------>");

            UserVO userVO = new UserVO();
            userVO.setMemAuthInputNo(card_num);

            CollectServiceBean csb = new CollectServiceBean();
            UserVO resultUserVO = csb.userService().userAuthSelect(userVO);

            logger.info("<----------------------- 사용자 인증 OK -------------------------> : " + resultUserVO);

            resultUserVO.setProviderId(commonVO.getProviderId());
            resultUserVO.setStId(commonVO.getStationId());
            resultUserVO.setChgrId(commonVO.getChgrId());
            resultUserVO.setMwKindCd("WS");
            resultUserVO.setMemAuthReqDt(create_date);
            resultUserVO.setResDt(commonVO.getResponseDate());
            resultUserVO.setAuthRsltValid(resultUserVO.getAuthResult());
            resultUserVO.setMemProviderId(resultUserVO.getProviderId());
            resultUserVO.setPrice(resultUserVO.getCurrentUnitCost());
            resultUserVO.setChgrTxDt(send_date);
            resultUserVO.setChId(plug_id);
            logger.info("<----------------------- 회원 인증 이력 등록 OK -------------------------> : " + csb.userService().userAuthListInert(resultUserVO));

            commonVO.setUserVO(resultUserVO);
            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //충전 시작정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO chargingStartParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

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

            logger.info("<----------- 충전 시작정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("start_date : " + start_date);
            logger.info("card_num : " + card_num);
            logger.info("credit_trx_no : " + credit_trx_no);
            logger.info("credit_trx_date : " + credit_trx_date);
            logger.info("pay_type : " + pay_type);
            logger.info("charge_plug_type : " + charge_plug_type);
            logger.info("plug_id : " + plug_id);
            logger.info("integrated_power : " + integrated_power);
            logger.info("prepayment : " + prepayment);
            logger.info("current_V : " + current_V);
            logger.info("current_A : " + current_A);
            logger.info("estimated_charge_time : " + estimated_charge_time);
            logger.info("<------------------------------------------------>");

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId(commonVO.getProviderId());
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

            CollectServiceBean csb = new CollectServiceBean();
            logger.info("<----------------------- 충전 시작 정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgSttInsert(chargeVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //충전 진행정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO chargingInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

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
            int charge_W = Integer.parseInt((String) data.get("charge_W"));
            int charge_cost = Integer.parseInt((String) data.get("charge_cost"));

            logger.info("<----------- 충전 진행정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("start_date : " + start_date);
            logger.info("card_num : " + card_num);
            logger.info("credit_trx_no : " + credit_trx_no);
            logger.info("credit_trx_date : " + credit_trx_date);
            logger.info("pay_type : " + pay_type);
            logger.info("charge_plug_type : " + charge_plug_type);
            logger.info("plug_id : " + plug_id);
            logger.info("integrated_power : " + integrated_power);
            logger.info("prepayment : " + prepayment);
            logger.info("current_V : " + current_V);
            logger.info("current_A : " + current_A);
            logger.info("estimated_charge_time : " + estimated_charge_time);
            logger.info("charge_W : " + charge_W);
            logger.info("charge_cost : " + charge_cost);
            logger.info("<------------------------------------------------>");

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId(commonVO.getProviderId());
            chargeVO.setStId(commonVO.getStationId());
            chargeVO.setChgrId(commonVO.getChgrId());
            chargeVO.setMwKindCd("WS");
            chargeVO.setRTimeYn("Y");
            chargeVO.setChId(plug_id);
            chargeVO.setPlugTypeCd(charge_plug_type);
            chargeVO.setMemAuthInputNo(card_num);
            chargeVO.setRechgSdt(start_date);
            chargeVO.setRechgDemandAmt(prepayment);
            chargeVO.setRechgingAmt(charge_cost);
            chargeVO.setRechgingWh(charge_W);
            chargeVO.setRechgRemainTime(estimated_charge_time);
            chargeVO.setPayTypeCd(pay_type);
            chargeVO.setCreditPPayTrxNo(credit_trx_no);
            chargeVO.setCreditPPayTrxDt(credit_trx_date);
            chargeVO.setIntegratedWh(integrated_power);
            chargeVO.setCurrVolt(current_V);
            chargeVO.setCurrC(current_A);
            chargeVO.setDataCreateDt(create_date);
            chargeVO.setChgrTxDt(send_date);

            CollectServiceBean csb = new CollectServiceBean();
            logger.info("<----------------------- 충전 진행정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgingInsert(chargeVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //충전 완료정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO chargingEndParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

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
            String charge_time = (String) data.get("charge_time");
            String charge_end_type = (String) data.get("charge_end_type");
            String end_date = (String) data.get("end_date");
            int cancel_cost = Integer.parseInt((String) data.get("cancel_cost"));
            String cancel_detl = (String) data.get("cancel_detl");
            int charge_W = Integer.parseInt((String) data.get("charge_W"));
            int charge_cost = Integer.parseInt((String) data.get("charge_cost"));

            String pay_fnsh_yn = "";

            if (data.get("pay_fnsh_yn") != null && !(data.get("pay_fnsh_yn").equals(""))) {
                pay_fnsh_yn = (String) data.get("pay_fnsh_yn");
            }

            logger.info("<----------- 충전 완료정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("start_date : " + start_date);
            logger.info("card_num : " + card_num);
            logger.info("credit_trx_no : " + credit_trx_no);
            logger.info("credit_trx_date : " + credit_trx_date);
            logger.info("pay_type : " + pay_type);

            logger.info("charge_plug_type : " + charge_plug_type);
            logger.info("plug_id : " + plug_id);
            logger.info("integrated_power : " + integrated_power);
            logger.info("prepayment : " + prepayment);
            logger.info("current_V : " + current_V);
            logger.info("current_A : " + current_A);
            logger.info("charge_time : " + charge_time);
            logger.info("charge_W : " + charge_W);
            logger.info("charge_end_type : " + charge_end_type);
            logger.info("end_date : " + end_date);
            logger.info("charge_cost : " + charge_cost);
            logger.info("cancel_cost : " + cancel_cost);
            logger.info("pay_fnsh_yn : " + pay_fnsh_yn);
            logger.info("<------------------------------------------------>");

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId(commonVO.getProviderId());
            chargeVO.setStId(commonVO.getStationId());
            chargeVO.setChgrId(commonVO.getChgrId());
            chargeVO.setMwKindCd("WS");
            chargeVO.setRTimeYn("Y");
            chargeVO.setChId(plug_id);
            chargeVO.setPlugTypeCd(charge_plug_type);
            chargeVO.setMemAuthInputNo(card_num);
            chargeVO.setRechgSdt(start_date);
            chargeVO.setRechgEdt(end_date);
            chargeVO.setRechgFnshTpCd(charge_end_type);
            chargeVO.setRechgDemandAmt(prepayment);
            chargeVO.setRechgAmt(charge_cost);
            chargeVO.setRechgWh(charge_W);
            chargeVO.setRechgTime(charge_time);
            chargeVO.setPayTypeCd(pay_type);
            chargeVO.setCreditPPayTrxNo(credit_trx_no);
            chargeVO.setCreditPPayTrxDt(credit_trx_date);
            chargeVO.setCancelAmt(cancel_cost);
            chargeVO.setCancelDetl(cancel_detl);
            chargeVO.setIntegratedWh(integrated_power);
            chargeVO.setCurrVolt(current_V);
            chargeVO.setCurrC(current_A);
            chargeVO.setDataCreateDt(create_date);
            chargeVO.setChgrTxDt(send_date);
            chargeVO.setPayFnshYn(pay_fnsh_yn);

            CollectServiceBean csb = new CollectServiceBean();
            logger.info("<----------------------- 충전 완료정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgFnshInsert(chargeVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //충전 결제정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO chargePaymentParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String prepayment_trx_no = (String) data.get("prepayment_trx_no");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String payment_type = (String) data.get("payment_type");
            String charge_plug_type = (String) data.get("charge_plug_type");
            int plug_id = Integer.parseInt((String) data.get("plug_id"));
            int payment_amount = Integer.parseInt((String) data.get("payment_amount"));

            logger.info("<----------- 충전 결제정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("prepayment_trx_no : " + prepayment_trx_no);
            logger.info("credit_trx_no : " + credit_trx_no);
            logger.info("credit_trx_date : " + credit_trx_date);
            logger.info("payment_type : " + payment_type);
            logger.info("charge_plug_type : " + charge_plug_type);
            logger.info("plug_id : " + plug_id);
            logger.info("payment_amount : " + payment_amount);
            logger.info("<------------------------------------------------>");

            ChargePaymentVO chargePaymentVO = new ChargePaymentVO();
            chargePaymentVO.setProviderId(commonVO.getProviderId());
            chargePaymentVO.setStId(commonVO.getStationId());
            chargePaymentVO.setChgrId(commonVO.getChgrId());
            chargePaymentVO.setChId(plug_id);
            chargePaymentVO.setPPayTrxNo(prepayment_trx_no);

            CollectServiceBean csb = new CollectServiceBean();

            if (payment_type.equals("0")) {

                chargePaymentVO.setRPayTrxNo(credit_trx_no);
                chargePaymentVO.setRPayTrxDt(credit_trx_date.equals("") ? null : credit_trx_date);
                chargePaymentVO.setRPayAmt(payment_amount);
                chargePaymentVO.setRPayTxDt(create_date);

                logger.info("<----------------------- 충전 결제정보 - 실충전결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoInsert(chargePaymentVO));
            } else if (payment_type.equals("1")) {

                chargePaymentVO.setCPayTrxNo(credit_trx_no);
                chargePaymentVO.setCPayTrxDt(credit_trx_date.equals("") ? null : credit_trx_date);
                chargePaymentVO.setCPayAmt(payment_amount);
                chargePaymentVO.setCPayTxDt(create_date);

                logger.info("<----------------------- 충전 결제정보 - 취소결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoUpdate(chargePaymentVO));
            }

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //문자 전송(충전기 -> 충전기정보시스템) CALL
    private CommonVO sendSmsParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            int plug_id = Integer.parseInt((String) data.get("plug_id"));
            int charge_cost = Integer.parseInt((String) data.get("charge_cost"));
            String charge_time = (String) data.get("charge_time");
            int charge_W = Integer.parseInt((String) data.get("charge_W"));
            String msg_kind = (String) data.get("msg_kind");
            String phone_no = (String) data.get("phone_no");

            logger.info("<----------- 문자 전송 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("plug_id : " + plug_id);
            logger.info("charge_cost : " + charge_cost);
            logger.info("charge_time : " + charge_time);
            logger.info("charge_W : " + charge_W);
            logger.info("msg_kind : " + msg_kind);
            logger.info("phone_no : " + phone_no);
            logger.info("<------------------------------------------------>");

            SendSmsVO sendSmsVO = new SendSmsVO();
            sendSmsVO.setProviderId(commonVO.getProviderId());
            sendSmsVO.setStId(commonVO.getStationId());
            sendSmsVO.setChgrId(commonVO.getChgrId());
            sendSmsVO.setChId(plug_id);
            sendSmsVO.setMsgKindCd(msg_kind);
            sendSmsVO.setTel(phone_no);
            sendSmsVO.setRechgTime(charge_time);
            sendSmsVO.setRechgWh(charge_W);
            sendSmsVO.setRechgAmt(charge_cost);
            sendSmsVO.setDataCreateDt(create_date);
            sendSmsVO.setChgrTxDt(send_date);

            CollectServiceBean csb = new CollectServiceBean();

            logger.info("<----------------------- 문자 전송 이력 등록 OK -------------------------> : " + csb.sendSmsService().msgSndListInsert(sendSmsVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //경보 이력(충전기 -> 충전기정보시스템) CALL
    private CommonVO alarmHistoryParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            String alarm_plug = (String) data.get("alarm_plug");
            String alarm_type = (String) data.get("alarm_type");
            String alarm_date = (String) data.get("alarm_date");
            String alarm_code = (String) data.get("alarm_code");

            //20-02-06 추가 - 제조사별 경보코드 사용하지 않는 경우 공란으로 설정
            String mf_alarm_code = "";

            if (data.get("mf_alarm_code") != null && !(data.get("mf_alarm_code").equals(""))) {
                mf_alarm_code = (String) data.get("mf_alarm_code");
            }

            logger.info("<----------- 경보 이력 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("alarm_plug : " + alarm_plug);
            logger.info("alarm_type : " + alarm_type);
            logger.info("alarm_date : " + alarm_date);
            logger.info("alarm_code : " + alarm_code);
            logger.info("mf_alarm_code : " + mf_alarm_code);
            logger.info("<------------------------------------------------>");

            AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

            alarmHistoryVO.setProviderId(commonVO.getProviderId());
            alarmHistoryVO.setStId(commonVO.getStationId());
            alarmHistoryVO.setChgrId(commonVO.getChgrId());
            alarmHistoryVO.setChId(alarm_plug);
            alarmHistoryVO.setAlarmStateCd(alarm_type);
            alarmHistoryVO.setOccurDt(alarm_date);
            alarmHistoryVO.setAlarmCd(alarm_code);
            alarmHistoryVO.setChgrTxDt(send_date);
            alarmHistoryVO.setMwKindCd("WS");
            alarmHistoryVO.setRTimeYn("Y");
            alarmHistoryVO.setMfAlarmCd(mf_alarm_code);

            CollectServiceBean csb = new CollectServiceBean();

            // req Data DB Insert
            logger.info("<----------------------- Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
    private CommonVO reportUpdateParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

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

            logger.info("<----------- 펌웨어 업데이트 결과 알림 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("update_result : " + update_result);
            logger.info("fwVerInfoVOList : " + fwVerInfoVOList);
            logger.info("<------------------------------------------------>");

            ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
            chgrInfoVO.setProviderId(commonVO.getProviderId());
            chgrInfoVO.setStId(commonVO.getStationId());
            chgrInfoVO.setChgrId(commonVO.getChgrId());

            CollectServiceBean csb = new CollectServiceBean();

            // req Data DB Insert
            for (int i = 0; i < fwVerInfoVOList.size(); i++) {

                chgrInfoVO.setFwType(fwVerInfoVOList.get(i).getFwType());
                chgrInfoVO.setCurrVer(fwVerInfoVOList.get(i).getCurrVer());
                logger.info("<----------------------- 펌웨어 업데이트 결과 알림 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrFwVerInfoInsUpdate(chgrInfoVO));
            }

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);




        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //신용승인 결제정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO paymentInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String send_date = (String) data.get("send_date");
            String create_date = (String) data.get("create_date");
            int plug_id = Integer.parseInt((String) data.get("plug_id"));
            String charge_plug_type = (String) data.get("charge_plug_type");
            String credit_trx_no = (String) data.get("credit_trx_no");
            String credit_trx_date = (String) data.get("credit_trx_date");
            String payment_type = (String) data.get("payment_type");
            int payment = Integer.parseInt((String) data.get("payment"));
            int cancel_payment = Integer.parseInt((String) data.get("cancel_payment"));
            String payment_detl = (String) data.get("payment_detl");
            String pay_fnsh_yn = (String) data.get("pay_fnsh_yn");

            logger.info("<----------- 신용승인 결제정보 Parsing Data ----------->");
            logger.info("send_date : " + send_date);
            logger.info("create_date : " + create_date);
            logger.info("plug_id : " + plug_id);
            logger.info("charge_plug_type : " + charge_plug_type);
            logger.info("credit_trx_no : " + credit_trx_no);
            logger.info("credit_trx_date : " + credit_trx_date);
            logger.info("payment_type : " + payment_type);
            logger.info("payment : " + payment);
            logger.info("cancel_payment : " + cancel_payment);
            logger.info("payment_detl : " + payment_detl);
            logger.info("pay_fnsh_yn : " + pay_fnsh_yn);
            logger.info("<------------------------------------------------>");

            PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
            paymentInfoVO.setProviderId(commonVO.getProviderId());
            paymentInfoVO.setStId(commonVO.getStationId());
            paymentInfoVO.setChgrId(commonVO.getChgrId());
            paymentInfoVO.setChId(plug_id);
            paymentInfoVO.setPlugTypeCd(charge_plug_type);
            paymentInfoVO.setCreditTrxNo(credit_trx_no);
            paymentInfoVO.setCreditTrxDt(credit_trx_date);

            CollectServiceBean csb = new CollectServiceBean();

            //선결제
            if (payment_type.equals("1")) {
                paymentInfoVO.setPayAmt(payment);
                paymentInfoVO.setCreditPayDetl(payment_detl);
                paymentInfoVO.setPPayFnshYn(pay_fnsh_yn);
                paymentInfoVO.setPPayChgrTxDt(send_date);

                logger.info("<----------------------- 신용승인 결제정보 등록 OK -------------------------> : " + csb.chargePaymentService().paymentInfoInsert(paymentInfoVO));
            }

            //무충전 카드취소결제 or 부분취소결제
            else if (payment_type.equals("2") || payment_type.equals("3")) {

                paymentInfoVO.setCPayType(payment_type);
                paymentInfoVO.setCPayAmt(cancel_payment);
                paymentInfoVO.setCPayFnshYn(pay_fnsh_yn);
                paymentInfoVO.setCPayChgrTxDt(send_date);

                logger.info("<----------------------- 신용승인 결제정보 수정 OK -------------------------> : " + csb.chargePaymentService().paymentInfoUpdate(paymentInfoVO));
            }

            PaymentListVO paymentListVO = new PaymentListVO();
            paymentListVO.setProviderId(commonVO.getProviderId());
            paymentListVO.setStId(commonVO.getStationId());
            paymentListVO.setChgrId(commonVO.getChgrId());
            paymentListVO.setChId(plug_id);
            paymentListVO.setPlugTypeCd(charge_plug_type);
            paymentListVO.setRTimeYn("Y");
            paymentListVO.setCreditTrxNo(credit_trx_no);
            paymentListVO.setCreditTrxDt(credit_trx_date);
            paymentListVO.setPayType(payment_type);
            paymentListVO.setPayAmt(payment);
            paymentListVO.setCPayAmt(cancel_payment);
            paymentListVO.setPayDetl(payment_detl);
            paymentListVO.setPayFnshYn(pay_fnsh_yn);
            paymentListVO.setChgrTxDt(send_date);

            logger.info("<----------------------- 신용승인 결제 리스트 등록 OK -------------------------> : " + csb.chargePaymentService().paymentListInsert(paymentListVO));

            commonVO.setSendDate(send_date);
            commonVO.setCreateDate(create_date);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO dChargingStartParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId(commonVO.getProviderId());
            chargeVO.setStId(commonVO.getStationId());
            chargeVO.setChgrId(commonVO.getChgrId());
            chargeVO.setMwKindCd("WS");
            chargeVO.setRTimeYn("N");

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String start_date = (String) tmp.get("start_date");
                String card_num = (String) tmp.get("card_num");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String pay_type = (String) tmp.get("pay_type");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                int plug_id = Integer.parseInt((String) tmp.get("plug_id"));
                int integrated_power = Integer.parseInt((String) tmp.get("integrated_power"));
                int prepayment = Integer.parseInt((String) tmp.get("prepayment"));
                String current_V = (String) tmp.get("current_V");
                String current_A = (String) tmp.get("current_A");
                String estimated_charge_time = (String) tmp.get("estimated_charge_time");

                logger.info("<----------- 덤프_충전 시작정보 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("start_date : " + start_date);
                logger.info("card_num : " + card_num);
                logger.info("credit_trx_no : " + credit_trx_no);
                logger.info("credit_trx_date : " + credit_trx_date);
                logger.info("pay_type : " + pay_type);
                logger.info("charge_plug_type : " + charge_plug_type);
                logger.info("plug_id : " + plug_id);
                logger.info("integrated_power : " + integrated_power);
                logger.info("prepayment : " + prepayment);
                logger.info("current_V : " + current_V);
                logger.info("current_A : " + current_A);
                logger.info("estimated_charge_time : " + estimated_charge_time);
                logger.info("<------------------------------------------------>");

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

                CollectServiceBean csb = new CollectServiceBean();

                logger.info("<----------------------- 덤프_충전 시작정보 이력 등록 OK [" + (i + 1) + "] -------------------------> : " + csb.chargeService().rechgSttInsert(chargeVO));

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);



            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO dChargingEndParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            ChargeVO chargeVO = new ChargeVO();
            chargeVO.setProviderId(commonVO.getProviderId());
            chargeVO.setStId(commonVO.getStationId());
            chargeVO.setChgrId(commonVO.getChgrId());
            chargeVO.setMwKindCd("WS");
            chargeVO.setRTimeYn("N");

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String start_date = (String) tmp.get("start_date");
                String card_num = (String) tmp.get("card_num");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String pay_type = (String) tmp.get("pay_type");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                int plug_id = Integer.parseInt((String) tmp.get("plug_id"));
                int integrated_power = Integer.parseInt((String) tmp.get("integrated_power"));
                int prepayment = Integer.parseInt((String) tmp.get("prepayment"));
                String current_V = (String) tmp.get("current_V");
                String current_A = (String) tmp.get("current_A");
                String charge_time = (String) tmp.get("charge_time");
                String charge_end_type = (String) tmp.get("charge_end_type");
                String end_date = (String) tmp.get("end_date");
                int cancel_cost = Integer.parseInt((String) tmp.get("cancel_cost"));
                String cancel_detl = (String) tmp.get("cancel_detl");
                int charge_W = Integer.parseInt((String) tmp.get("charge_W"));
                int charge_cost = Integer.parseInt((String) tmp.get("charge_cost"));

                logger.info("<----------- 덤프_충전 완료정보 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("start_date : " + start_date);
                logger.info("card_num : " + card_num);
                logger.info("credit_trx_no : " + credit_trx_no);
                logger.info("credit_trx_date : " + credit_trx_date);
                logger.info("pay_type : " + pay_type);
                logger.info("charge_plug_type : " + charge_plug_type);
                logger.info("plug_id : " + plug_id);
                logger.info("integrated_power : " + integrated_power);
                logger.info("prepayment : " + prepayment);
                logger.info("current_V : " + current_V);
                logger.info("current_A : " + current_A);
                logger.info("charge_time : " + charge_time);
                logger.info("charge_W : " + charge_W);
                logger.info("charge_end_type : " + charge_end_type);
                logger.info("end_date : " + end_date);
                logger.info("charge_cost : " + charge_cost);
                logger.info("cancel_cost : " + cancel_cost);
                logger.info("cancel_detl : " + cancel_detl);
                logger.info("<------------------------------------------------>");

                chargeVO.setChId(plug_id);
                chargeVO.setPlugTypeCd(charge_plug_type);
                chargeVO.setMemAuthInputNo(card_num);
                chargeVO.setRechgSdt(start_date);
                chargeVO.setRechgEdt(end_date);
                chargeVO.setRechgFnshTpCd(charge_end_type);
                chargeVO.setRechgDemandAmt(prepayment);
                chargeVO.setRechgAmt(charge_cost);
                chargeVO.setRechgWh(charge_W);
                chargeVO.setRechgTime(charge_time);
                chargeVO.setPayTypeCd(pay_type);
                chargeVO.setCreditPPayTrxNo(credit_trx_no);
                chargeVO.setCreditPPayTrxDt(credit_trx_date);
                chargeVO.setCancelAmt(cancel_cost);
                chargeVO.setCancelDetl(cancel_detl);
                chargeVO.setIntegratedWh(integrated_power);
                chargeVO.setCurrVolt(current_V);
                chargeVO.setCurrC(current_A);
                chargeVO.setDataCreateDt(create_date);
                chargeVO.setChgrTxDt(send_date);

                CollectServiceBean csb = new CollectServiceBean();

                logger.info("<----------------------- 덤프_충전 완료정보 이력 등록 OK -------------------------> : " + csb.chargeService().rechgFnshInsert(chargeVO));

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);



            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO dChargePaymentParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            ChargePaymentVO chargePaymentVO = new ChargePaymentVO();
            chargePaymentVO.setProviderId(commonVO.getProviderId());
            chargePaymentVO.setStId(commonVO.getStationId());
            chargePaymentVO.setChgrId(commonVO.getChgrId());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String prepayment_trx_no = (String) tmp.get("prepayment_trx_no");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String payment_type = (String) tmp.get("payment_type");
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                int plug_id = Integer.parseInt((String) tmp.get("plug_id"));
                int payment_amount = Integer.parseInt((String) tmp.get("payment_amount"));

                logger.info("<----------- 덤프_충전 결제정보 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("prepayment_trx_no : " + prepayment_trx_no);
                logger.info("credit_trx_no : " + credit_trx_no);
                logger.info("credit_trx_date : " + credit_trx_date);
                logger.info("payment_type : " + payment_type);
                logger.info("charge_plug_type : " + charge_plug_type);
                logger.info("plug_id : " + plug_id);
                logger.info("payment_amount : " + payment_amount);
                logger.info("<------------------------------------------------>");

                chargePaymentVO.setChId(plug_id);
                chargePaymentVO.setPPayTrxNo(prepayment_trx_no);

                CollectServiceBean csb = new CollectServiceBean();

                if (payment_type.equals("0")) {

                    chargePaymentVO.setRPayTrxNo(credit_trx_no);
                    chargePaymentVO.setRPayTrxDt(credit_trx_date.equals("") ? null : credit_trx_date);
                    chargePaymentVO.setRPayAmt(payment_amount);
                    chargePaymentVO.setRPayTxDt(create_date);

                    logger.info("<----------------------- 덤프_충전 결제정보 - 실충전결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoInsert(chargePaymentVO));
                } else if (payment_type.equals("1")) {

                    chargePaymentVO.setCPayTrxNo(credit_trx_no);
                    chargePaymentVO.setCPayTrxDt(credit_trx_date.equals("") ? null : credit_trx_date);
                    chargePaymentVO.setCPayAmt(payment_amount);
                    chargePaymentVO.setCPayTxDt(create_date);

                    logger.info("<----------------------- 덤프_충전 결제정보 - 취소결제 이력 등록 OK -------------------------> : " + csb.chargePaymentService().creditTrxInfoUpdate(chargePaymentVO));
                }

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);



            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_경보 이력(충전기 -> 충전기정보시스템) CALL
    private CommonVO dAlarmHistoryParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            AlarmHistoryVO alarmHistoryVO = new AlarmHistoryVO();

            alarmHistoryVO.setProviderId(commonVO.getProviderId());
            alarmHistoryVO.setStId(commonVO.getStationId());
            alarmHistoryVO.setChgrId(commonVO.getChgrId());
            alarmHistoryVO.setMwKindCd("WS");
            alarmHistoryVO.setRTimeYn("N");

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                String alarm_plug = (String) tmp.get("alarm_plug");
                String alarm_type = (String) tmp.get("alarm_type");
                String alarm_date = (String) tmp.get("alarm_date");
                String alarm_code = (String) tmp.get("alarm_code");

                //20-02-06 추가 - 제조사별 경보코드 사용하지 않는 경우 공란으로 설정
                String mf_alarm_code = "";

                if (tmp.get("mf_alarm_code") != null && !(tmp.get("mf_alarm_code").equals(""))) {
                    mf_alarm_code = (String) tmp.get("mf_alarm_code");
                }

                logger.info("<----------- 덤프_경보 이력 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("alarm_plug : " + alarm_plug);
                logger.info("alarm_type : " + alarm_type);
                logger.info("alarm_date : " + alarm_date);
                logger.info("alarm_code : " + alarm_code);
                logger.info("mf_alarm_code : " + mf_alarm_code);
                logger.info("<------------------------------------------------>");

                alarmHistoryVO.setChId(alarm_plug);
                alarmHistoryVO.setAlarmStateCd(alarm_type);
                alarmHistoryVO.setOccurDt(alarm_date);
                alarmHistoryVO.setAlarmCd(alarm_code);
                alarmHistoryVO.setChgrTxDt(send_date);
                alarmHistoryVO.setMfAlarmCd(mf_alarm_code);

                CollectServiceBean csb = new CollectServiceBean();

                // req Data DB Insert
                logger.info("<----------------------- 덤프_경보 이력 Insert OK -------------------------> : " + csb.alarmHistoryService().alarmHistoryInsert(alarmHistoryVO));

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
    private CommonVO dReportUpdateParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            for (int i = 0; i < data.size(); i++) {
                JSONObject obj = (JSONObject) data.get(i);

                String send_date = (String) obj.get("send_date");
                String create_date = (String) obj.get("create_date");
                String update_result = (String) obj.get("update_result");

                ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

                JSONArray fwVerInfoArr = (JSONArray) obj.get("fw_ver_info");

                for (int j = 0; j < fwVerInfoArr.size(); j++) {
                    JSONObject tmp = (JSONObject) fwVerInfoArr.get(j);

                    String curr_ver = (String) tmp.get("curr_ver");
                    String fw_type = (String) tmp.get("fw_type");

                    FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
                    fwVerInfoVO.setCurrVer(curr_ver);
                    fwVerInfoVO.setFwType(fw_type);
                    fwVerInfoVOList.add(fwVerInfoVO);
                }

                logger.info("<----------- 덤프_펌웨어 업데이트 결과 알림 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("update_result : " + update_result);
                logger.info("fwVerInfoVOList : " + fwVerInfoVOList);
                logger.info("<------------------------------------------------>");

                ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
                chgrInfoVO.setProviderId(commonVO.getProviderId());
                chgrInfoVO.setStId(commonVO.getStationId());
                chgrInfoVO.setChgrId(commonVO.getChgrId());

                // req Data DB Insert
                for (int j = 0; j < fwVerInfoVOList.size(); j++) {

                    chgrInfoVO.setFwType(fwVerInfoVOList.get(j).getFwType());
                    chgrInfoVO.setCurrVer(fwVerInfoVOList.get(j).getCurrVer());

                    CollectServiceBean csb = new CollectServiceBean();

                    logger.info("<----------------------- 덤프_펌웨어 업데이트 결과 알림 Update OK -------------------------> : " + csb.beanChgrInfoService().chgrFwVerInfoInsUpdate(chgrInfoVO));
                }

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);



            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //덤프_신용승인 결제정보(충전기 -> 충전기정보시스템) CALL
    private CommonVO dPaymentInfoParsingData(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONArray data = (JSONArray) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
            paymentInfoVO.setProviderId(commonVO.getProviderId());
            paymentInfoVO.setStId(commonVO.getStationId());
            paymentInfoVO.setChgrId(commonVO.getChgrId());

            PaymentListVO paymentListVO = new PaymentListVO();
            paymentListVO.setProviderId(commonVO.getProviderId());
            paymentListVO.setStId(commonVO.getStationId());
            paymentListVO.setChgrId(commonVO.getChgrId());

            for (int i = 0; i < data.size(); i++) {
                JSONObject tmp = (JSONObject) data.get(i);

                String send_date = (String) tmp.get("send_date");
                String create_date = (String) tmp.get("create_date");
                int plug_id = Integer.parseInt((String) tmp.get("plug_id"));
                String charge_plug_type = (String) tmp.get("charge_plug_type");
                String credit_trx_no = (String) tmp.get("credit_trx_no");
                String credit_trx_date = (String) tmp.get("credit_trx_date");
                String payment_type = (String) tmp.get("payment_type");
                int payment = Integer.parseInt((String) tmp.get("payment"));
                int cancel_payment = Integer.parseInt((String) tmp.get("cancel_payment"));
                String payment_detl = (String) tmp.get("payment_detl");
                String pay_fnsh_yn = (String) tmp.get("pay_fnsh_yn");


                logger.info("<----------- 신용승인 결제정보 Parsing Data ----------->");
                logger.info("send_date : " + send_date);
                logger.info("create_date : " + create_date);
                logger.info("plug_id : " + plug_id);
                logger.info("charge_plug_type : " + charge_plug_type);
                logger.info("credit_trx_no : " + credit_trx_no);
                logger.info("credit_trx_date : " + credit_trx_date);
                logger.info("payment_type : " + payment_type);
                logger.info("payment : " + payment);
                logger.info("cancel_payment : " + cancel_payment);
                logger.info("payment_detl : " + payment_detl);
                logger.info("pay_fnsh_yn : " + pay_fnsh_yn);
                logger.info("<------------------------------------------------>");


                paymentInfoVO.setChId(plug_id);
                paymentInfoVO.setPlugTypeCd(charge_plug_type);
                paymentInfoVO.setCreditTrxNo(credit_trx_no);
                paymentInfoVO.setCreditTrxDt(credit_trx_date);

                CollectServiceBean csb = new CollectServiceBean();

                //선결제
                if (payment_type.equals("1")) {
                    paymentInfoVO.setPayAmt(payment);
                    paymentInfoVO.setCreditPayDetl(payment_detl);
                    paymentInfoVO.setPPayFnshYn(pay_fnsh_yn);
                    paymentInfoVO.setPPayChgrTxDt(send_date);

                    logger.info("<----------------------- 신용승인 결제정보 등록 OK -------------------------> : " + csb.chargePaymentService().paymentInfoInsert(paymentInfoVO));
                }

                //무충전 카드취소결제 or 부분취소결제
                else if (payment_type.equals("2") || payment_type.equals("3")) {

                    paymentInfoVO.setCPayType(payment_type);
                    paymentInfoVO.setCPayAmt(cancel_payment);
                    paymentInfoVO.setCPayFnshYn(pay_fnsh_yn);
                    paymentInfoVO.setCPayChgrTxDt(send_date);

                    logger.info("<----------------------- 신용승인 결제정보 수정 OK -------------------------> : " + csb.chargePaymentService().paymentInfoUpdate(paymentInfoVO));
                }

                paymentListVO.setChId(plug_id);
                paymentListVO.setPlugTypeCd(charge_plug_type);
                paymentListVO.setRTimeYn(commonVO.getRTimeYn());
                paymentListVO.setCreditTrxNo(credit_trx_no);
                paymentListVO.setCreditTrxDt(credit_trx_date);
                paymentListVO.setPayType(payment_type);
                paymentListVO.setPayAmt(payment);
                paymentListVO.setCPayAmt(cancel_payment);
                paymentListVO.setPayDetl(payment_detl);
                paymentListVO.setPayFnshYn(pay_fnsh_yn);
                paymentListVO.setChgrTxDt(send_date);

                logger.info("<----------------------- 신용승인 결제 리스트 등록 OK -------------------------> : " + csb.chargePaymentService().paymentListInsert(paymentListVO));

                commonVO.setSendDate(send_date);
                commonVO.setCreateDate(create_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
            recvMsg(commonVO);
        }

        return commonVO;
    }

    //reset
    private CommonVO controlResponse(CommonVO commonVO) {

        try {
            JSONParser jParser = new JSONParser();
            JSONObject data = (JSONObject) jParser.parse(commonVO.getData());

            logger.info("data : " + data.toString());

            String station_id = (String) data.get("station_id");
            String chgr_id = (String) data.get("chgr_id");
            String response_date = (String) data.get("response_date");
            String response_receive = (String) data.get("response_receive");
            String response_reason = (String) data.get("response_reason");

            String before_mode = "";
            String after_mode = "";

            String fw_type = "";
            String curr_ver = "";

            String logText = "";

            commonVO.setResponseDate(response_date);

            if (commonVO.getActionType().equals("reset")) {
                logText = "리셋";
            } else if (commonVO.getActionType().equals("prices")) {
                logText = "단가";
            } else if (commonVO.getActionType().equals("changeMode")) {
                logText = "모드 변경";

                before_mode = (String) data.get("before_mode");
                after_mode = (String) data.get("after_mode");

            } else if (commonVO.getActionType().equals("displayBrightness")) {
                logText = "화면 밝기 정보";
            } else if (commonVO.getActionType().equals("sound")) {
                logText = "소리 정보";
            } else if (commonVO.getActionType().equals("askVer")) {
                logText = "펌웨어 버전 정보";

                fw_type = (String) data.get("fw_type");
                curr_ver = (String) data.get("curr_ver");
            }

            logger.info("<----------- [제어 응답] 충전기 -> M/W " + logText + " Parsing Data ----------->");
            logger.info("providerId : " + commonVO.getProviderId());
            logger.info("stationId : " + commonVO.getStationId());
            logger.info("chgrId : " + commonVO.getChgrId());
            logger.info("uuid : " + commonVO.getUuid());
            logger.info("send_type : " + commonVO.getSendType());
            logger.info("action_type : " + commonVO.getActionType());
            logger.info("station_id : " + station_id);
            logger.info("chgr_id : " + chgr_id);
            logger.info("response_date : " + response_date);
            logger.info("response_receive : " + response_receive);
            logger.info("response_reason : " + response_reason);
            logger.info("before_mode : " + before_mode);
            logger.info("after_mode : " + after_mode);
            logger.info("fw_type : " + fw_type);
            logger.info("curr_ver : " + curr_ver);
            logger.info("<------------------------------------------------>");

            //전문 이력 업데이트
            commonVO = txMsgListUpdate(commonVO);

        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("<----------------------- 파싱 오류 ------------------------->");
            commonVO = parameterErrorMsgSend(commonVO);

            //전문 이력 업데이트
            commonVO = txMsgListUpdate(commonVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("<----------------------- DB Insert 오류 ------------------------->");
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");

            //전문 이력 업데이트
            commonVO = txMsgListUpdate(commonVO);
        }

        return commonVO;
    }

    //수신 전문 이력 인서트&업데이트(충전기 <-> M/W)
    public CommonVO recvMsg(CommonVO commonVO) {

        try {

            RevMsgVO revMsgVO = new RevMsgVO();

            String providerId = commonVO.getProviderId();
            String stationId = commonVO.getStationId();

            revMsgVO.setProviderId(commonVO.getProviderId());
            revMsgVO.setStId(stationId.indexOf(providerId) != -1 ? stationId.replace(providerId, "") : stationId);

            revMsgVO.setChgrId(commonVO.getChgrId());
            revMsgVO.setMwKindCd("WS");
            revMsgVO.setCmdTp(commonVO.getActionType());
            revMsgVO.setRTimeYn(commonVO.getRTimeYn());
            revMsgVO.setDataCreateDt(commonVO.getCreateDate());
            revMsgVO.setChgrTxDt(commonVO.getSendDate());
            revMsgVO.setRecvMsg(commonVO.getRcvMsg());

            revMsgVO.setResDt(commonVO.getResponseDate());
            revMsgVO.setResCd(commonVO.getResponseReceive());
            revMsgVO.setResRsnCd(commonVO.getResponseReason());
            revMsgVO.setResMsg(commonVO.getResMsg());

            CollectServiceBean csb = new CollectServiceBean();

            logger.info("<----------------------- 수신 전문 이력 Insert OK -------------------------> : " + csb.revMsgService().recvMsgInsert(revMsgVO));

        } catch (Exception e) {
            e.printStackTrace();
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
        }

        return commonVO;
    }

    //전문 이력 업데이트(M/W <-> 충전기)
    public CommonVO txMsgListUpdate(CommonVO commonVO) {

        ControlChgrVO controlChgrVO = new ControlChgrVO();

        controlChgrVO.setUuid(commonVO.getUuid());
        controlChgrVO.setResDt(commonVO.getResponseDate());
        controlChgrVO.setResCd(commonVO.getResponseReceive());
        controlChgrVO.setResRsnCd(commonVO.getResponseReason());
        controlChgrVO.setResMsg(commonVO.getRcvMsg());

        try {
            CollectServiceBean csb = new CollectServiceBean();
            logger.info("<----------------------- 전문 이력 Update OK -------------------------> : " + csb.controlChgrService().txMsgListUpdate(controlChgrVO));
        } catch (Exception e) {
            e.printStackTrace();
            commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
        }

        return commonVO;
    }

    public void protocolErrorMsgSend(CommonVO commonVO) {

        logger.info("필수항목 누락으로 인한 전문 오류 입니다. 받은 uuid : " + commonVO.getUuid() + ", 받은 send_type : "
                + commonVO.getSendType() + ", 받은 action_type : " + commonVO.getActionType() + ", 받은 data : " + commonVO.getData());

        commonVO.setResponseReceive("0");
        commonVO.setResponseReason("11");
        sendMessage(commonVO);

        return;
    }

    //파라미터 오류
    public CommonVO parameterErrorMsgSend(CommonVO commonVO) {
        logger.info("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());

        commonVO.setResponseReceive("0");   //실패
        commonVO.setResponseReason("12");   //파라미터 오류

        return commonVO;
    }

    //충전소(기)ID 오류
    public void invalidErrorMsgSend(CommonVO commonVO) {
        logger.info("충전소(기)ID 중 오류 입니다. 받은 Data : " + commonVO.getData());

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

        logger.info("JSON Parsing 중 오류 입니다. 받은 Data : " + commonVO.getData());
        return;
    }

    public CommonVO unknownErrorMsgSend(CommonVO commonVO, String msg) {
        logger.info("ST_ID : " + commonVO.getStationId());
        logger.info("CHGR_ID : " + commonVO.getChgrId());
        logger.info("내부 오류 입니다. 받은 uuid : " + commonVO.getUuid() + ", 받은 send_type : "
                + commonVO.getSendType() + ", 받은 action_type : " + commonVO.getActionType() + ", 받은 data : " + commonVO.getData());
        logger.info("내부 오류 : " + msg);

        commonVO.setResponseReceive("0");
        commonVO.setResponseReason("15");

        return commonVO;
    }

    public int getByteLength(String str) {

        int strLength = 0;

        char tempChar[] = new char[str.length()];

        for (int i = 0; i < tempChar.length; i++) {

            tempChar[i] = str.charAt(i);

            if (tempChar[i] < 128) {

                strLength++;

            } else {

                strLength += 2;
            }
        }

        return strLength;

    }
}
