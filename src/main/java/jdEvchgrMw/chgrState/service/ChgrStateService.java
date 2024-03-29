package jdEvchgrMw.chgrState.service;

import jdEvchgrMw.vo.ChgrStatusVO;

/**
 * @ interface Name  : ChgrStateService.java
 * @ Description : 상태 SERVICE
 * @ author : 고재훈
 * @ since : 2019-11-21 13:49
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-21                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/
public interface ChgrStateService {

    /*충전기 상태 이력 등록 및 상태 수정*/
    int chgrStateUpdate(ChgrStatusVO chgrStatusVO) throws Exception;

}
