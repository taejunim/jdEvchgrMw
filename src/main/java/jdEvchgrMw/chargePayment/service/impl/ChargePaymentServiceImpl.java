package jdEvchgrMw.chargePayment.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.chargePayment.service.ChargePaymentService;
import jdEvchgrMw.vo.ChargePaymentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : ChargePaymentServiceImpl.java
 * @ Description : 충전 결제 정보 IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-27 16:01
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-27                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("chargePaymentService")
public class ChargePaymentServiceImpl extends EgovAbstractServiceImpl implements ChargePaymentService {

    @Resource(name = "chargePaymentMapper")
    private ChargePaymentMapper chargePaymentMapper;

    /*충전 결제 정보 등록(실충전결제 일때))*/
    public int creditTrxInfoInsert(ChargePaymentVO chargePaymentVO) throws Exception {

        EgovMap egovMap = chargePaymentMapper.rechgSttInfoSelect(chargePaymentVO);

        if(egovMap != null) {

            if(egovMap.get("rechgingListId") != null )
                chargePaymentVO.setRechgSttListId(egovMap.get("rechgingListId").toString());

            if(egovMap.get("rechgDemandAmt") != null )
                chargePaymentVO.setPPayAmt(Integer.valueOf(egovMap.get("rechgDemandAmt").toString()));      //충전 결제 정보의 선결제 정보 조회 CALL

            if(egovMap.get("creditPPayTrxNo") != null )
                chargePaymentVO.setPPayTrxNo(egovMap.get("creditPPayTrxNo").toString());

            if(egovMap.get("creditPPayTrxDt") != null )
                chargePaymentVO.setPPayTrxDt(egovMap.get("creditPPayTrxDt").toString());

        }

        return chargePaymentMapper.creditTrxInfoInsert(chargePaymentVO);
    }

    /*충전 결제 정보 수정(취소결제 일때))*/
    public int creditTrxInfoUpdate(ChargePaymentVO chargePaymentVO) throws Exception {

        EgovMap egovMap = chargePaymentMapper.rechgSttInfoSelect(chargePaymentVO);                          //충전 결제 정보의 선결제 정보 조회 CALL

        if(egovMap != null) {

            if(egovMap.get("rechgingListId") != null )
                chargePaymentVO.setRechgSttListId(egovMap.get("rechgingListId").toString());
        }

        return chargePaymentMapper.creditTrxInfoUpdate(chargePaymentVO);
    }

}
