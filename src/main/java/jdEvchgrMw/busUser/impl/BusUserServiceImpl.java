package jdEvchgrMw.busUser.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.busUser.BusUserService;
import jdEvchgrMw.vo.BusUserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : BusUserServiceImpl.java
 * @ Description : 버스 사용자 IMPLEMENTS
 * @ author : 임태준
 * @ since : 2020-07-07 09:26
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2020-07-07                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("busUserService")
public class BusUserServiceImpl extends EgovAbstractServiceImpl implements BusUserService {

    Logger logger = LogManager.getLogger(BusUserServiceImpl.class);

    @Resource(name = "busUserMapper")
    private BusUserMapper busUserMapper;

    /*사용자 인증 조회(단가 포함)*/
    public BusUserVO busUserAuthSelect(BusUserVO busUserVO) throws Exception {

        EgovMap egovMap = busUserMapper.busUserAuthSelect(busUserVO);               //회원 인증 조회 CALL

        if (egovMap != null) {

            busUserVO.setCustId(egovMap.get("custId").toString());             //고객 ID
            busUserVO.setStopYn(egovMap.get("stopYn").toString());             //정지 여부
            busUserVO.setAccYn(egovMap.get("accYn").toString());               //정산 여부
            busUserVO.setProviderId(egovMap.get("providerId").toString());     //충전사업자 ID
            busUserVO.setBId(egovMap.get("bId").toString());                   //기관 ID
            busUserVO.setEvbusCarNo(egovMap.get("evbusCarNo").toString());         //버스 차량 번호

            //카드 정지 또는  유효여부 체크
            if (busUserVO.getStopYn().equals("Y")) {

                busUserVO.setAuthResult("2");
                busUserVO.setAuthRsltCd("0"); //인증실패
            }

            //성공
            else {

                busUserVO.setAuthResult("3");
                busUserVO.setAuthRsltCd("1"); //인증성공
                busUserVO.setCurrentUnitCost(busUserMapper.busUserPriceSelect(busUserVO));  //단가 정보 조회 SET
            }
        }

        //카드번호 오류
        else {

            busUserVO.setAuthResult("4");
            busUserVO.setAuthRsltCd("0");  //인증실패
        }

        busUserVO.setAuthRsltValid(busUserVO.getAuthResult());

        busUserVO.setPrice(busUserVO.getCurrentUnitCost());

        logger.info("<----------------------- 회원 인증 이력 등록 OK -------------------------> : " + busUserMapper.busUserAuthListInsert(busUserVO));

        return busUserVO;
    }
}
