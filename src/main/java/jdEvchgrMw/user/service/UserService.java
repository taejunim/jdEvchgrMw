package jdEvchgrMw.user.service;

import jdEvchgrMw.vo.UserVO;

/**
 * @ interface Name  : UserService.java
 * @ Description : 사용자 SERVICE
 * @ author : 고재훈
 * @ since : 2019-11-21 18:23
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface UserService {

    /*사용자 인증 조회(단가 포함)*/
    UserVO userAuthSelect(UserVO userVO) throws Exception;

    /*단가 정보 조회*/
    String userPriceSelect(UserVO userVO) throws Exception;

    /*회원 인증 이력 등록*/
    int userAuthListInsert(UserVO userVO) throws Exception;

}
