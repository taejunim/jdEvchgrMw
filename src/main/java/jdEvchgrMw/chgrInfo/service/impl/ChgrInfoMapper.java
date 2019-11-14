package jdEvchgrMw.chgrInfo.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.ChgrInfoVO;

@Mapper("chgrInfoMapper")
public interface ChgrInfoMapper {

	public List<EgovMap> chgrInfoDataList(ChgrInfoVO chgrInfoVO) throws Exception;
	public int chgrInfoDataListCnt(ChgrInfoVO chgrInfoVO) throws Exception;
	public void chgrInfoDataInsert(ChgrInfoVO chgrInfoVO) throws Exception;
	
}
