package jdEvchgrMw.user.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.user.service.UserService;
import jdEvchgrMw.vo.UserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : UserServiceImpl.java
 * @ Description : 사용자 IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-21 17:36
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("userService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

    Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    /*사용자 인증 조회(단가 포함)*/
    public UserVO userAuthSelect(UserVO userVO) throws Exception {

        EgovMap egovMap = userMapper.userAuthSelect(userVO);               //회원 인증 조회 CALL

        if (egovMap != null) {

            userVO.setCustId(egovMap.get("custId").toString());             //고객 ID
            userVO.setStopYn(egovMap.get("stopYn").toString());             //정지 여부
            userVO.setProviderId(egovMap.get("providerId").toString());     //충전사업자 ID
            userVO.setBId(egovMap.get("bId").toString());     //충전사업자 ID

            //카드 정지 또는  유효여부 체크
            if (userVO.getStopYn().equals("Y")) {

                userVO.setAuthResult("2");
                userVO.setAuthRsltCd("0"); //인증실패
            }

            //성공
            else {

                userVO.setAuthResult("3");
                userVO.setAuthRsltCd("1"); //인증성공
                userVO.setCurrentUnitCost(userMapper.userPriceSelect(userVO));  //단가 정보 조회 SET
            }
        }

        //카드번호 오류
        else {

            userVO.setAuthResult("4");
            userVO.setAuthRsltCd("0");  //인증실패
        }

        userVO.setAuthRsltValid(userVO.getAuthResult());
        userVO.setMemProviderId(userVO.getProviderId());
        userVO.setPrice(userVO.getCurrentUnitCost());

        logger.info("<----------------------- 회원 인증 이력 등록 OK -------------------------> : " + userMapper.userAuthListInsert(userVO));

        return userVO;
    }

    /*단가 정보 조회*/
    public String userPriceSelect(UserVO userVO) throws Exception {

        return userMapper.userPriceSelect(userVO);
    }

    /*회원 인증 이력 등록*/
    public int userAuthListInsert(UserVO userVO) throws Exception {

        return userMapper.userAuthListInsert(userVO);
    }

}
