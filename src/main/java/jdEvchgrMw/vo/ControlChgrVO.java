package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : ControlChgrVO.java
 * @ Description : 제어 VO
 * @ author : 고재훈
 * @ since : 2019-12-03 16:15
 * @
 * @ Modification Information
 * @    수정일      	  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-12-03                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class ControlChgrVO {

    private String ctrlListId;		//제어 이력 ID
    private String uuid;            //UUID
    private String providerId;		//충전사업자 ID
    private String stationId;		//충전소 ID
    private String chgrId;			//충전기 ID
    private String mwKindCd;        //미들웨어 종류 코드
    private String dataCreateDt;    //관제에서 보낸 sendDate
    private String txDt;            //M/W에서 전송한 시간
    private String txMsg;	        //전송 전문
    private String resDt;	        //응답 일시
    private String resCd;	        //응답 코드
    private String resRsnCd;	    //응답 사유 코드
    private String resMsg;	        //응답 전문

}
