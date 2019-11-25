package jdEvchgrMw.user.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.user.service.UserService;
import jdEvchgrMw.vo.UserVO;
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

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    /*사용자 인증 조회(단가 포함)*/
    public UserVO userAuthSelect(UserVO userVO) throws Exception {

        EgovMap egovMap = userMapper.userAuthSelect(userVO);               //회원 인증 조회 CALL

        if(egovMap != null){

            userVO.setCustId(egovMap.get("custId").toString());             //고객 ID
            userVO.setPayKindCd(egovMap.get("payKindCd").toString());       //결제 종류 코드
            userVO.setStopYn(egovMap.get("stopYn").toString());             //정지 여부
            userVO.setStopRsn(egovMap.get("stopRsn").toString());           //정지 사유
            userVO.setValidYn(egovMap.get("validYn").toString());           //유효 여부
            userVO.setRegDt(egovMap.get("regDt").toString());               //등록 일시
            userVO.setRegUid(egovMap.get("regUid").toString());             //등록 UID
            userVO.setModDt(egovMap.get("modDt").toString());               //수정 일시
            userVO.setModUid(egovMap.get("modUid").toString());             //수정 UID
            userVO.setProviderId(egovMap.get("providerId").toString());     //충전사업자 ID

            if (userVO.getStopYn().equals("Y") || userVO.getValidYn().equals("N"))  //카드 정지 또는  유효여부 체크
                userVO.setAuthResult("2");

            else {                                                                  //성공

                userVO.setAuthResult("3");
                userVO.setCurrentUnitCost(userMapper.userPriceSelect(userVO));      //단가 정보 조회 SET
            }
        }else                                                                      //카드번호 오류
            userVO.setAuthResult("4");

        return userVO;
    }

    /*단가 정보 조회*/
    public String userPriceSelect(UserVO userVO) throws Exception {

        return userMapper.userPriceSelect(userVO);
    }

    /*회원 인증 이력 등록*/
    public int userAuthListInert(UserVO userVO) throws Exception {

        return userMapper.userAuthListInert(userVO);
    }

}
