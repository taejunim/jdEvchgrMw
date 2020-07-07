package jdEvchgrMw.busUser;

import jdEvchgrMw.vo.BusUserVO;

/**
 * @ interface Name  : BusUserService.java
 * @ Description : 버스 사용자 SERVICE
 * @ author : 임태준
 * @ since : 2020-07-07 09:26
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2020-07-07                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface BusUserService {

    /*버스 사용자 인증 조회(단가 포함)*/
    BusUserVO busUserAuthSelect(BusUserVO busUserVO) throws Exception;
}
