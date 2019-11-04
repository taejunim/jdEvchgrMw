package jdEvchgrMw.chgrInfo.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.chgrInfo.service.ChgrInfoVO;

@Controller
public class ChgrInfoController {
	
    @Resource(name = "chgrInfoService")
    protected ChgrInfoService chgrInfoService;
    
	/**
	 * CHGR INFO LIST VIEW PAGE
	 */
	@RequestMapping(value = "/chgrInfoListView.do")
	public String chgrInfoListView(ChgrInfoVO chgrInfoVO) throws Exception {

		return "jdEvchgrMw/chgrInfo/chgrInfoListView";
	}

	@RequestMapping(value="/chgrInfoListData.do")
	@ResponseBody
	public Object chgrInfoListData(ChgrInfoVO chgrInfoVO, Model model) throws Exception{
			  
		return chgrInfoService.chgrInfoDataList();
	}
	
}
