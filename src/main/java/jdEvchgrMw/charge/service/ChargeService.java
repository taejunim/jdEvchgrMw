package jdEvchgrMw.charge.service;

import jdEvchgrMw.vo.ChargeVO;

/**
 * @ interface Name  : ChargeService.java
 * @ Description : 충전(시작/진행/완료) SERVICE
 * @ author : 고재훈
 * @ since : 2019-11-25 11:54
 * @
 * @ Modification Information
 * @    수정일      	  수정자                   수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-25                                    최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface ChargeService {

    /*충전 시작 이력의 ID GET*/
    String getRechgSttListId(ChargeVO chargeVO) throws Exception;

    /*충전 시작 이력 등록*/
    int rechgSttInsert(ChargeVO chargeVO) throws Exception;

    /*충전 진행 이력 등록*/
    int rechgingInsert(ChargeVO chargeVO) throws Exception;

    /*충전 완료 이력 등록*/
    int rechgFnshInsert(ChargeVO chargeVO) throws Exception;

}
