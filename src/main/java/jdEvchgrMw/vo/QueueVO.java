package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : QueueVO.java
 * @ Description : 큐 VO
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
public class QueueVO {

    private String rTimeYn;             //실시간 여부
    private String actionType;
    private Object object;
}
