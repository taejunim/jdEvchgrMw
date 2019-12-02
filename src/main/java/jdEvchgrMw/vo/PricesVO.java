package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : PricesVO.java
 * @ Description : 단가 정보 VO
 * @ author : 임태준
 * @ since : 2019-11-14 13:26
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
public class PricesVO {

    private String ctrlListId;		//제어이력 ID
    private String stationId;		//충전소 ID
    private String chgrId;			//충전기 ID
}
