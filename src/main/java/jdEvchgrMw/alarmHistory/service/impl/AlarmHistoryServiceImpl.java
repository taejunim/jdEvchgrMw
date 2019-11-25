package jdEvchgrMw.alarmHistory.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.alarmHistory.service.AlarmHistoryService;
import jdEvchgrMw.vo.AlarmHistoryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : AlarmHistoryServiceImpl.java
 * @ Description : 알람 이력 IMPLEMENTS
 * @ author : 고재훈
 * @ since : 2019-11-20 10:15
 * @
 * @ Modification Information
 * @ 수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-20                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("alarmHistoryService")
public class AlarmHistoryServiceImpl extends EgovAbstractServiceImpl implements AlarmHistoryService {

    @Resource(name = "alarmHistoryMapper")
    private AlarmHistoryMapper alarmHistoryMapper;

    /*알람 이력 등록*/
    public int alarmHistoryInsert(AlarmHistoryVO alarmHistoryVO) throws Exception {

        return alarmHistoryMapper.alarmHistoryInsert(alarmHistoryVO);
    }

}
