package jdEvchgrMw.busUser.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.BusUserVO;

/**
 * @ interface Name  : BusUserMapper.java
 * @ Description : 버스 사용자 MAPPER
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

@Mapper("busUserMapper")
public interface BusUserMapper {

    /*버스 회원 인증 조회*/
    EgovMap busUserAuthSelect(BusUserVO busUserVO) throws Exception;

    /*버스 단가 정보 조회*/
    String busUserPriceSelect(BusUserVO busUserVO) throws Exception;

    /*버스 회원 인증 이력 등록*/
    int busUserAuthListInsert(BusUserVO busUserVO) throws Exception;

}
