package jdEvchgrMw.chgrInfo.service;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import jdEvchgrMw.common.service.PageVO;
import lombok.Data;

@Data
public class ChgrInfoVO extends PageVO{
	
	private String chgrInfoMsg;
	private String msgSendType;
	private String msgActionType;
	private String msgData;
	private PaginationInfo paginationInfo;
}
