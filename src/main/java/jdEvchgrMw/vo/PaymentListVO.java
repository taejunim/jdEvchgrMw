package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : PaymentListVO.java
 * @ Description : 신용승인 결제 목록 정보 VO
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
public class PaymentListVO {

    private String providerId;          //충전사업자 ID
    private String stId;                //충전소 ID
    private String chgrId;              //충전기 ID
    private int    chId;                //채널 ID
    private String plugTypeCd;          //플러그 타입 코드
    private String rTimeYn;             //실시간 여부
    private String creditTrxNo;         //신용카드 거래번호
    private String creditTrxDt;         //신용카드 거래일시
    private String payType;             //결제 타입
    private int    payAmt;              //결제 금액
    private int    cPayAmt;             //취소결제 금액
    private String payDetl;             //신용카드 결제 상세정보
    private String payFnshYn;           //선결제 완료 여부
    private String chgrTxDt;            //충전기 전송 일시
}
