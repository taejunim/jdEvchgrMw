package jdEvchgrMw.chgrInfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.chgrInfo.service.ChgrInfoVO;

@Service("chgrInfoService")
public class ChgrInfoServiceImpl extends EgovAbstractServiceImpl implements ChgrInfoService {

	@Resource(name = "chgrInfoMapper")
	private ChgrInfoMapper chgrInfoMapper;
	
	public List<EgovMap> chgrInfoDataList() throws Exception {
		
		return chgrInfoMapper.chgrInfoDataList();
	}
	
	public void chgrInfoDataInsert(ChgrInfoVO chgrInfoVO) throws Exception {
		
		chgrInfoMapper.chgrInfoDataInsert(chgrInfoVO);
	}
	
}
