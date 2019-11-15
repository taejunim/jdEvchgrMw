package jdEvchgrMw.chgrInfo.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import jdEvchgrMw.vo.ChgrInfoVO;

public interface ChgrInfoService {

    /*충전기 설지 정보 UPDATE*/
    public void chgrInfoUpdate(ChgrInfoVO chgrInfoVO) throws Exception;
}
