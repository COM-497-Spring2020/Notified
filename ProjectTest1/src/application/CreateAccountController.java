package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.PasswordField;

public class CreateAccountController {
	@FXML
    private AnchorPane rootPane;
	@FXML
	private TextField nameField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	public static UUID userID;
	
//	private Node pane;
	public void start(Stage primaryStage) throws Exception {
		   
	       //=================================================================================================================
		   //================================ setting up MongoDB Connection ==================================================
		   //=================================================================================================================
		  MongoClientURI uri = new MongoClientURI(
			 "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
		   MongoClient mongoClient = new MongoClient(uri);
		   MongoDatabase database = mongoClient.getDatabase("Notified");
		   
		   Document document = new Document();}
	
	//this is the method to access the create user part of the api
	public void createUser(String user, String pass) throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://67.78.159.98:9991/api/v1/create/"+user+"/"+pass+"/")).header("accept", "application/json").build();
		var response = client.send(request, BodyHandlers.ofString());
		String token = response.body(); 
		System.out.println(response.statusCode());
		System.out.println(token);
	}

	// Event Listener on Button.onAction
	@FXML
	public void SignUp(ActionEvent event) throws Exception {
		ServerSessionModel sessionModel = new ServerSessionModel();
		ServerSessionController sessionController = new ServerSessionController(sessionModel);
		
			MongoClientURI uri = new MongoClientURI(
					 "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
				   MongoClient mongoClient = new MongoClient(uri);
				   MongoDatabase database = mongoClient.getDatabase("Notified");
				   Document document = new Document();
				   
		    // checks for documents containing the name entered
		    MongoCollection<Document> collec = database.getCollection("Login");
	        long name = collec.countDocuments(Filters.and(Filters.eq("Name", nameField.getText())));
	        
	        // if a document with the name entered already exists, an error is displayed.
		   	if (name == 1) {
		   		Alert alert = new Alert(Alert.AlertType.ERROR);
		   		alert.setTitle("Error");
		   		alert.setHeaderText(null);
		   		alert.setContentText("This name is taken. Please try a different name.");
//				alert.initOwner(pane.getScene().getWindow());
				alert.show();
				return;
		   	}
			if(nameField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Form Error!!");
				alert.setHeaderText(null);
				alert.setContentText("Please enter your name");
//				alert.initOwner(pane.getScene().getWindow());
				alert.show();
				return;
			} 
			if(!emailField.getText().isEmpty()) {
				String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher match = pattern.matcher(emailField.getText());
				if (match.find() == false) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Form Error!!");
					alert.setHeaderText(null);
					alert.setContentText("Please enter your email in the correct format.");
//					alert.initOwner(pane.getScene().getWindow());
					alert.show();
					return;
				}
			}
			
			if(emailField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Form Error!!");
				alert.setHeaderText(null);
				alert.setContentText("Please enter your email");
//				alert.initOwner(pane.getScene().getWindow());
				alert.show();
				return;
			}
			if(passwordField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Form Error!!");
				alert.setHeaderText(null);
				alert.setContentText("Please enter your password!");
//				alert.initOwner(pane.getScene().getWindow());
				alert.show();
				return;
			}
			
			// a controller to save tne username
			sessionController.setUsername(nameField.getText());
			
			// hash the password before adding to database
			String password = passwordField.getText();
			String hashedPassword = null;
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-256");
				byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
				StringBuilder sb = new StringBuilder();
	            for(int i=0; i < digest.length ;i++)
	            {
	                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            hashedPassword = sb.toString();
	            sessionController.setPassword(sb.toString());
	            //tried running it in here too
	            //createUser(sessionController.getUsername(), hashedPassword);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			// a controller to put session as false = not logged in
			sessionController.setSessionValid(false);
			
			// create a unique user id
			userID = UUID.randomUUID();
							
			// appending the value retrieved at pressing the button
			document.append("Name", nameField.getText());
			document.append("Email", emailField.getText());
			document.append("Password", hashedPassword);
			document.append("UserID", userID.toString());
			//document.append("Password", passField.getText());
			createUser(nameField.getText(), hashedPassword);
			
			// tried to call the class and run the method 
			//ServerSessionActions.createUser(nameField.getText(), hashedPassword);
			
			Document found = database.getCollection("Login").find(document).first();
			
			if (found == null) {
			    database.getCollection("Login").insertOne(document);
			}
			
			MongoCollection<Document> coll = database.getCollection("Login");
			
			// Retrieving the documents - prints the document on console to show it was created
			FindIterable<Document> collection = coll.find();
			MongoCursor<Document> cursor = collection.iterator(); // (2)
			try {
				while(cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			} finally {
				cursor.close();
			}
			
			// alert that fields have inputs and goes back to login page
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Registered!");
			alert.setHeaderText(null);
			alert.setContentText("Info recieved");
//			alert.initOwner(pane.getScene().getWindow());
//			alert.showAndWait().ifPresent(response -> {
//			     if (response == ButtonType.OK) {
////			    	 primaryStage.setScene(scene);
//			     }
//			});

			AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewSignIn.fxml"));
			rootPane.getChildren().setAll(pane);
		};	
	
	   public void BackToLogin(ActionEvent event) throws Exception{
		   AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewSignIn.fxml"));
			rootPane.getChildren().setAll(pane);}
	   
	   public String getUserID() {
		   return userID.toString();
	   }
	 
	// Event Listener on Button.onAction
	@FXML
	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
}
