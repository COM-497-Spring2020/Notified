package application;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerSessionActions{
	public static String sessionToken;
	private ServerSessionModel sessionModel;
	
	public void setSessionToken(String token) {
		sessionModel.setSessionToken(token);
	}
	public String getSessionToken() {
		return sessionModel.getSessionToken();
	}
	
	public static void createUser(String user, String pass) throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://67.78.159.98:9991/api/v1/create/"+user+"/"+pass+"/")).header("accept", "application/json").build();
		var response = client.send(request, BodyHandlers.ofString());
		String token = response.body(); 
		System.out.println(response.statusCode());
		System.out.println(token);
	}
	
	public static void createSession(String user, String pass) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://67.78.159.98:9991/api/v1/login/"+user+"/"+pass+"/")).header("accept", "application/json").build();
		var response = client.send(request, BodyHandlers.ofString());
		String token = response.body(); 
		System.out.println(response.statusCode());
		System.out.println(token);
		
		Pattern pattern = Pattern.compile("token\":\"(.*)\",\"last", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(token);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	      System.out.println("Match found");
	      System.out.println(matcher.group(1));
	      sessionToken = (matcher.group(1)).toString();
	      //sessionToken = setSessionToken(tokenMade);
	    } else {
	      System.out.println("Match not found");
	    }
	}
	
	public void checkSession(String token) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://67.78.159.98:9991/api/v1/list/user/")).header("accept", "application/json").build();
		var response = client.send(request, BodyHandlers.ofString());
		String watsay = response.body(); 
		System.out.println(watsay);
		System.out.println(response.statusCode());
		
		Pattern pattern = Pattern.compile("token\":\"(.*)\",\"last", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(getSessionToken());
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	      System.out.println("Match found");
	      System.out.println(matcher.group(1));
	      //sessionToken = matcher.toString();
	    } else {
	      System.out.println("Match not found");
	    }
	}
	
	public void endSession(String user) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://67.78.159.98:9991/api/v1/list/user/")).header("accept", "application/json").build();
		var response = client.send(request, BodyHandlers.ofString());
		String watsay = response.body(); 
		System.out.println(watsay);
		System.out.println(response.statusCode());
	}
}
