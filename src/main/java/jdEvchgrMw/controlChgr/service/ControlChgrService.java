package jdEvchgrMw.controlChgr.service;

import jdEvchgrMw.vo.ControlChgrVO;

/**
 * @ interface Name  : ControlChgrService.java
 * @ Description : 제어 SERVICE
 * @ author : 고재훈
 * @ since : 2019-12-03 16:15
 * @
 * @ Modification Information
 * @    수정일      	  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-12-03                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface ControlChgrService {

    /*제어 이력 수정(TABLE: CTRL_LIST / M/W->관제)*/
    int ctrlListUpdate(ControlChgrVO controlChgrVO) throws Exception;

    /*제어 이력 등록(TABLE: TX_MSG_LIST / M/W->충전기)*/
    int txMsgListInsert(ControlChgrVO controlChgrVO) throws Exception;

    /*제어 이력 수정(TABLE: TX_MSG_LIST / 충전기->M/W)*/
    int txMsgListUpdate(ControlChgrVO controlChgrVO) throws Exception;
    
}
