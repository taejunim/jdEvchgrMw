package jdEvchgrMw.user.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.UserVO;

/**
 * @ interface Name  : UserMapper.java
 * @ Description : 사용자 MAPPER
 * @ author : 고재훈
 * @ since : 2019-11-21 15:43
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Mapper("userMapper")
public interface UserMapper {

    /*회원 인증 조회*/
    EgovMap userAuthSelect(UserVO userVO) throws Exception;

    /*단가 정보 조회*/
    String userPriceSelect(UserVO userVO) throws Exception;

    /*회원 인증 이력 등록*/
    int userAuthListInert(UserVO userVO) throws Exception;

}
