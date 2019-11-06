package jdEvchgrMw.vo;

import lombok.Data;

import javax.websocket.Session;

@Data
public class CommonVO {

	private Session userSession;							//USER SESSION
	private String stationId;								//STATION ID
	private String chgrId;									//CHGR ID
	private String rcvMsg;									//REV
	private String sndMsg;									//SEND MESSAGE
	private String actionType;								//ACTION TYPE
	private String sendType;								//SEND TYPE
	private String data;									//DATA
}
