package jdEvchgrMw.vo;

import javax.websocket.Session;

public class CommonVO {

	private Session userSession;							//USER SESSION
	private String stationId;								//STATION ID
	private String chgrId;									//CHGR ID
	private String rcvMsg;									//REV
	private String sndMsg;									//SEND MESSAGE
	private String actionType;								//ACTION TYPE
	private String sendType;								//SEND TYPE
	private String data;									//DATA

	public Session getUserSession() {
		return userSession;
	}
	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}
	public String getChgrId() {
		return chgrId;
	}
	public void setChgrId(String chgrId) {
		this.chgrId = chgrId;
	}
	public String getRcvMsg() {
		return rcvMsg;
	}
	public void setRcvMsg(String rcvMsg) {
		this.rcvMsg = rcvMsg;
	}
	public String getSndMsg() {
		return sndMsg;
	}
	public void setSndMsg(String sndMsg) {
		this.sndMsg = sndMsg;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
}
