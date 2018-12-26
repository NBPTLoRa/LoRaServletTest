package me.gacl.domain;

public class LoRaAddress {
	private String address;
	private String userID;
	private String userPWD;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPWD() {
		return userPWD;
	}
	public void setUserPWD(String userPWD) {
		this.userPWD = userPWD;
	}
	
    public String toString() {
        return userID+","+userPWD+","+address;
    }
	
}
