package jdEvchgrMw.vo;

import lombok.Data;

/**
 * @ Class Name  : AnnounceVO.java
 * @ Description : 공지사항 VO
 * @ author : 임태준
 * @ since : 2021-07-19 16:29
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2021-07-19                       최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Data
public class AnnounceVO {

    private String providerId;			//충전사업자 ID
    private String stId;				//충전소 ID
    private String chgrId;				//충전기 ID
    private String koDetl;              //공지사항 한글
    private String enDetl;              //공지사항 영문
    private String uuid;                //UUID
    private String resDt;               //수신 일시
}
