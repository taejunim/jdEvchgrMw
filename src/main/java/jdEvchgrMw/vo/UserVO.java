package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : UserVO.java
 * @ Description : 사용자 인증 VO
 * @ author : 임태준
 * @ since : 2019-11-14 13:26
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-14                       최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class UserVO {

    private String memAuthId;               //사용자 인증 로그 ID
    private String providerId;              //충전사업자 ID
    private String stId;                    //충전소 ID
    private String chgrId;                  //충전기 ID
    private int chId;                       //채널 ID
    private String mwKindCd;                //미들웨어 종류 코드
    private String memAuthReqDt;            //회원 인증 요청 일시
    private String memAuthInputNo;          //회원 인증 입력 번호
    private String resDt;                   //응답 일시
    private String authRsltCd;              //인증 결과 코드
    private String authRsltValid;           //인증 결과 유효
    private String memProviderId;           //회원 충전사업자 ID
    private String price;                   //단가
    private String chgrTxDt;                //충전기 전송 일시
    private String custId;                  //고객 ID
    private String bId;                     //회원사 - 공단, 각 민간 기업코드
    private String stopYn;                  //정지 여부
    private String modUid;                  //수정 UID
    private String authResult;              //인증 결과
    private String currentUnitCost;         //인증 성공 시 적용 단가

}











