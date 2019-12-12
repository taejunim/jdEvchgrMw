package jdEvchgrMw.chgrInfo.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.ChgrInfoVO;
import jdEvchgrMw.vo.DeviceConfigVO;

/**
 * @ interface Name  : ChgrInfoMapper.java
 * @ Description : 충전기 설치 정보 MAPPER
 * @ author : 고재훈
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

@Mapper("chgrInfoMapper")
public interface ChgrInfoMapper {

    /*충전기 유효성 체크*/
    EgovMap chgrCheckValidation(ChgrInfoVO chgrInfoVO) throws Exception;

    /*충전기 사운드 및 밝기 조회*/
    DeviceConfigVO deviceConfigSelect(DeviceConfigVO deviceConfigVO) throws Exception;

    /*충전기 설지 정보 수정*/
    int chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

    /*충전기 채널 정보 등록 및 수정*/
    int chgrChInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

    /*충전기 펌웨어 버전 정보 등록 및 수정*/
    int chgrFwVerInfoInsUpdate(ChgrInfoVO chgrInVO) throws Exception;

}
