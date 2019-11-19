package jdEvchgrMw.chgrInfo.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.ChgrInfoVO;

/**
 * @ interface Name  : ChgrInfoMapper.java
 * @ Description : 충전기 설치 정보 MAPPER
 * @ autor : 고재훈
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

    /*충전기 설지 정보 UPDATE*/
    public int chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

}
