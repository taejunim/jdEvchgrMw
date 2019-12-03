package jdEvchgrMw.sendSms.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import jdEvchgrMw.vo.SendSmsVO;

/**
 * @ interface Name  : SendSmsMapper.java
 * @ Description : 문자 전송 MAPPER
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

@Mapper("sendSmsMapper")
public interface SendSmsMapper {

    /*문자 전송 등록*/
    int msgSndListInsert(SendSmsVO sendSmsVO) throws Exception;

}
