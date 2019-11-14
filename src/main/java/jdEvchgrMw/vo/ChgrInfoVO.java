package jdEvchgrMw.vo;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import jdEvchgrMw.common.service.PageVO;
import lombok.Data;

/**
 * @ Class Name  : ChgrInfoVO.java
 * @ Description : 충전기 설치 정보 VO
 * @ autor : 임태준
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
public class ChgrInfoVO extends PageVO{
	
	private String chgrInfoMsg;
	private String msgSendType;
	private String msgActionType;
	private String msgData;
	private PaginationInfo paginationInfo;


}
