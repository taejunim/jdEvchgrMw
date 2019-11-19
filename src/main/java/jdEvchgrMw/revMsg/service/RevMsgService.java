package jdEvchgrMw.revMsg.service;

import jdEvchgrMw.vo.RevMsgVO;

/**
 * @ interface Name  : RevMsgService.java
 * @ Description : 수신 전문 이력 SERVICE
 * @ autor : 고재훈
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

public interface RevMsgService {

    /*수신 전문 이력의 수신 로그 ID GET*/
    public String getRecvLogId() throws Exception;

    /*수신 전문 인력 등록*/
    public int recvMsgInsert(RevMsgVO revMsgVO) throws Exception;

    /*수신 전문 인력 수정*/
    public int recvMsgUpdate(RevMsgVO revMsgVO) throws Exception;

}