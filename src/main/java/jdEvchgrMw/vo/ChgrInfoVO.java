package jdEvchgrMw.vo;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.Data;

/**
 * @ Class Name  : ChgrInfoVO.java
 * @ Description : 충전기 설치 정보 VO
 * @ author : 임태준
 * @ since : 2019-11-14 13:29
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
public class ChgrInfoVO extends FwVerInfoVO {

	private String chgrInfoMsg;
	private String msgSendType;
	private String msgActionType;
	private String msgData;
	private PaginationInfo paginationInfo;

	private String providerId;			//충전사업자 ID
	private String stId;				//충전소 ID
	private String chgrId;				//충전기 ID
	private String chgrDispId;			//충전기 식별번호
	private String chgrNm;				//충전기 명
	private String mwKindCd;			//미들웨어 종류 코드
	private String chgrSvcComId;		//충전기 SVC 회사 ID
	private String speedTpCd;			//충전속도 구분 코드
	private String gpsXpos;				//GPS X좌표
	private String gpsYpos;				//GPS y좌표
	private String chgrTypeCd;			//충전기 타입 코드
	private String locInfo;				//설치장소 정보
	private String mfCd;				//제조사 코드
	private String m2mMfCd;				//M2M통신단말기 제조사 코드
	private String rfMfCd;				//RF카드단말기 제조사 코드
	private String m2mTel;				//M2M 전화번호
	private String vanIp;				//VAN사 IP
	private int vanPort;				//VAN사 PORT
	private String installYy;			//설치 년도
	private String useTime;				//사용 시간
	private String payingYn;			//유료 여부
	private String memshOnlyYn;			//멤버쉽 OplugTypeCdNLY 여부
	private String parkingFeeYn;		//주차비 여부
	private String parkingFeeDetl;		//주차비 상세정보
	private String rsvFuncYn;			//예약 기능 여부
	private String cableYn;				//케이블 여부
	private String showYn;				//표시 여부
	private String remark;				//비고
	private String chQty;				//채널 수량
	private int offlineFreeWh;			//OFFLINE 무료 WH
	private String mwSession;			//미들웨어 세션
	private String delYn;				//자료삭제 여부
	private String regUid;				//등록 UID
	private String modUid;				//수정 UID

	private String plugTypeCd;			//플러그 타입 코드
	private int chId;					//채널 ID
	//private String chargerPayYn;		//충전기 과금 여부

	private FwVerInfoVO fwVerInfoVO;

}
