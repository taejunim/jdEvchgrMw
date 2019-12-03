package jdEvchgrMw.controlChgr.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.controlChgr.service.ControlChgrService;
import jdEvchgrMw.vo.ControlChgrVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : ControlServiceImpl.java
 * @ Description : 제어 IMPLEMENTS
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

@Service("controlChgrService")
public class ControlServiceImpl extends EgovAbstractServiceImpl implements ControlChgrService {

    @Resource(name = "controlChgrMapper")
    private ControlChgrMapper controlChgrMapper;

    /*제어 이력 수정(TABLE: CTRL_LIST / M/W->관제)*/
    public int ctrlListUpdate(ControlChgrVO controlChgrVO) throws Exception {

        return controlChgrMapper.ctrlListUpdate(controlChgrVO);
    }

    /*제어 이력 등록(TABLE: TX_MSG_LIST / M/W->충전기)*/
    public int txMsgListInsert(ControlChgrVO controlChgrVO) throws Exception {

        return controlChgrMapper.txMsgListInsert(controlChgrVO);
    }

    /*제어 이력 수정(TABLE: TX_MSG_LIST / 충전기->M/W)*/
    public int txMsgListUpdate(ControlChgrVO controlChgrVO) throws Exception {

        return controlChgrMapper.txMsgListUpdate(controlChgrVO);
    }

}
