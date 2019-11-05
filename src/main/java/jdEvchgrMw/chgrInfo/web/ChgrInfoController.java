package jdEvchgrMw.chgrInfo.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import java.util.List;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
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
	public String c(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO,ModelMap model) throws Exception {


		/***********************페이징 SETTING 시작***********************/
		PaginationInfo paginationInfo = new PaginationInfo();
		  
		paginationInfo.setCurrentPageNo(chgrInfoVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(chgrInfoVO.getPageUnit());
		paginationInfo.setPageSize(chgrInfoVO.getPageSize());
	    paginationInfo.setCurrentPageNo(chgrInfoVO.getPageIndex());

	    chgrInfoVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	    chgrInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
	    chgrInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
						
	    
	    if(chgrInfoVO.getPageIndex() != 1)
	    	chgrInfoVO.setPageRowIndex(chgrInfoVO.getPageIndex()*10-10);
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt();	
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> chrgList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    
	    model.addAttribute("resultList", chrgList);
		return "jdEvchgrMw/chgrInfo/chgrInfoListView";
	}

	@RequestMapping(value="/chgrInfoListData.do")
	@ResponseBody
	public Object chgrInfoListData(ChgrInfoVO chgrInfoVO, Model model) throws Exception{
			  
		return chgrInfoService.chgrInfoDataList(chgrInfoVO);
	}
	
}
