package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : ChargePaymentVO.java
 * @ Description : 충전 결제 정보 VO
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
public class ChargePaymentVO {

    private String paymentType;         //결제타입
    private String providerId;          //충전사업자 ID
    private String stId;                //충전소 ID
    private String chgrId;              //충전기 ID
    private int    chId;                //채널 ID
    private String rechgSttListId;      //충전 시작 이력 ID
    private String pPayTrxNo;           //선결제 거래 번호
    private String pPayTrxDt;           //선결제 거래 일시
    private int    pPayAmt;             //선결제 금액
    private String pPayTxDt;            //선결제  전송 일시
    private String rPayTrxNo;           //실결제 거래 번호
    private String rPayTrxDt;           //실결제 거래 일시
    private int    rPayAmt;             //실결제 금액
    private String rPayTxDt;            //실결제  전송 일시
    private String cPayTrxNo;           //취소결제 거래 번호
    private String cPayTrxDt;           //취소결제 거래 일시
    private int    cPayAmt;             //취소결제 금액
    private String cPayTxDt;            //취소결제 전송 일시

}
