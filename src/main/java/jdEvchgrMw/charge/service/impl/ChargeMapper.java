package jdEvchgrMw.charge.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import jdEvchgrMw.vo.ChargeVO;

/**
 * @ interface Name  : ChargeMapper.java
 * @ Description : 충전(시작/진행/완료) MAPPER
 * @ author : 고재훈
 * @ since : 2019-11-25 11:52
 * @
 * @ Modification Information
 * @    수정일      	  수정자                수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-25                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Mapper("chargeMapper")
public interface ChargeMapper {

    /*충전 시작 이력 정보*/
    ChargeVO getRechgSttListInfo(ChargeVO chargeVO) throws Exception;

    /*충전 시작 이력 등록*/
    int rechgSttInsert(ChargeVO chargeVO) throws Exception;

    /*충전 진행 이력 등록*/
    int rechgingInsert(ChargeVO chargeVO) throws Exception;

    /*충전 완료 이력 등록*/
    int rechgFnshInsert(ChargeVO chargeVO) throws Exception;

}
