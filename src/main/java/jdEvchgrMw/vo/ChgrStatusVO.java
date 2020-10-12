package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : ChgrStatusVO.java
 * @ Description : 충전기 상태 정보 VO
 * @ author : 임태준
 * @ since : 2019-11-14 13:26
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-14                            최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class ChgrStatusVO{

    private String stateListId;             //충전기 상태 로그 ID
    private String providerId;              //충전사업자 ID
    private String stId;                    //충전소 ID
    private String chgrId;                  //충전기 ID
    private String stateDt;                 //상태 일시
    private String opModeCd;                //운영 모드 코드
    private String ch1RechgStateCd;         //채널1 충전 상태 코드
    private String ch1DoorStateCd;          //채널1 DOOR 상태 코드
    private String ch1PlugStateCd;          //채널1 PLUG 상태 코드
    private String ch2RechgStateCd;         //채널2 충전 상태 코드
    private String ch2DoorStateCd;          //채널2 DOOR 상태 코드
    private String ch2PlugStateCd;          //채널2 PLUG 상태 코드
    private String ch3RechgStateCd;         //채널3 충전 상태 코드
    private String ch3DoorStateCd;          //채널3 DOOR 상태 코드
    private String ch3PlugStateCd;          //채널3 PLUG 상태 코드
    private String powerboxState;           //파워박스 상태
    private int    integratedKwh;            //적산 WH
    private double dcPower;                 //DC 적산유효전력량
    private double acPower;                 //AC 적산유효전력량
    private String chgrTxDt;                //충전기 전소 일시
    private String mwKindCd;                //미들웨어 종류 코드
    private String rTimeYn;                 //실시간 여부

}
