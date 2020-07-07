package jdEvchgrMw.vo;

import lombok.Data;

import javax.websocket.Session;
import java.util.ArrayList;

@Data
public class CommonVO {

	private Session userSession;	//USER SESSION
	private String providerId;		//사업자 ID
	private String stationId;		//STATION ID
	private String chgrId;			//CHGR ID
	private String rcvMsg;			//REV
	private String resMsg;			//RESPONSE MESSAGE
	private String uuid;			//uuid
	private String sendType;		//SEND TYPE
	private String actionType;		//ACTION TYPE
	private String data;			//DATA
	private String sendDate;		//전송일시 - YYYYMMDDhhmmss
	private String createDate;		//데이터 생성 일시
	private String rTimeYn;         //실시간 여부
	private String recvLogId;       //수신 로그 ID

	private String responseDate;	//응답일시 - YYYYMMDDhhmmss
	//private String reqCreateDate;	//정보생성일시
	private String responseReceive;	//응답코드 - 0:실패 1:성공
	private String responseReason;	//응답사유코드 - 11:필수항목누락, 12:파라미터오류, 13:충전소(기)ID오류, 14:데이터형식오류, 15:내부오류 *성공인 경우 공란으로 설정

	private String ctrlListId;		//제어이력 ID
	private String costSdd;			//요금적용시작일자 - YYYYMMDD
	private String price;			//단가

	private ChangeModeVO changeModeVO;		//충전기 모드 변경 VO
	private HourVO hourVO; 					//시간 VO
	private FwVerInfoVO fwVerInfoVO;		//충전기 버전 VO
	private UserVO userVO;					//사용자 인증 VO
	private BusUserVO busUserVO;			//버스 사용자 인증 VO
	private DeviceConfigVO deviceConfigVO;	//충전기 사운드 및 밝기 VO

	private ArrayList<ControlChgrVO> controlChgrVOArrayList = new ArrayList<>();	//관제에서 설정한 단가 리스트
}
