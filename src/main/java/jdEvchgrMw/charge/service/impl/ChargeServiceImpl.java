package jdEvchgrMw.charge.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.charge.service.ChargeService;
import jdEvchgrMw.vo.ChargeVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : ChargeServiceImpl.java
 * @ Description : 충전(시작/진행/완료) IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-25 11:53
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-25                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("chargeService")
public class ChargeServiceImpl extends EgovAbstractServiceImpl implements ChargeService {

    Logger logger = LogManager.getLogger(ChargeServiceImpl.class);

    @Resource(name = "chargeMapper")
    private ChargeMapper chargeMapper;

    /*충전 시작 이력의 ID GET*/
    public String getRechgSttListId(ChargeVO chargeVO) throws Exception {

        return chargeMapper.getRechgSttListId(chargeVO);
    }

    /*충전 시작 이력 등록*/
    public int rechgSttInsert(ChargeVO chargeVO) throws Exception {

        return chargeMapper.rechgSttInsert(chargeVO);
    }

    /*충전 진행 이력 등록*/
    public int rechgingInsert(ChargeVO chargeVO) throws Exception {

        String rechgSttListId = getRechgSttListId(chargeVO);

        logger.info("------------------------ 충전 진행 이력 등록 - rechgSttListId : " + rechgSttListId);
        logger.info("------------------------ 충전 진행 이력 등록 - rechgingListId : " + chargeVO.getRechgingListId());

        chargeVO.setRechgSttListId(rechgSttListId);
        return chargeMapper.rechgingInsert(chargeVO);
    }

    /*충전 완료 이력 등록*/
    public int rechgFnshInsert(ChargeVO chargeVO) throws Exception {

        String rechgSttListId = getRechgSttListId(chargeVO);

        logger.info("------------------------ 충전 완료 이력 등록 - rechgSttListId : " + rechgSttListId);
        logger.info("------------------------ 충전 진행 이력 등록 - rechgFnshListId : " + chargeVO.getRechgFnshListId());

        chargeVO.setRechgSttListId(rechgSttListId);
        return chargeMapper.rechgFnshInsert(chargeVO);
    }

}
