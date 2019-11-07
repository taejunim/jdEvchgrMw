package jdEvchgrMw.chgrInfo.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface ChgrInfoService {

	public List<EgovMap> chgrInfoDataList(ChgrInfoVO chgrInfoVO) throws Exception;
	
	public int chgrInfoDataListCnt(ChgrInfoVO chgrInfoVO) throws Exception;
	
	public void chgrInfoDataInsert(ChgrInfoVO chgrInfoVO) throws Exception;
	
}
