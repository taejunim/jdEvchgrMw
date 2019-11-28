package jdEvchgrMw.chgrInfo.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.vo.ChgrInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource(name = "chgrInfoMapper")
    private ChgrInfoMapper chgrInfoMapper;

    /*충전기 설지 정보 수정*/
    public int chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

        return chgrInfoMapper.chgrInfoUpdate(chgrInfoVO);
    }

    /*충전기 채널 정보 등록 및 수정*/
    public int chgrChInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

        return chgrInfoMapper.chgrChInfoInsUpdate(chgrInfoVO);
    }

    /*충전기 펌웨어 버전 정보 등록 및 수정*/
    public int chgrFwVerInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

        return chgrInfoMapper.chgrFwVerInfoInsUpdate(chgrInfoVO);
    }

}
