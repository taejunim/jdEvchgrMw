package jdEvchgrMw.revMsg.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.revMsg.service.RevMsgService;
import jdEvchgrMw.vo.RevMsgVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : RevMsgServiceImpl.java
 * @ Description : 수신 전문 이력 IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-19 10:01
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-19                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("revMsgService")
public class RevMsgServiceImpl extends EgovAbstractServiceImpl implements RevMsgService {

    @Resource(name = "revMsgMapper")
    private RevMsgMapper revMsgMapper;

    /*수신 전문 인력 등록*/
    public int recvMsgInsert(RevMsgVO revMsgVO) throws Exception {

        return revMsgMapper.recvMsgInsert(revMsgVO);
    }
}
