package jdEvchgrMw.chgrState.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import jdEvchgrMw.vo.ChgrStatusVO;

/**
 * @ interface Name  : ChgrStateMapper.java
 * @ Description : 충전기 상태 MAPPER
 * @ author : 고재훈
 * @ since : 2019-11-21 13:47
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Mapper("chgrStateMapper")
public interface ChgrStateMapper {

    /*충전기 현재 상태 수정*/
    int chgrCurrStateUpdate(ChgrStatusVO chgrStatusVO) throws Exception;

    /*충전기 상태 이력 등록*/
    int chgrStateInsert(ChgrStatusVO chgrStatusVO) throws Exception;

}
