package jdEvchgrMw.chgrInfo.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.vo.ChgrInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ class Name  : ChgrInfoServiceImpl.java
 * @ Description : 충전기 설치 정보 IMPLEMENTS
 * @ autor : 고재훈
 * @ since : 2019-11-19 10:01
 * @
 * @ Modification Information
 * @    수정일      		  수정자               수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2019-11-19                                최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

@Service("chgrInfoService")
public class ChgrInfoServiceImpl extends EgovAbstractServiceImpl implements ChgrInfoService {

	@Resource(name = "chgrInfoMapper")
	private ChgrInfoMapper chgrInfoMapper;

	/*충전기 설지 정보 UPDATE*/
	public int chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception {

		//int test = chgrInfoMapper.chgrInfoUpdate(chgrInfoVO);
		System.out.println("Update Count : " + chgrInfoMapper.chgrInfoUpdate(chgrInfoVO));
		return chgrInfoMapper.chgrInfoUpdate(chgrInfoVO);
	}

}
