package jdEvchgrMw.chargePayment.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.ChargePaymentVO;
import jdEvchgrMw.vo.PaymentInfoVO;
import jdEvchgrMw.vo.PaymentListVO;

/**
 * @ interface Name  : ChargePaymentMapper.java
 * @ Description : 충전 결제 정보 MAPPER
 * @ author : 고재훈
 * @ since : 2019-11-27 16:00
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-27                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Mapper("chargePaymentMapper")
public interface ChargePaymentMapper {

    /*충전 결제 정보의 선결제 정보 조회*/
    EgovMap rechgSttInfoSelect(ChargePaymentVO chargePaymentVO) throws Exception;

    /*충전 결제 정보 등록(실충전결제 일때))*/
    int creditTrxInfoInsert(ChargePaymentVO chargePaymentVO) throws Exception;

    /*충전 결제 정보 수정(취소결제 일때))*/
    int creditTrxInfoUpdate(ChargePaymentVO chargePaymentVO) throws Exception;

    /*신용승인 결제정보 등록*/
    int paymentInfoInsert(PaymentInfoVO paymentInfoVO) throws Exception;

    /*신용승인 결제정보 수정*/
    int paymentInfoUpdate(PaymentInfoVO paymentInfoVO) throws Exception;

    /*신용승인 결제 리스트 등록*/
    int paymentListInsert(PaymentListVO paymentListVO) throws Exception;
}
