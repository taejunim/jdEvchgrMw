package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : PaymentInfoVO.java
 * @ Description : 신용승인 결제정보 VO
 * @ author : 임태준
 * @ since : 2020-01-31 13:26
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
public class PaymentInfoVO {

    private String providerId;          //충전사업자 ID
    private String stId;                //충전소 ID
    private String chgrId;              //충전기 ID
    private int    chId;                //채널 ID
    private String plugTypeCd;          //플러그 타입 코드
    private String creditTrxNo;         //신용카드 거래번호
    private String creditTrxDt;         //신용카드 거래일시
    private int    payAmt;              //결제 금액
    private String cPayType;            //취소결제 타입
    private String cPayTrxDt;           //취소결제 거래일시
    private int    cPayAmt;             //취소결제 금액
    private String creditPayDetl;       //신용카드 결제 상세정보
    private String pPayFnshYn;          //선결제 완료 여부
    private String cPayFnshYn;          //취소결제 완료 여부
    private String pPayChgrTxDt;        //선결제 충전기 전송 일시
    private String cPayChgrTxDt;        //취소결제 충전기 전송 일시
}
