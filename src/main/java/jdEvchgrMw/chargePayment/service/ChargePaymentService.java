package jdEvchgrMw.chargePayment.service;

import jdEvchgrMw.vo.ChargePaymentVO;
import jdEvchgrMw.vo.PaymentInfoVO;
import jdEvchgrMw.vo.PaymentListVO;

/**
 * @ interface Name  : ChargePaymentService.java
 * @ Description : 충전 결제 정보 SERVICE
 * @ author : 고재훈
 * @ since : 2019-11-27 15:55
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-27                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface ChargePaymentService {

    /*충전 결제 정보 등록(실충전결제 일때))*/
    int creditTrxInfoInsert(ChargePaymentVO chargePaymentVO) throws Exception;

    /*충전 결제 정보 수정(취소결제 일때))*/
    int creditTrxInfoUpdate(ChargePaymentVO chargePaymentVO) throws Exception;

    /*신용승인 결제정보 등록*/
    int paymentInfoInsert(PaymentInfoVO paymentInfoVO, PaymentListVO paymentListVO) throws Exception;

    /*신용승인 결제정보 수정*/
    int paymentInfoUpdate(PaymentInfoVO paymentInfoVO, PaymentListVO paymentListVO) throws Exception;

    /*신용승인 결제 리스트 등록*/
    int paymentListInsert(PaymentListVO paymentListVO) throws Exception;
}
