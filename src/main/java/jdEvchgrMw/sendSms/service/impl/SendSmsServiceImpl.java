package jdEvchgrMw.sendSms.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.sendSms.service.SendSmsService;
import jdEvchgrMw.vo.SendSmsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : SendSmsServiceImpl.java
 * @ Description : 문자 전송 IMPLEMENTS
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

@Service("sendSmsService")
public class SendSmsServiceImpl extends EgovAbstractServiceImpl implements SendSmsService {

    @Resource(name = "sendSmsMapper")
    private SendSmsMapper sendSmsMapper;

    /*문자 전송 등록*/
    public int msgSndListInsert(SendSmsVO sendSmsVO) throws Exception {

        return sendSmsMapper.msgSndListInsert(sendSmsVO);
    }

}
