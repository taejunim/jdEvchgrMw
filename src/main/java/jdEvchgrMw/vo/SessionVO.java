package jdEvchgrMw.vo;

import lombok.Data;

import javax.websocket.Session;

/**
 * @ Class Name  : SessionVO.java
 * @ Description : 세션 VO
 * @ author : 임태준
 * @ since : 2019-11-18 13:26
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-14                       최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class SessionVO {

    private String sessionId;       //Session Id => 0,1,2, ... ,n
    private String stationChgrId;   //stationChgrId = 충전소ID + 충전기ID
    private Session userSession;	//USER SESSION
}
