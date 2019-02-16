package me.gacl.domain;

public class instruction {
	private String insID;
	private String userID;
	private String operationToken;
	private String createTime;
	private String isFin;
	private String hwOpt;
	private String req;
	private String devEui;
	private String t;
	private String st;
	private String et;
	
	public String getInsID() {
		return insID;
	}
	public void setInsID(String insID) {
		this.insID = insID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getOperationToken() {
		return operationToken;
	}
	public void setOperationToken(String operationToken) {
		this.operationToken = operationToken;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsFin() {
		return isFin;
	}
	public void setIsFin(String isFin) {
		this.isFin = isFin;
	}
	public String getHwOpt() {
		return hwOpt;
	}
	public void setHwOpt(String hwOpt) {
		this.hwOpt = hwOpt;
	}
	public String getReq() {
		return req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	public String getDevEui() {
		return devEui;
	}
	public void setDevEui(String devEui) {
		this.devEui = devEui;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getEt() {
		return et;
	}
	public void setEt(String et) {
		this.et = et;
	}
	
    public String toString() {
        return insID;
    }
}
