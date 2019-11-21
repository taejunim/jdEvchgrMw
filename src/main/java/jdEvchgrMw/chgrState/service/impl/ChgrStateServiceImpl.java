package jdEvchgrMw.chgrState.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.chgrState.service.ChgrStateService;
import jdEvchgrMw.vo.ChgrStatusVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : ChgrStateServiceImpl.java
 * @ Description : 충전기 상태 IMPLEMENTS
 * @ autor : 고재훈
 * @ since : 2019-11-21 13:48
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("chgrStateService")
public class ChgrStateServiceImpl extends EgovAbstractServiceImpl implements ChgrStateService {

    @Resource(name = "chgrStateMapper")
    private ChgrStateMapper chgrStateMapper;

    /*충전기 현재 상태 수정*/
    public int chgrCurrStateUpdate(ChgrStatusVO chgrStatusVO) throws Exception {

        return chgrStateMapper.chgrCurrStateUpdate(chgrStatusVO);
    }

    /*충전기 상태 이력 등록*/
    public int chgrStateInsert(ChgrStatusVO chgrStatusVO) throws Exception {

        return chgrStateMapper.chgrStateInsert(chgrStatusVO);
    }

}
