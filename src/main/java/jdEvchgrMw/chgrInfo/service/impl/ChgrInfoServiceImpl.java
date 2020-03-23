package jdEvchgrMw.chgrInfo.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.user.service.impl.UserMapper;
import jdEvchgrMw.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @ class Name  : ChgrInfoServiceImpl.java
 * @ Description : 충전기 설치 정보 IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-19 10:01
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-19                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("chgrInfoService")
public class ChgrInfoServiceImpl extends EgovAbstractServiceImpl implements ChgrInfoService {

    Logger logger = LogManager.getLogger(ChgrInfoServiceImpl.class);

    @Resource(name = "chgrInfoMapper")
    private ChgrInfoMapper chgrInfoMapper;

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    /*충전기 목록*/
    public ArrayList<String> chgrList() throws Exception {

        ArrayList<String> chgrInfoVOArrayList = chgrInfoMapper.chgrList();

        return chgrInfoVOArrayList;
    }

    /*충전기 사운드 및 밝기 조회*/
    public DeviceConfigVO deviceConfigSelect(DeviceConfigVO deviceConfigVO) throws Exception {

        DeviceConfigVO resultDeviceConfigVO = chgrInfoMapper.deviceConfigSelect(deviceConfigVO);

        //단가 조회
        UserVO userVO = new UserVO();
        userVO.setProviderId(deviceConfigVO.getProviderId());

        String price = userMapper.userPriceSelect(userVO);

        resultDeviceConfigVO.setPrice(price);
        logger.info("***** 단가 ***** : " + price);
        logger.info("***** resultDeviceConfigVO ***** : " + resultDeviceConfigVO);

        return resultDeviceConfigVO;
    }

    /*충전기 설치 정보 수정*/
    public DeviceConfigVO chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

        //펌웨어 정보 Update
        logger.info("<----------------------- 펌웨어 정보 Update OK -------------------------> : " + chgrFwVerInfoInsUpdate(chgrInfoVO));

        ArrayList<PlugDetlInfoVO> plugDetlInfoVOList = chgrInfoVO.getPlugDetlInfoVOList();

        //채널 정보 Update
        for (int i = 0; i < plugDetlInfoVOList.size(); i++) {

            chgrInfoVO.setChId(Integer.parseInt(plugDetlInfoVOList.get(i).getPlug_id()));
            chgrInfoVO.setPlugTypeCd(plugDetlInfoVOList.get(i).getPlug_type());

            logger.info("<----------------------- 채널 정보 Update OK -------------------------> : " + chgrInfoMapper.chgrChInfoInsUpdate(chgrInfoVO));

            logger.info("----------------------- 채널 : " + chgrInfoVO.getChId() + ", plugTypeCd : " + chgrInfoVO.getPlugTypeCd());
        }

        logger.info("<----------------------- 설치 정보 Update OK -------------------------> : " + chgrInfoMapper.chgrInfoUpdate(chgrInfoVO));

        //충전기 사운드 및 밝기 조회
        DeviceConfigVO deviceConfigVO = new DeviceConfigVO();
        deviceConfigVO.setProviderId(chgrInfoVO.getProviderId());
        deviceConfigVO.setStId(chgrInfoVO.getStId());
        deviceConfigVO.setChgrId(chgrInfoVO.getChgrId());

        return deviceConfigSelect(deviceConfigVO);
    }

    /*충전기 펌웨어 버전 정보 등록 및 수정*/
    public int chgrFwVerInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

        int result = 0;

        ArrayList<FwVerInfoVO> fwVerInfoVOList = chgrInfoVO.getFwVerInfoVOList();			//펌웨어 리스트

        //펌웨어 정보 Update
        for (int i = 0; i < fwVerInfoVOList.size(); i++) {

            chgrInfoVO.setFwType(fwVerInfoVOList.get(i).getFwType());
            chgrInfoVO.setCurrVer(fwVerInfoVOList.get(i).getCurrVer());

            chgrInfoMapper.chgrFwVerInfoInsUpdate(chgrInfoVO);
            result++;
        }

        return result;
    }

}
