package jdEvchgrMw.chgrInfo.service;

import jdEvchgrMw.vo.ChgrInfoVO;
import jdEvchgrMw.vo.DeviceConfigVO;

import java.util.ArrayList;

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

    /*충전기 목록*/
    ArrayList<String> chgrList() throws Exception;

    /*충전기 사운드 및 밝기 조회*/
    DeviceConfigVO deviceConfigSelect(DeviceConfigVO deviceConfigVO) throws Exception;

    /*충전기 설치 정보 UPDATE*/
    DeviceConfigVO chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

    /*충전기 펌웨어 버전 정보 등록 및 수정*/
    int chgrFwVerInfoInsUpdate(ChgrInfoVO chgrInfoVO) throws Exception;

}
