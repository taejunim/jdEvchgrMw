package jdEvchgrMw.websocket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonParser;

import jdEvchgrMw.chgrInfo.service.ChgrInfoVO;
import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.CommonVO;

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
			
		}else
			System.out.println("[ 정의 되지 않은 PACKET 입니다. ]");
	}

	/*충전기 설치정보(충전기 -> 충전기정보시스템)*/
	private void chgrInfoParsingData(CommonVO commonVO) {

		JSONParser jParser		= new JSONParser();
		JSONObject jObject		= new JSONObject();
		ChgrInfoVO chgrInfoVO	= new ChgrInfoVO();
		
		try {
			
			jObject = (JSONObject) jParser.parse(commonVO.getData());
			
			
			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
