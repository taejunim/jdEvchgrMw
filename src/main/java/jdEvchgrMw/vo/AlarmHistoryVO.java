package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : AlarmHistoryVO.java
 * @ Description : 경보 이력 VO
 * @ autor : 임태준
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
public class AlarmHistoryVO {

    private String alarmId;             //알람 ID
    private String providerId;          //충전사업자 ID
    private String stId;                //충전소 ID
    private String chgrId;              //충전기 ID
    private String mwKindCd;            //미들웨어 종류 코드
    private String rTimeYn;             //실시간 여부
    private String occurDt;             //발생 일시
    private String alarmStateCd;        //알람 상태 코드
    private String alarmCd;             //알람 코드
    private String chgrTxDt;            //충전기 전송 일시

}
