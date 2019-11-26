package jdEvchgrMw.vo;

import lombok.Data;

import javax.websocket.Session;

@Data
public class CommonVO {

	private Session userSession;	//USER SESSION
	private String stationId;		//STATION ID
	private String chgrId;			//CHGR ID
	private String rcvMsg;			//REV
	private String sndMsg;			//SEND MESSAGE
	private String uuid;			//uuid
	private String sendType;		//SEND TYPE
	private String actionType;		//ACTION TYPE
	private String data;			//DATA

	private String responseDate;	//응답일시 - YYYYMMDDhhmmss
	//private String reqCreateDate;	//정보생성일시
	private String responseReceive;	//응답코드 - 0:실패 1:성공
	private String responseReason;	//응답사유코드 - 11:필수항목누락, 12:파라미터오류, 13:충전소(기)ID오류, 14:데이터형식오류, 15:내부오류 *성공인 경우 공란으로 설정

	private String ctrlListId;		//제어이력 ID

	private ChangeModeVO changeModeVO;	//충전기 모드 변경 VO
	private HourVO hourVO; 				//시간 VO
	private FwVerInfoVO fwVerInfoVO;	//충전기 버전 VO
}
