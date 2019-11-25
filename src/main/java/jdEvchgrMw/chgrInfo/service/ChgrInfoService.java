package jdEvchgrMw.chgrInfo.service;

import jdEvchgrMw.vo.ChgrInfoVO;

/**
 * @ interface Name  : ChgrInfoService.java
 * @ Description : 충전기 설치 정보 SERVICE
 * @ author : 고재훈
 * @ since : 2019-11-19 10:01
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-19                               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface ChgrInfoService {

    /*충전기 설지 정보 UPDATE*/
    int chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

    /*충전기 채널 정보 등록 및 수정*/
    int chgrChInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

}
