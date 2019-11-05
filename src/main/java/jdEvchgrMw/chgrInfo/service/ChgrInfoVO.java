package jdEvchgrMw.chgrInfo.service;

import jdEvchgrMw.common.service.PageVO;
import lombok.Data;

@Data
public class ChgrInfoVO extends PageVO{
	
	private String chgrInfoMsg;
	private String msgSendType;
	private String msgActionType;
	private String msgData;
}
