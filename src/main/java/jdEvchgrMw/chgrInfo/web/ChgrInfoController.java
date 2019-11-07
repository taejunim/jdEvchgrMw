package jdEvchgrMw.chgrInfo.web;

import javax.annotation.Resource;

import jdEvchgrMw.common.CollectServiceBean;
import jdEvchgrMw.vo.CommonVO;
import jdEvchgrMw.vo.FwVerInfoVO;
import jdEvchgrMw.vo.PlugDetlInfoVO;
import jdEvchgrMw.vo.chgrInfoVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.chgrInfo.service.ChgrInfoVO;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChgrInfoController {
	
    @Resource(name = "chgrInfoService")
    protected ChgrInfoService chgrInfoService;
    
	/**
	 * CHGR INFO LIST VIEW PAGE
	 */
	@RequestMapping(value = "/chgrInfoListView.do")
	public String chgrInfoListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chgrInfo");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);										//출고 목록 조회 TOTAL CNT CALL	
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chgrInfo/chgrInfoListView";
	}
	@RequestMapping(value = "/alarmHistoryListView.do")
	public String alarmHistoryListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("alarmHistory");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);						
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/alarmHistory/alarmHistoryListView";
	}
	@RequestMapping(value = "/askVerListView.do")
	public String askVerListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("askVer");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);							
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/askVer/askVerListView";
	}
	@RequestMapping(value = "/changeModeListView.do")
	public String changeModeListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("changeMode");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);									
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/changeMode/changeModeListView";
	}
	@RequestMapping(value = "/chargePavmentListView.do")
	public String chargePavmentListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chargePavment");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chargePavment/chargePavmentListView";
	}
	@RequestMapping(value = "/chargingEndListView.do")
	public String chargingEndListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chargingEnd");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chargingEnd/chargingEndListView";
	}
	@RequestMapping(value = "/chargingInfoListView.do")
	public String chargingInfoListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chargingInfo");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chargingInfo/chargingInfoListView";
	}
	@RequestMapping(value = "/chargingStartListView.do")
	public String chargingStartListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chargingStart");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chargingStart/chargingStartListView";
	}
	@RequestMapping(value = "/chgrStatusListView.do")
	public String chgrStatusListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("chgrStatus");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/chgrStatus/chgrStatusListView";
	}
	@RequestMapping(value = "/dAlarmHistoryListView.do")
	public String dAlarmHistoryListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dAlarmHistory");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dAlarmHistory/dAlarmHistoryListView";
	}
	@RequestMapping(value = "/dChargePavmentListView.do")
	public String dChargePavmentListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dChargePavment");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dChargePavment/dChargePavmentListView";
	}
	@RequestMapping(value = "/dChargingEndListView.do")
	public String dChargingEndListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dChargingEnd");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dChargingEnd/dChargingEndListView";
	}
	@RequestMapping(value = "/dChargingInfoListView.do")
	public String dChargingInfoListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dChargingInfo");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dChargingInfo/dChargingInfoListView";
	}
	@RequestMapping(value = "/dChargingStartListView.do")
	public String dChargingStartListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dChargingStart");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dChargingStart/dChargingStartListView";
	}
	@RequestMapping(value = "/dChgrStatusListView.do")
	public String dChgrStatusListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dChgrStatus");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dChgrStatus/dChgrStatusListView";
	}
	@RequestMapping(value = "/displayBrightnessListView.do")
	public String displayBrightnessListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("displayBrightness");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/displayBrightness/displayBrightnessListView";
	}
	@RequestMapping(value = "/dReportUpdateListView.do")
	public String dReportUpdateListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("dReportUpdate");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/dReportUpdate/dReportUpdateListView";
	}
	@RequestMapping(value = "/notifyVerUpgradeListView.do")
	public String notifyVerUpgradeListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("notifyVerUpgrade");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/notifyVerUpgrade/notifyVerUpgradeListView";
	}
	@RequestMapping(value = "/pricesListView.do")
	public String pricesListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("prices");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/prices/pricesListView";
	}
	@RequestMapping(value = "/reportUpateListView.do")
	public String reportUpdateListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("reportUpate");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/reportUpdate/reportUpdateListView";
	}
	@RequestMapping(value = "/resetListView.do")
	public String resetListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("reset");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/reset/resetListView";
	}
	@RequestMapping(value = "/sendSmsListView.do")
	public String sendSmsListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("sendSms");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/sendSms/sendSmsListView";
	}
	@RequestMapping(value = "/soundListView.do")
	public String soundListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("sound");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/sound/soundListView";
	}
	@RequestMapping(value = "/userListView.do")
	public String userListView(@ModelAttribute("chgrInfoVO") ChgrInfoVO chgrInfoVO, ModelMap model) throws Exception {

		
		chgrInfoVO.setMsgActionType("user");
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
	   
	    int totCnt = chgrInfoService.chgrInfoDataListCnt(chgrInfoVO);
	    paginationInfo.setTotalRecordCount(totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	    /***********************페이징 SETTING 끝***********************/

	    List<EgovMap> resList = chgrInfoService.chgrInfoDataList(chgrInfoVO);
	    
	    model.addAttribute("resultList", resList);
		
		
		return "jdEvchgrMw/user/userListView";
	}
	@RequestMapping(value="/chgrInfoListData.do")
	@ResponseBody
	public Object chgrInfoListData(ChgrInfoVO chgrInfoVO, Model model) throws Exception{

		chgrInfoParsingData();	//파싱하는거 샘플 추후 수정 예정
		//chgrStatusParsingData();
		//userParsingData();
		//chargingStartParsingData();
		//chargingInfoParsingData();
		//chargingEndParsingData();
		//chargePaymentParsingData();
		//sendSmsParsingData();
		//alarmHistoryParsingData();
		//reportUpateParsingData();

		//dChargingStartParsingData();
		//dChargingEndParsingData();
		//dChargePaymentParsingData();
		//dAlaramHistoryParsingData();
		//dReportUpdateParsingData();

		return chgrInfoService.chgrInfoDataList(chgrInfoVO);
	}

	private void chgrInfoParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chgrInfo");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");
		data.put("gps_xpos", "126.570667");
		data.put("gps_ypos", "33.450701");
		data.put("charger_ip", "127.0.0.1");
		data.put("charger_port", "8080");
		data.put("charger_type", "0");

		JSONObject plug_detl_info_data = new JSONObject();

		plug_detl_info_data.put("plug_id", "1");
		plug_detl_info_data.put("plug_type", "0");

		JSONArray plug_detl_info_data_array = new JSONArray();
		plug_detl_info_data_array.add(plug_detl_info_data);

		data.put("plug_detl_info", plug_detl_info_data_array);

		data.put("charger_mf", "C");
		data.put("m2m_mf", "L");
		data.put("rf_mf", "V");
		data.put("m2m_tel", "01220160912");
		data.put("van_ip", "127.0.0.2");
		data.put("van_port", "8081");



		JSONArray fw_ver_info_array = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject fw_ver_info_data = new JSONObject();

			String curr_ver = "";
			String fw_type = "";

			if (i == 0) {
				curr_ver = "JS_HMI_1905252";
				fw_type = "hmi";
			} else if (i == 1) {
				curr_ver = "JS_VCI_1905252";
				fw_type = "voiceInfo";
			}
			fw_ver_info_data.put("curr_ver", curr_ver);
			fw_ver_info_data.put("fw_type", fw_type);

			fw_ver_info_array.add(fw_ver_info_data);
		}

		data.put("fw_ver_info", fw_ver_info_array);
		data.put("offline_free_charge_W", "5");


		JSONArray req_array = new JSONArray();
		req_array.add(data);

		jsonObject.put("data", req_array);

		System.out.println("만든 JSON : " + jsonObject.toString());
		System.out.println("data : " + data.toString());

		String send_date = (String) data.get("send_date");
		String create_date = (String) data.get("create_date");
		String gps_xpos = (String) data.get("gps_xpos");
		String gps_ypos = (String) data.get("gps_ypos");
		String charger_ip = (String) data.get("charger_ip");
		String charger_port = (String) data.get("charger_port");
		String charger_type = (String) data.get("charger_type");

		String charger_mf = (String) data.get("charger_mf");
		String m2m_mf = (String) data.get("m2m_mf");
		String rf_mf = (String) data.get("rf_mf");
		String m2m_tel = (String) data.get("m2m_tel");
		String van_ip = (String) data.get("van_ip");
		String van_port = (String) data.get("van_port");
		String offline_free_charge_W = (String) data.get("offline_free_charge_W");

		ArrayList<FwVerInfoVO> fwVerInfoVOList = new ArrayList<>();

		JSONArray fwVerInfoArr = (JSONArray) data.get("fw_ver_info");

		for (int i=0; i<fwVerInfoArr.size(); i++) {
			JSONObject tmp = (JSONObject) fwVerInfoArr.get(i);

			String curr_ver = (String) tmp.get("curr_ver");
			String fw_type = (String) tmp.get("fw_type");

			FwVerInfoVO fwVerInfoVO = new FwVerInfoVO();
			fwVerInfoVO.setCurr_ver(curr_ver);
			fwVerInfoVO.setFw_type(fw_type);
			fwVerInfoVOList.add(fwVerInfoVO);
		}

		ArrayList<PlugDetlInfoVO> plugDetlInfoVOList = new ArrayList<>();

		JSONArray plugDetlInfoArr = (JSONArray) data.get("plug_detl_info");

		for (int i=0; i<plugDetlInfoArr.size(); i++) {
			JSONObject tmp = (JSONObject) plugDetlInfoArr.get(i);

			String plug_id = (String) tmp.get("plug_id");
			String plug_type = (String) tmp.get("plug_type");

			PlugDetlInfoVO plugDetlInfoVO = new PlugDetlInfoVO();
			plugDetlInfoVO.setPlug_id(plug_id);
			plugDetlInfoVO.setPlug_type(plug_type);
			plugDetlInfoVOList.add(plugDetlInfoVO);
		}

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chgrInfo");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("price_apply_yn", "1");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		JSONArray unit_prices_array = new JSONArray();

		JSONObject unit_prices_data = new JSONObject();

		for (int i=0; i<24; i++) {

			String key;
			String value = String.valueOf(i);

			if (i < 10) {
				key = "h0" + i;
			} else {
				key = "h" + i;
			}

			unit_prices_data.put(key, value);
		}

		unit_prices_array.add(unit_prices_data);

		sendData.put("unit_prices", unit_prices_array);


		JSONArray display_brightness_array = new JSONArray();

		JSONObject display_brightness_data = new JSONObject();

		for (int i=0; i<24; i++) {

			String key;

			if (i < 10) {
				key = "h0" + i;
			} else {
				key = "h" + i;
			}

			display_brightness_data.put(key, "100");
		}

		display_brightness_array.add(display_brightness_data);

		sendData.put("display_brightness", display_brightness_array);


		JSONArray sound_array = new JSONArray();

		JSONObject sound_data = new JSONObject();

		for (int i=0; i<24; i++) {

			String key;
			String value = String.valueOf(i);

			if (i < 10) {
				key = "h0" + i;
			} else {
				key = "h" + i;
			}

			sound_data.put(key, value);
		}

		sound_array.add(sound_data);

		sendData.put("sound", sound_array);

		JSONArray send_array = new JSONArray();
		send_array.add(sendData);

		sendJsonObject.put("data", send_array);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전기 상태정보
	private void chgrStatusParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chgrStatus");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("mode", "1");
		data.put("integrated_power", "14562");
		data.put("powerbox", "3330000000000000");

		JSONObject charger_plug_data = new JSONObject();

		charger_plug_data.put("plug_id", "1");
		charger_plug_data.put("charging_state", "1");
		charger_plug_data.put("plug_door", "1");
		charger_plug_data.put("plug_state", "1");

		JSONArray charger_plug_data_array = new JSONArray();
		charger_plug_data_array.add(charger_plug_data);

		data.put("charger_plug", charger_plug_data_array);

		JSONArray req_array = new JSONArray();
		req_array.add(data);

		jsonObject.put("data", req_array);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chgrStatus");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//사용자인증
	private void userParsingData() {
		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "user");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("card_num", "2100147845698523");

		JSONArray req_array = new JSONArray();
		req_array.add(data);

		jsonObject.put("data", req_array);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "user");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");

		sendData.put("card_num", "2100147845698523");
		sendData.put("valid", "1");
		sendData.put("pay_yn", "N");
		sendData.put("charger_pay_yn", "Y");
		sendData.put("member_company", "ME");
		sendData.put("current_unit_cost", "173.8");

		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전 시작 정보
	private void chargingStartParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chargingStart");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("start_date", "20190529172029");
		data.put("card_num", "2100147845698523");
		data.put("credit_trx_no", "2100147809542587");
		data.put("credit_trx_date", "20190529172011");
		data.put("pay_type", "Y");
		data.put("charger_pay_yn", "Y");
		data.put("charge_plug_type", "2");
		data.put("plug_id", "2");
		data.put("integrated_power", "14562");
		data.put("prepayment", "3000");
		data.put("current_V", "40");
		data.put("current_A", "20");
		data.put("estimated_charge_time", "2400");

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chargingStart");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전 진행 정보
	private void chargingInfoParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chargingInfo");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("start_date", "20190529172029");
		data.put("card_num", "2100147845698523");
		data.put("credit_trx_no", "2100147809542587");
		data.put("credit_trx_date", "20190529172011");
		data.put("pay_type", "Y");
		data.put("charger_pay_yn", "Y");
		data.put("charge_plug_type", "2");
		data.put("plug_id", "2");
		data.put("integrated_power", "14562");
		data.put("prepayment", "3000");
		data.put("current_V", "40");
		data.put("current_A", "20");
		data.put("estimated_charge_time", "2400");
		data.put("charge_W", "12");
		data.put("charge_cost", "200");

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chargingInfo");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전 완료 정보
	private void chargingEndParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chargingEnd");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("start_date", "20190529172029");
		data.put("card_num", "");
		data.put("credit_trx_no", "2100147809542587");
		data.put("credit_trx_date", "20190529172011");
		data.put("pay_type", "Y");
		data.put("charger_pay_yn", "Y");
		data.put("charge_plug_type", "2");
		data.put("plug_id", "2");
		data.put("integrated_power", "14562");
		data.put("prepayment", "3000");
		data.put("current_V", "40");
		data.put("current_A", "20");
		data.put("charge_time", "1200");
		data.put("charge_W", "12");
		data.put("charge_end_type", "3");
		data.put("end_date", "20190529174035,");
		data.put("charge_cost", "2000");
		data.put("cancel_cost", "1000");


		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chargingEnd");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전 결제 정보
	private void chargePaymentParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "chargePayment");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("prepayment_trx_no", "2019052917202900,");
		data.put("credit_trx_no", "2100147809542587");
		data.put("credit_trx_date", "20190529172011");
		data.put("payment_type", "0");
		data.put("charge_plug_type", "0");
		data.put("plug_id", "1");
		data.put("payment_amount", "2000");

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chargePayment");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//문자전송
	private void sendSmsParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "sendSms");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("plug_id", "2");
		data.put("charge_cost", "2000");
		data.put("charge_time", "1200");
		data.put("charge_W", "12");
		data.put("msg_kind", "2");
		data.put("phone_no", "01009542587");

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "sendSms");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//경보이력
	private void alarmHistoryParsingData() {
		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "alarmHistory");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("alarm_type", "0");
		data.put("alaram_date", "20190530110840");
		data.put("alarm_code", "11");

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "alarmHistory");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//충전기 펌웨어 Update 결과 알림
	private void reportUpateParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "reportUpdate");

		JSONObject data = new JSONObject();
		data.put("send_date", "201905929172029");
		data.put("create_date", "20190529172029");

		data.put("update_result", "1");

		JSONArray fw_ver_info_array = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject fw_ver_info_data = new JSONObject();

			String curr_ver = "";
			String fw_type = "";

			if (i == 0) {
				curr_ver = "JS_HMI_1910252";
				fw_type = "hmi";
			} else if (i == 1) {
				curr_ver = "JS_VCI_1910252";
				fw_type = "voiceInfo";
			}
			fw_ver_info_data.put("curr_ver", curr_ver);
			fw_ver_info_data.put("fw_type", fw_type);

			fw_ver_info_array.add(fw_ver_info_data);
		}

		data.put("fw_ver_info", fw_ver_info_array);

		jsonObject.put("data", data);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "reportUpdate");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//덤프_충전 시작 정보
	private void dChargingStartParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "dChargingStart");

		JSONArray dJsonArray = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject data = new JSONObject();

			if (i ==0 ) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("start_date", "20190529172029");
				data.put("card_num", "2100147845698523");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("pay_type", "Y");
				data.put("charger_pay_yn", "Y");
				data.put("charge_plug_type", "2");
				data.put("plug_id", "2");
				data.put("integrated_power", "14562");
				data.put("prepayment", "3000");
				data.put("current_V", "40");
				data.put("current_A", "20");
				data.put("estimated_charge_time", "2400");
				data.put("charge_W", "12");
				data.put("charge_cost", "200");
			} else if (i == 1) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("start_date", "20190529172029");
				data.put("card_num", "2100147845698523");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("pay_type", "Y");
				data.put("charger_pay_yn", "Y");
				data.put("charge_plug_type", "2");
				data.put("plug_id", "2");
				data.put("integrated_power", "14562");
				data.put("prepayment", "3000");
				data.put("current_V", "40");
				data.put("current_A", "20");
				data.put("estimated_charge_time", "2400");
				data.put("charge_W", "12");
				data.put("charge_cost", "200");
			}

			dJsonArray.add(data);
		}

		jsonObject.put("data", dJsonArray);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "dChargingStart");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//덤프_충전 완료 정보
	private void dChargingEndParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "dChargingEnd");

		JSONArray dJsonArray = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject data = new JSONObject();

			if (i ==0 ) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("start_date", "20190529172029");
				data.put("card_num", "");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("pay_type", "Y");
				data.put("charger_pay_yn", "Y");
				data.put("charge_plug_type", "2");
				data.put("plug_id", "2");
				data.put("integrated_power", "14562");
				data.put("prepayment", "3000");
				data.put("current_V", "40");
				data.put("current_A", "20");
				data.put("charge_time", "1200");
				data.put("charge_W", "12");
				data.put("charge_end_type", "3");
				data.put("end_date", "20190529174035,");
				data.put("charge_cost", "2000");
				data.put("cancel_cost", "1000");
			} else if (i == 1) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("start_date", "20190529172029");
				data.put("card_num", "");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("pay_type", "Y");
				data.put("charger_pay_yn", "Y");
				data.put("charge_plug_type", "2");
				data.put("plug_id", "2");
				data.put("integrated_power", "14562");
				data.put("prepayment", "3000");
				data.put("current_V", "40");
				data.put("current_A", "20");
				data.put("charge_time", "1200");
				data.put("charge_W", "12");
				data.put("charge_end_type", "3");
				data.put("end_date", "20190529174035,");
				data.put("charge_cost", "2000");
				data.put("cancel_cost", "1000");
			}

			dJsonArray.add(data);
		}

		jsonObject.put("data", dJsonArray);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "dChargingEnd");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//덤프_충전 결제 정보
	private void dChargePaymentParsingData() {
		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "dChargePayment");

		JSONArray dJsonArray = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject data = new JSONObject();

			if (i ==0 ) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("prepayment_trx_no", "2019052917202900,");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("payment_type", "0");
				data.put("charge_plug_type", "0");
				data.put("plug_id", "1");
				data.put("payment_amount", "2000");
			} else if (i == 1) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("prepayment_trx_no", "2019052917202900,");
				data.put("credit_trx_no", "2100147809542587");
				data.put("credit_trx_date", "20190529172011");
				data.put("payment_type", "0");
				data.put("charge_plug_type", "0");
				data.put("plug_id", "1");
				data.put("payment_amount", "2000");
			}

			dJsonArray.add(data);
		}

		jsonObject.put("data", dJsonArray);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "chargePayment");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//덤프_경보이력
	private void dAlaramHistoryParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "dAlarmHistory");

		JSONArray dJsonArray = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject data = new JSONObject();

			if (i ==0 ) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("alarm_type", "0");
				data.put("alaram_date", "20190530110840");
				data.put("alarm_code", "11");
			} else if (i == 1) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("alarm_type", "0");
				data.put("alaram_date", "20190530110840");
				data.put("alarm_code", "11");
			}

			dJsonArray.add(data);
		}

		jsonObject.put("data", dJsonArray);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "dAlarmHistory");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	//덤프_펌웨어 Update 결과 알림
	private void dReportUpdateParsingData() {

		CollectServiceBean csb = new CollectServiceBean();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("send_type", "req");
		jsonObject.put("action_type", "dReportUpdate");


		JSONArray dJsonArray = new JSONArray();

		for (int i=0; i<2; i++) {

			JSONObject data = new JSONObject();

			if (i ==0 ) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("update_result", "1");


				JSONArray fw_ver_info_array = new JSONArray();

				for (int j=0; j<2; j++) {

					JSONObject fw_ver_info_data = new JSONObject();

					String curr_ver = "";
					String fw_type = "";

					if (j == 0) {
						curr_ver = "JS_HMI_1910252";
						fw_type = "hmi";
					} else if (j == 1) {
						curr_ver = "JS_VCI_1910252";
						fw_type = "voiceInfo";
					}
					fw_ver_info_data.put("curr_ver", curr_ver);
					fw_ver_info_data.put("fw_type", fw_type);

					fw_ver_info_array.add(fw_ver_info_data);
				}

				data.put("fw_ver_info", fw_ver_info_array);


			} else if (i == 1) {
				data.put("send_date", "201905929172029");
				data.put("create_date", "20190529172029");

				data.put("update_result", "1");


				JSONArray fw_ver_info_array = new JSONArray();

				for (int j=0; j<2; j++) {

					JSONObject fw_ver_info_data = new JSONObject();

					String curr_ver = "";
					String fw_type = "";

					if (j == 0) {
						curr_ver = "JS_HMI_1910252";
						fw_type = "hmi";
					} else if (j == 1) {
						curr_ver = "JS_VCI_1910252";
						fw_type = "voiceInfo";
					}
					fw_ver_info_data.put("curr_ver", curr_ver);
					fw_ver_info_data.put("fw_type", fw_type);

					fw_ver_info_array.add(fw_ver_info_data);
				}

				data.put("fw_ver_info", fw_ver_info_array);

			}

			dJsonArray.add(data);
		}

		jsonObject.put("data", dJsonArray);

		System.out.println("만든 JSON : " + jsonObject.toString());

		String reqData = jsonObject.toString();

		try {
			JSONParser jParser = new JSONParser();
			JSONObject jObject = (JSONObject) jParser.parse(reqData);

			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();

			chgrInfoVO.setMsgSendType(jObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(jObject.get("action_type").toString());
			chgrInfoVO.setMsgData(jObject.get("data").toString());

			System.out.println("chgrInfoVO : " + chgrInfoVO);
			System.out.println("chgrInfoVO.toString() : " + chgrInfoVO.toString());


			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//받기 끝

		//보내기 시작
		JSONObject sendJsonObject = new JSONObject();

		sendJsonObject.put("send_type", "res");
		sendJsonObject.put("action_type", "dReportUpdate");

		JSONObject sendData = new JSONObject();
		sendData.put("station_id", "JD000001");
		sendData.put("chgr_id", "01");
		sendData.put("response_date", "201905929172029");
		sendData.put("req_create_date", "20190529172029");
		sendData.put("response_receive", "1");
		sendData.put("response_reason", "");

		sendJsonObject.put("data", sendData);

		System.out.println("보낼 JSON : " + sendJsonObject);

		try {
			ChgrInfoVO chgrInfoVO = new ChgrInfoVO();
			chgrInfoVO.setMsgSendType(sendJsonObject.get("send_type").toString());
			chgrInfoVO.setMsgActionType(sendJsonObject.get("action_type").toString());
			chgrInfoVO.setMsgData(sendJsonObject.get("data").toString());

			csb.beanChgrInfoService().chgrInfoDataInsert(chgrInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//보내기 끝
	}

	private void resetParsingData() {

	}

	private void pricesParsingData() {

	}

	private void changeModeParsingData() {

	}

	private void displayBrightnessParsingData() {

	}

	private void soundParsingData() {

	}

	private void askVerParsingData() {

	}

	private void notifyVerUpgradeParsingData() {

	}

}
