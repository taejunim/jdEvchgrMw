package jdEvchgrMw;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

import static jdEvchgrMw.common.ServerUtils.chgrList;
import static jdEvchgrMw.websocket.JdEvChgrMwMain.qDataObjectList;
import static jdEvchgrMw.websocket.JdEvChgrMwMain.sessionList;

@Controller
public class JdEvChgrMwController {

	Logger logger = LogManager.getLogger(JdEvChgrMwController.class);

	/**
	 * MAIN PAGE
	 * @param 
	 * @param 
	 * @return "Main"
	 */
	@RequestMapping(value = "/main.do")
	public String main() throws Exception {
		
		logger.info("MAIN");

		String profile = System.getProperty("spring.profiles.active");

		logger.info("profile : " + profile);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chargingStart");

		JSONObject jData = new JSONObject();
		jData.put("send_date", "20200603102429");
		jData.put("create_date", "20200603102430");

		jData.put("start_date", "20200603102430");
		jData.put("card_num", "123");
		jData.put("credit_trx_no", "");
		jData.put("credit_trx_date", "");
		jData.put("pay_type", "Y");
		jData.put("charger_pay_yn", "Y");
		jData.put("charge_plug_type", "2");
		jData.put("plug_id", "2");
		jData.put("integrated_power", "14562");
		jData.put("prepayment", "3000");
		jData.put("current_V", "40");
		jData.put("current_A", "20");
		jData.put("estimated_charge_time", "2400");

		jsonObject.put("data", jData);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();







		try {
			JSONParser jParser = new JSONParser();
			JSONObject data = (JSONObject) jParser.parse(jsonObject.get("data").toString());

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

			SimpleDateFormat logIdFormat = new SimpleDateFormat("yyMMddHHmmssSSS");     //이력ID 중복을 피하기위해 밀리세컨드 추가하여 구분하기 위함

			//충전 진행 ID
			Date dt = new Date();
			String rechgingListId = logIdFormat.format(dt);
			logger.info("------------------------ 충전 진행 ID - rechgingListId : " + rechgingListId);

			ChargeVO chargeVO = new ChargeVO();
			chargeVO.setProviderId("JD");
			chargeVO.setStId("110001");
			chargeVO.setChgrId("07");
			chargeVO.setMwKindCd("TEST");
			chargeVO.setRTimeYn("Y");
			chargeVO.setChId(2);
			chargeVO.setPlugTypeCd("1");
			chargeVO.setMemAuthInputNo("");
			chargeVO.setRechgingListId(rechgingListId);
			chargeVO.setRechgSdt("2020-05-12 19:20:35");
			chargeVO.setRechgDemandAmt(prepayment);
			chargeVO.setRechgingAmt(0);
			chargeVO.setRechgingWh(0);
			chargeVO.setRechgRemainTime(estimated_charge_time);
			chargeVO.setPayTypeCd("Y");
			chargeVO.setCreditPPayTrxNo("58720842");
			chargeVO.setCreditPPayTrxDt("2020-05-12 19:20:18");
			chargeVO.setIntegratedWh(integrated_power);
			chargeVO.setCurrVolt(current_V);
			chargeVO.setCurrC(current_A);
			chargeVO.setDataCreateDt(create_date);
			chargeVO.setChgrTxDt(send_date);

			QueueVO queueVO = new QueueVO();
			queueVO.setRTimeYn("Y");
			queueVO.setActionType("chargingInfo");
			queueVO.setObject(chargeVO);

			qDataObjectList.offer(queueVO);

		} catch (ParseException e) {

			logger.info("<----------------------- 파싱 오류 ------------------------->");
			logger.error("*******************************************************");
			logger.error("ParseException : " + e);
			logger.error("*******************************************************");
			//commonVO = parameterErrorMsgSend(commonVO);
		} catch (Exception e) {

			logger.info("<----------------------- DB Insert 오류 ------------------------->");
			logger.error("*******************************************************");
			logger.error("Exception : " + e);
			logger.error("*******************************************************");
			//commonVO = unknownErrorMsgSend(commonVO, "DB Insert 오류입니다. 담당자에게 문의주세요.");
			//recvMsg(commonVO);
		}





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

		return "jdEvchgrMw/main";
	}

	/**
	 * DB에 등록된 충전기 목록
	 */
	@RequestMapping(value = "/selectChgrList.do")
	public void selectChgrList() throws Exception {

		logger.info("------------------ /selectChgrList.do ------------------ ");

		CollectServiceBean csb = new CollectServiceBean();

		try {
			chgrList.clear();
			chgrList = csb.beanChgrInfoService().chgrList();

			for (int i=0; i<chgrList.size(); i++) {
				logger.info("[" + (i+1) + "] : " + chgrList.get(i));
			}

		} catch (Exception e) {
			logger.error("selectChgrList Exception : " + e);
		}
	}

	/**
	 * 현재 세션 목록
	 */
	@RequestMapping(value = "/currentSessionList.do")
	public void currentSessionList() throws Exception {

		logger.info("------------------ /currentSessionList.do ------------------");

		logger.info("[ 총 session 수 : " + sessionList.size() + " ]");
		logger.info("  현재 session 목록 -> ");

		for (int i=0; i<sessionList.size(); i++) {
			logger.info("[" + (i+1) + "] : " + sessionList.get(i).getStationChgrId());
		}

		logger.info("------------------------------------------------------------");
	}
}
