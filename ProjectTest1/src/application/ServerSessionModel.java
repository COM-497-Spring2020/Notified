package application;

public class ServerSessionModel {
	private String sessionToken;
	private String username;
	private String password;
	private boolean sessionValid;
	
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getSessionValid() {
		return sessionValid;
	}
	public void setSessionValid(boolean sessionValid) {
		this.sessionValid = sessionValid;
	}	
}
