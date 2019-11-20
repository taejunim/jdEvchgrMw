package jdEvchgrMw.alarmHistory.service;

import jdEvchgrMw.vo.AlarmHistoryVO;

/**
 * @ interface Name  : AlarmHistoryService.java
 * @ Description : 알람 이력 SERVICE
 * @ autor : 고재훈
 * @ since : 2019-11-20 10:12
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-20                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public interface AlarmHistoryService {

    /*알람 이력 등록*/
    int alarmHistoryInsert(AlarmHistoryVO alarmHistoryVO) throws Exception;

}
