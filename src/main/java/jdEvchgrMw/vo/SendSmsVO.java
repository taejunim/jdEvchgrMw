package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : SendSmsVO.java
 * @ Description : 문자 전송 VO
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
public class SendSmsVO {

    public String msgSndListId;         //전문송신이력ID
    public String providerId;           //충전사업자 ID
    public String stId;                 //충전소 ID
    public String chgrId;               //충전기 ID
    public int chId;                    //채널 ID
    public String msgKindCd;            //전문 종류 코드
    public String tel;                  //전화번호
    public String rechgTime;            //충전시간
    public int rechgWh;                 //충전 WH
    public int rehcgAmt;                //충전 금액
    public String dataCreateDt;         //데이터 생성 일시
    public String chgrTxDt;             //충전기 전송 일시
    public String regDt;                //등록 일시

}