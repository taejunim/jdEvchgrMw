package jdEvchgrMw.websocket;

import jdEvchgrMw.vo.ChargerPlugVO;
import jdEvchgrMw.vo.FwVerInfoVO;
import jdEvchgrMw.vo.PlugDetlInfoVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonParser;

import jdEvchgrMw.chgrInfo.service.ChgrInfoVO;
import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.CommonVO;

import java.util.ArrayList;

/**
 * @ Class Name  : JsonDataParsing.java
 * @ Description : JSON DATA PARSING
 * @ Modification Information
 * @
 * @    수정일      		  수정자                 			         수정 내용
 * @ ----------   ----------   -------------------------------
 * @ 2018.07.02              고재훈    				 	최초 생성
 *
 * @ since 
 * @ version 
 * @ see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

public class JsonDataParsing {

	//private CsmsService csmsService;
	
	//CsmsMainController csmsMainController = new CsmsMainController();
	
	CollectServiceBean	csb			= new CollectServiceBean();
	//CommonFunction     function    		  = new CommonFunction();
	//CsmsRequest    	   req         		  = new CsmsRequest();
	//CsmsResponse   	   res    			  = new CsmsResponse();
	JsonParser     	   jParser		= new JsonParser();
	
	/**
	 * JSON DATA PARSING MAIN
	 * @param commonVO - 전송 정보가 담겨있는 VO
	 * @throws Exception 
	 */
	public void jsonDataParsingMain(CommonVO commonVO) {
		
		JSONParser jParser = new JSONParser();
		JSONObject jObject = new JSONObject();
		
		try {
		
			jObject = (JSONObject) jParser.parse(commonVO.getRcvMsg());
		
		} catch (ParseException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*COMMON PARSING*/
		commonVO.setSendType(jObject.get("send_type").toString());
		commonVO.setActionType(jObject.get("action_type").toString());
		commonVO.setData(jObject.get("data").toString());
		
		if(commonVO.getActionType().equals("chgrInfo")){
			chgrInfoParsingData(commonVO);						//충전기 설치정보(충전기 -> 충전기정보시스템) CALL
			
		} else if(commonVO.getActionType().equals("chgrStatus")){
			chgrStatusParsingData(commonVO);						//충전기 상태정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("user")){
			userParsingData(commonVO);						//사용자 인증요청(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("chargingStart")){
			chargingStartParsingData(commonVO);						//충전 시작정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("chargingInfo")){
			chargingInfoParsingData(commonVO);						//충전 진행정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("chargingEnd")){
			chargingEndParsingData(commonVO);						//충전 완료정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("chargePayment")){
			chargePaymentParsingData(commonVO);						//충전 결제정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("sendSms")){
			sendSmsParsingData(commonVO);						//문자 전송(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("alarmHistory")){
			alarmHistoryParsingData(commonVO);						//경보 이력(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("reportUpate")){
			reportUpdateParsingData(commonVO);						//펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dChgrStatus")){
			dChgrStatusParsingData(commonVO);						//덤프_충전기상태(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dChargingStart")){
			dChargingStartParsingData(commonVO);						//덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dChargingInfo")){
			dChargingInfoParsingData(commonVO);						//덤프_충전 진행정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dChargingEnd")){
			dChargingEndParsingData(commonVO);						//덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dChargePayment")){
			dChargePaymentParsingData(commonVO);						//덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dAlaramHistory")){
			dAlaramHistoryParsingData(commonVO);						//덤프_경보이력(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("dReportUpdate")){
			dReportUpdateParsingData(commonVO);						//덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL

		} else if(commonVO.getActionType().equals("reset")){
			resetParsingData(commonVO);						//충전기 RESET 요청(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("prices")){
			pricesParsingData(commonVO);						//단가정보(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("changeMode")){
			changeModeParsingData(commonVO);						//충전기모드변경(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("displayBrightness")){
			displayBrightnessParsingData(commonVO);						//충전기 화면밝기정보(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("sound")){
			soundParsingData(commonVO);						//충전기 소리정보(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("askVer")){
			askVerParsingData(commonVO);						//펌웨어 버전 정보 확인(충전기정보시스템 -> 충전기) CALL

		} else if(commonVO.getActionType().equals("notifyVerUpgrade")){
			notifyVerUpgradeParsingData(commonVO);						//펌웨어 버전 정보 업그레이드 알림(충전기정보시스템 -> 충전기) CALL

		} else {
			System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");
		}

		// req Data DB Insert
		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(commonVO.getSendType());
			chgrInfoVO.setMsgActionType(commonVO.getActionType());
			chgrInfoVO.setMsgData(commonVO.getData());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		//응답 보내기
		sendMessage(commonVO);

	}

	/**
	 * WEBSOCKET SEND MESSAGE
	 * @param commonVO - SEND MESSAGE
	 */
	public void sendMessage(CommonVO commonVO) {

		System.out.println("[ MSG <- M/W : "+ commonVO.getSndMsg() +" ]");

		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");

		JSONObject sendData = new JSONObject();

		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		try {

			if (commonVO.getActionType().equals("chgrInfo")) {

				sendJsonObject.put("action_type", "chgrInfo");

				sendData.put("price_apply_yn", "1");

				JSONArray unit_prices_array = new JSONArray();

				JSONObject unit_prices_data = new JSONObject();

				for (int i=0; i<24; i++) {

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

				for (int i=0; i<24; i++) {

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

				for (int i=0; i<24; i++) {

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

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);



			} else if(commonVO.getActionType().equals("chgrStatus")){	//충전기 상태정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "chgrStatus");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("user")){		//사용자 인증요청(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "user");

				sendData.put("card_num", "2100147845698523");
				sendData.put("valid", "1");
				sendData.put("pay_yn", "N");
				sendData.put("charger_pay_yn", "Y");
				sendData.put("member_company", "ME");
				sendData.put("current_unit_cost", "173.8");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("chargingStart")){	//충전 시작정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "chargingStart");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("chargingInfo")){		//충전 진행정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "chargingInfo");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("chargingEnd")){		//충전 완료정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "chargingEnd");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("chargePayment")){	//충전 결제정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "chargePayment");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("sendSms")){			//문자 전송(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "sendSms");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("alarmHistory")){		//경보 이력(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "alarmHistory");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("reportUpate")){		//펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "reportUpate");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("dChargingStart")){	//덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "dChargingStart");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("dChargingEnd")){		//덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "dChargingEnd");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("dChargePayment")){	//덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "dChargePayment");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("dAlaramHistory")){	//덤프_경보이력(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "dAlaramHistory");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("dReportUpdate")){	//덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
				sendJsonObject.put("action_type", "dReportUpdate");

				sendJsonObject.put("data", sendData);

				System.out.println("보낼 JSON : " + sendJsonObject);

			} else if(commonVO.getActionType().equals("reset")){
				resetParsingData(commonVO);						//충전기 RESET 요청(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("prices")){
				pricesParsingData(commonVO);						//단가정보(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("changeMode")){
				changeModeParsingData(commonVO);						//충전기모드변경(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("displayBrightness")){
				displayBrightnessParsingData(commonVO);						//충전기 화면밝기정보(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("sound")){
				soundParsingData(commonVO);						//충전기 소리정보(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("askVer")){
				askVerParsingData(commonVO);						//펌웨어 버전 정보 확인(충전기정보시스템 -> 충전기) CALL

			} else if(commonVO.getActionType().equals("notifyVerUpgrade")){
				notifyVerUpgradeParsingData(commonVO);						//펌웨어 버전 정보 업그레이드 알림(충전기정보시스템 -> 충전기) CALL

			} else {
				System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");
			}

			//응답 데이터 DB에 저장
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

			commonVO.getUserSession().getAsyncRemote().sendText(sendJsonObject.toString());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("[ 클라이언트가 종료되어 MSG를 전송하지 못하였습니다. ]");
		}
	}

	/*충전기 설치정보(충전기 -> 충전기정보시스템)*/
	private void chgrInfoParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			String send_date = (String) data.get("send_date");
			String create_date = (String) data.get("create_date");
			String gps_xpos = (String) data.get("gps_xpos");
			String gps_ypos = (String) data.get("gps_ypos");
			String charger_ip = (String) data.get("charger_ip");
			String charger_port = (String) data.get("charger_port");
			String charger_type = (String) data.get("charger_type");
			String charger_mf = (String) data.get("charger_mf");
			String m2m_mf = (String) data.get("m2m_mf");
			String rf_mf = (String) data.get("rf_mf");
			String m2m_tel = (String) data.get("m2m_tel");
			String van_ip = (String) data.get("van_ip");
			String van_port = (String) data.get("van_port");
			String offline_free_charge_W = (String) data.get("offline_free_charge_W");

			ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();
			ArrayList<PlugDetlInfoVO> plugDetlInfoVOList = new ArrayList<>();

			JSONArray fwVerInfoArr = (JSONArray) data.get("fw_ver_info");
			JSONArray plugDetlInfoArr = (JSONArray) data.get("plug_detl_info");

			for (int i=0; i<fwVerInfoArr.size(); i++) {
				JSONObject tmp = (JSONObject) fwVerInfoArr.get(i);

				String curr_ver = (String) tmp.get("curr_ver");
				String fw_type = (String) tmp.get("fw_type");

				FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
				fwVerInfoVO.setCurr_ver(curr_ver);
				fwVerInfoVO.setFw_type(fw_type);
				fwVerInfoVOList.add(fwVerInfoVO);
			}

			for (int i=0; i<plugDetlInfoArr.size(); i++) {
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
			System.out.println("charger_ip : " + charger_ip);
			System.out.println("charger_port : " + charger_port);
			System.out.println("charger_type : " + charger_type);
			System.out.println("charger_mf : " + charger_mf);
			System.out.println("m2m_mf : " + m2m_mf);
			System.out.println("rf_mf : " + rf_mf);
			System.out.println("m2m_tel : " + m2m_tel);
			System.out.println("van_ip : " + van_ip);
			System.out.println("van_port : " + van_port);
			System.out.println("offline_free_charge_W : " + offline_free_charge_W);
			System.out.println("fwVerInfoVOList : " + fwVerInfoVOList);
			System.out.println("plugDetlInfoVOList : " + plugDetlInfoVOList);
			System.out.println("<------------------------------------------------>");
			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//충전기 상태정보(충전기 -> 충전기정보시스템) CALL
	private void chgrStatusParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			String send_date = (String) data.get("send_date");
			String create_date = (String) data.get("create_date");
			String mode = (String) data.get("mode");
			String integrated_power = (String) data.get("integrated_power");
			String powerbox = (String) data.get("powerbox");

			ArrayList<ChargerPlugVO> chargerPlugList = new ArrayList<>();

			JSONArray chargerPlugArr = (JSONArray) data.get("charger_plug");

			for (int i=0; i<chargerPlugArr.size(); i++) {
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


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//사용자 인증요청(충전기 -> 충전기정보시스템) CALL
	private void userParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			String send_date = (String) data.get("send_date");
			String create_date = (String) data.get("create_date");
			String card_num = (String) data.get("card_num");

			System.out.println("<----------- 사용자 인증요청 Parsing Data ----------->");
			System.out.println("send_date : " + send_date);
			System.out.println("create_date : " + create_date);
			System.out.println("card_num : " + card_num);
			System.out.println("<------------------------------------------------>");


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//충전 시작정보(충전기 -> 충전기정보시스템) CALL
	private void chargingStartParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

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


			System.out.println("<----------- 충전 시작정보 Parsing Data ----------->");
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
			System.out.println("<------------------------------------------------>");


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//충전 진행정보(충전기 -> 충전기정보시스템) CALL
	private void chargingInfoParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

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
			String charge_W  = (String) data.get("charge_W");
			String charge_cost  = (String) data.get("charge_cost");


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
		}
	}

	//충전 완료정보(충전기 -> 충전기정보시스템) CALL
	private void chargingEndParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

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
			String charge_cost  = (String) data.get("charge_cost");
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
		}
	}

	//충전 결제정보(충전기 -> 충전기정보시스템) CALL
	private void chargePaymentParsingData(CommonVO commonVO) {
		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

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
		}
	}

	//문자 전송(충전기 -> 충전기정보시스템) CALL
	private void sendSmsParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

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
		}
	}

	//경보 이력(충전기 -> 충전기정보시스템) CALL
	private void alarmHistoryParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			String send_date = (String) data.get("send_date");
			String create_date = (String) data.get("create_date");
			String alarm_type = (String) data.get("alarm_type");
			String alaram_date = (String) data.get("alaram_date");
			String alarm_code = (String) data.get("alarm_code");

			System.out.println("<----------- 경보 이력 Parsing Data ----------->");
			System.out.println("send_date : " + send_date);
			System.out.println("create_date : " + create_date);
			System.out.println("alarm_type : " + alarm_type);
			System.out.println("alaram_date : " + alaram_date);
			System.out.println("alarm_code : " + alarm_code);
			System.out.println("<------------------------------------------------>");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
	private void reportUpdateParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONObject data	= (JSONObject) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			String send_date = (String) data.get("send_date");
			String create_date = (String) data.get("create_date");
			String update_result = (String) data.get("update_result");

			ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

			JSONArray fwVerInfoArr = (JSONArray) data.get("fw_ver_info");

			for (int i=0; i<fwVerInfoArr.size(); i++) {
				JSONObject tmp = (JSONObject) fwVerInfoArr.get(i);

				String curr_ver = (String) tmp.get("curr_ver");
				String fw_type = (String) tmp.get("fw_type");

				FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
				fwVerInfoVO.setCurr_ver(curr_ver);
				fwVerInfoVO.setFw_type(fw_type);
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
		}
	}

	private void dChgrStatusParsingData(CommonVO commonVO) {

	}

	//덤프_충전 시작정보(충전기 -> 충전기정보시스템) CALL
	private void dChargingStartParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONArray data	= (JSONArray) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			for (int i=0; i<data.size(); i++) {
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


				System.out.println("<----------- 덤프_충전 시작정보 Parsing Data " + (i+1) + " ----------->");
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
		}
	}

	private void dChargingInfoParsingData(CommonVO commonVO) {

	}

	//덤프_충전 완료정보(충전기 -> 충전기정보시스템) CALL
	private void dChargingEndParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONArray data	= (JSONArray) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			for (int i=0; i<data.size(); i++) {
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
				String charge_cost  = (String) tmp.get("charge_cost");
				String cancel_cost = (String) tmp.get("cancel_cost");

				System.out.println("<----------- 덤프_충전 완료정보 Parsing Data " + (i+1) + " ----------->");
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
		}
	}

	//덤프_충전 결제정보(충전기 -> 충전기정보시스템) CALL
	private void dChargePaymentParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONArray data	= (JSONArray) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			for (int i=0; i<data.size(); i++) {
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

				System.out.println("<----------- 덤프_충전 결제정보 Parsing Data " + (i+1) + " ----------->");
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
		}
	}

	//덤프_경보이력(충전기 -> 충전기정보시스템) CALL
	private void dAlaramHistoryParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONArray data	= (JSONArray) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			for (int i=0; i<data.size(); i++) {
				JSONObject tmp = (JSONObject) data.get(i);

				String send_date = (String) tmp.get("send_date");
				String create_date = (String) tmp.get("create_date");
				String alarm_type = (String) tmp.get("alarm_type");
				String alaram_date = (String) tmp.get("alaram_date");
				String alarm_code = (String) tmp.get("alarm_code");

				System.out.println("<----------- 덤프_경보이력 Parsing Data " + (i+1) + "  ----------->");
				System.out.println("send_date : " + send_date);
				System.out.println("create_date : " + create_date);
				System.out.println("alarm_type : " + alarm_type);
				System.out.println("alaram_date : " + alaram_date);
				System.out.println("alarm_code : " + alarm_code);
				System.out.println("<------------------------------------------------>");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//덤프_펌웨어 업데이트 결과 알림(충전기 -> 충전기정보시스템) CALL
	private void dReportUpdateParsingData(CommonVO commonVO) {

		try {
			JSONParser jParser		= new JSONParser();
			JSONArray data	= (JSONArray) jParser.parse(commonVO.getData());

			System.out.println("data : " + data.toString());

			for (int i=0; i<data.size(); i++) {
				JSONObject tmp = (JSONObject) data.get(i);

				String send_date = (String) tmp.get("send_date");
				String create_date = (String) tmp.get("create_date");
				String update_result = (String) tmp.get("update_result");

				ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

				JSONArray fwVerInfoArr = (JSONArray) tmp.get("fw_ver_info");

				for (int j=0; j<fwVerInfoArr.size(); j++) {
					JSONObject jTmp = (JSONObject) fwVerInfoArr.get(j);

					String curr_ver = (String) jTmp.get("curr_ver");
					String fw_type = (String) jTmp.get("fw_type");

					FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
					fwVerInfoVO.setCurr_ver(curr_ver);
					fwVerInfoVO.setFw_type(fw_type);
					fwVerInfoVOList.add(fwVerInfoVO);
				}

				System.out.println("<----------- 덤프_펌웨어 업데이트 결과 알림 Parsing Data " + (i+1) + " ----------->");
				System.out.println("send_date : " + send_date);
				System.out.println("create_date : " + create_date);
				System.out.println("update_result : " + update_result);
				System.out.println("fwVerInfoVOList : " + fwVerInfoVOList);
				System.out.println("<------------------------------------------------>");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
