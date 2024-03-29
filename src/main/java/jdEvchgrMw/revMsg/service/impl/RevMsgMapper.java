package jdEvchgrMw.revMsg.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import jdEvchgrMw.vo.RevMsgVO;

/**
 * @ interface Name  : RevMsgMapper.java
 * @ Description : 수신 전문 이력 MAPPER
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

@Mapper("revMsgMapper")
public interface RevMsgMapper {

    /*수신 전문 인력 등록*/
    int recvMsgInsert(RevMsgVO revMsgVO) throws Exception;
}
