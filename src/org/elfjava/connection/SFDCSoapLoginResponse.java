package org.elfjava.connection;

public class SFDCSoapLoginResponse {
	//this class is going to be turned into an XMLBean for building a response object in the LoginResponse Feature
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	private String sessionId;
	
}
