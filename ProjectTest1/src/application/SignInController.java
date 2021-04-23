package application;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

//mongodb imports
import org.bson.Document;

import com.example.demo.ServerSessionActions;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Filters.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInController {
	public String email;
	public String hashedPassword;
	public static String username;
	public static String userID;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
    private AnchorPane rootPane;
	
		public void start(Stage primaryStage) throws Exception {
		   
			//=================================================================================================================
			//================================ setting up MongoDB Connection ==================================================
			//=================================================================================================================
			MongoClientURI uri = new MongoClientURI(
					"mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("Notified");
		   
			Document document = new Document();
		
		}
		// Event Listener on Button.onAction
		@FXML
		public void CreateAccount(ActionEvent event) throws Exception {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewCreateAccount.fxml"));
			rootPane.getChildren().setAll(pane);}
		
		// Event Listener on Button.onAction
		@FXML
		public void SignIn(ActionEvent event) throws Exception {
			
			ServerSessionModel sessionModel = new ServerSessionModel();
			ServerSessionController sessionController = new ServerSessionController(sessionModel);
			
//			pane = null;
				// TODO Autogenerated
				if(emailField.getText().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Form Error!!");
					alert.setHeaderText(null);
					alert.setContentText("Please enter your email");
//					alert.initOwner(pane.getScene().getWindow());
					alert.show();
					return;
				}
				if(passwordField.getText().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Form Error!!");
					alert.setHeaderText(null);
					alert.setContentText("Please enter your password!");
//					alert.initOwner(pane.getScene().getWindow());
					alert.show();
					return;
				}
	
				// Check database at login to see if the user exists
				MongoClientURI uri = new MongoClientURI(
						  "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
						MongoClient mongoClient = new MongoClient(uri);
						MongoDatabase database = mongoClient.getDatabase("Notified");
				// check for documents containing what the user types in the email field and creates a count.
				MongoCollection<Document> coll = (MongoCollection<Document>) database.getCollection("Login");
				long found = coll.countDocuments(Filters.and(Filters.eq("Email", emailField.getText())));
				
				// hashes the entered password and checks if it matches the hashed password in the DB
				String password = passwordField.getText();
				this.hashedPassword = null;
				MessageDigest md;
				try {
					md = MessageDigest.getInstance("SHA-256");
					byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
					StringBuilder sb = new StringBuilder();
		            for(int i=0; i < digest.length ;i++)
		            {
		                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		            }
		            this.hashedPassword = sb.toString();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} 
				
				// creates a query that searches the DB for a document matching what the user input
				// for the email and password fields and stores the resulting document
				BasicDBObject search = new BasicDBObject().append("Email", emailField.getText()).append("Password", this.hashedPassword);
				Document result = coll.find(search).first();
				
				// if the email exists in the database -> it will check the password
				if (found > 0) {
						// if the email AND the password are correct -> login is successful
						if (result != null) {	
							//ServerSessionActions.createSession(sessionModel.getUsername(), sessionModel.getPassword());
							sessionController.setSessionToken(ServerSessionActions.createSession(sessionModel.getUsername(), sessionModel.getPassword()));
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/NewMainPage.fxml"));
							AnchorPane pane = loader.load();
							rootPane.getChildren().setAll(pane);
							MainController show = (MainController)loader.getController();;
								
							// prints the username from the document found above
							FindIterable<Document> find = coll.find(search).projection(Projections.include("Name"));
							for (Document name : find) {
								username = (String) name.get("Name");
								show.SetText();									
							}
								
							// stores the userID
							FindIterable<Document> id = coll.find(search).projection(Projections.include("UserID"));
							for (Document user : id) { 
								userID = (String) user.get("UserID");
								}
								
						// if the password is incorrect -> try again
						} else {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("");
							alert.setHeaderText(null);
							alert.setContentText("Incorrect password");
//							alert.initOwner(pane.getScene().getWindow());
							alert.show();
						}
				// if the email doesn't exist in the database -> user does not exist
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("");
					alert.setHeaderText(null);
					alert.setContentText("User does not exist. Please register.");
//					alert.initOwner(pane.getScene().getWindow());
					alert.show();
				}				
			}
		
		public String getUserName() {
			return username;
		}
		
		public String getUserID() {
			return userID.toString();
		}	
		
		// Event Listener on Button.onAction
		@FXML
		public void CloseApp(ActionEvent event) {
			// TODO Autogenerated
			Platform.exit();
			System.exit(0);
		}
	}
