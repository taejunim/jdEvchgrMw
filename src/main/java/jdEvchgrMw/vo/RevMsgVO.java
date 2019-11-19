package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : ResetVO.java
 * @ Description : 수신 전문 이력 VO
 * @ autor : 고재훈
 * @ since : 2019-11-18 18:56
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-18                       최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class RevMsgVO {

    private String recvLogId;           //수신 로그 ID
    private String providerId;          //충전사업자 ID
    private String stId;                //충전소 ID
    private String chgrId;              //충전기 ID
    private String mwKindCd;            //미들웨어 종류 코드
    private String cmdTp;               //명령어 구분
    private String rTimeYn;             //실시간 여부
    private String dataCreateDt;        //데이터 생성 일시
    private String chgrTxDt;            //충전기 전송 일시
    private String recvMsg;             //수신 전문
    private String resDt;               //응답 일시
    private String resCd;               //응답 코드
    private String resRsnCd;            //응답 사유 코드
    private String resMsg;              //응답 전문

}
