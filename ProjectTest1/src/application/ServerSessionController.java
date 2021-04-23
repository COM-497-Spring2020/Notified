package application;

import com.example.demo.ServerSessionModel;

public class ServerSessionController {
	private ServerSessionModel sessionModel;
	
	public ServerSessionController(ServerSessionModel sessionModel) {
		this.sessionModel = sessionModel;
	}
	
	public void setSessionToken(String token) {
		sessionModel.setSessionToken(token);
	}
	public String getSessionToken() {
		return sessionModel.getSessionToken();
	}
	
	public void setUsername(String username) {
		sessionModel.setUsername(username);
	}
	public String getUsername() {
		return sessionModel.getUsername();
	}
	
	public void setPassword(String password) {
		sessionModel.setPassword(password);
	}
	public String getPassword() {
		return sessionModel.getPassword();
	}
	
	public void setSessionValid(boolean status) {
		sessionModel.setSessionValid(status);
	}
	public boolean getSessoinValid() {
		return sessionModel.getSessionValid();
	}
}
