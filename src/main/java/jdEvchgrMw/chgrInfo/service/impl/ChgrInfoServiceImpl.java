package jdEvchgrMw.chgrInfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.vo.ChgrInfoVO;

@Service("chgrInfoService")
public class ChgrInfoServiceImpl extends EgovAbstractServiceImpl implements ChgrInfoService {

	@Resource(name = "chgrInfoMapper")
	private ChgrInfoMapper chgrInfoMapper;

	/*충전기 설지 정보 UPDATE*/
	public void chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

		chgrInfoMapper.chgrInfoUpdate(chgrInfoVO);
	}
}
