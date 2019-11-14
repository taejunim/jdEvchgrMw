package jdEvchgrMw;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jdEvchgrMw.chgrInfo.service.ChgrInfoService;
import jdEvchgrMw.vo.ChgrInfoVO;

@Controller
public class JdEvChgrMwController {
	
    @Resource(name = "chgrInfoService")
    protected ChgrInfoService chgrInfoService;
	
	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * MAIN PAGE
	 * @param 
	 * @param 
	 * @return "Main"
	 */
	@RequestMapping(value = "/main.do")
	public String main(ChgrInfoVO chgrInfoVO) throws Exception {
		
		System.out.println("MAIN");
		
/*		String	data = "{\"send_type\":\"req\",\"action_type\":\"chgrInfo\",\"data\":{\"send_date\":\"201905929172029\",";
				data += "\"create_date\":\"20190529172029\",\"gps_xpos\":\"126.570667\",\"gps_ypos\":\"33.450701\",\"charger_ip\":\"127.0.0.1\",";
				data += "\"charger_port\":\"8080\",\"charger_type\":\"0\",\"plug_detl_info\":[{\"plug_id\":\"1\",\"plug_type\":\"0\"}],";
				data += "\"charger_mf\":\"C\",\"m2m_mf\":\"L\",\"rf_mf\":\"V\",\"m2m_tel\":\"01220160912\",\"van_ip\":\"127.0.0.2\",\"van_port\":\"8081\",";
				data += "\"fw_ver_info\":[{\"curr_ver\":\"JS_HMI_1905252\",\"fw_type\":\"hmi\"},{\"curr_ver\":\"JS_VCI_1905252\",\"fw_type\":\"voiceInfo\"}],";
				data += "\"offline_free_charge_W\":\"5\"}}";
		
		chgrInfoVO.setChgrInfoMsg(data);		
		
		chgrInfoService.chgrInfoDataInsert(chgrInfoVO);

		System.out.println("data: "+data);*/
		
		return "jdEvchgrMw/main";
	}
	
}
