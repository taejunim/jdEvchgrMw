package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : ChargeVO.java
 * @ Description : 충전(시작/진행/완료) VO
 * @ author : 고재훈
 * @ since : 2019-11-25 11:39
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-25                       최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class ChargeVO {

    private String providerId;              //충전사업자 ID
    private String stId;                    //충전소 ID
    private String chgrId;                  //충전기 ID
    private String mwKindCd;                //미들웨어 종류 코드
    private String rTimeYn;                 //실시간 여부
    private int    chId;                    //채널 ID
    private String plugTypeCd;              //플러그 타입 코드
    private String memAuthInputNo;          //회원 인증 입력 번호
    private String rechgSttListId;          //충전 시작이력 ID
    private String rechgingListId;          //충전 진이력 ID
    private String rechgFnshListId;         //충전 완료이력 ID
    private String rechgSdt;                //충전 시작일시
    private String rechgEdt;                //충전 종료일시
    private String rechgFnshTpCd;           //충전 완료 구분 코드
    private int    rechgDemandAmt;          //충전 요구 금액
    private String rechgEstTime;            //충전 추정 시간
    private int    rechgingAmt;             //충전 진행 금액
    private int    rechgingWh;              //충전 진행 WH
    private int    rechgAmt;                //충전 금액
    private int    rechgWh;                 //충전 WH
    private String rechgTime;               //충전 시간
    private String rechgRemainTime;         //충전 잔여 시간
    private String chgrPayingYn;            //충전기 유료 여부
    private String payTypeCd;               //결제 타입 코드
    private String memAuthId;               //회원 인증 ID
    private String creditPPayTrxNo;         //신용카드 선결제 거래 번호
    private String creditPPayTrxDt;         //신용카드 선결제 거래 일시
    private int    cancelAmt;               //부분취소 금액
    private String cancelDetl;              //부분취소_상세정보
    private int    integratedWh;            //적산 WH
    private double dcPower;                 //DC 적산유효전력량
    private double acPower;                 //AC 적산유효전력량
    private String currVolt;                //현재 전압
    private String currC;                   //현재 전류
    private String dataCreateDt;            //데이터 생성 일시
    private String chgrTxDt;                //충전기 전송 일시
    private String payFnshYn;               //결제 완료 여부

}
