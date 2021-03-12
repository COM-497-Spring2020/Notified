package application;

//mongodb imports
import org.bson.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.PasswordField;

public class SignInController {
	public String email;
	public String hashedPassword;
	public String who;
	public static String username;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	
		public void start(Stage primaryStage) throws Exception {
		   
		//=================================================================================================================
		//================================ setting up MongoDB Connection ==================================================
		//=================================================================================================================
		MongoClientURI uri = new MongoClientURI(
	      "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Notified");
		   
		Document document = new Document();}
		// Event Listener on Button.onAction
		@FXML
		public void CreateAccount(ActionEvent event) throws Exception {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/CreateAccount.fxml"));
				Scene scene = new Scene(root,500,300);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				scene.setFill(Color.TRANSPARENT);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.setScene(scene);
				primaryStage.show();}
		
		// Event Listener on Button.onAction
		@FXML
		public void SignIn(ActionEvent event) throws Exception {
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
				
				// This is where I want to check the database at login
				// hopefully an if else
				// if this email exists, go to -> if the password is correct, login alert success!, else incorrect password alert
				// else alert that email isn't registered, send user to register
	
				
				MongoClientURI uri = new MongoClientURI(
					    //"mongodb+srv://andrea:45A7GP6sO0xm82bC@cluster0.yuj3e.mongodb.net/Register?retryWrites=true&w=majority");
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
							//Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							//alert.setTitle("");
							//alert.setHeaderText(null);
							//alert.setContentText("Login successful!");
//							alert.initOwner(pane.getScene().getWindow());
							//alert.show();
							
							((Node)event.getSource()).getScene().getWindow().hide();
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
							Parent root = loader.load();
							MainController show = (MainController)loader.getController();
							Stage primaryStage = new Stage();
//								Parent root = FXMLLoader.load(getClass().getResource("/application/MainPage.fxml"));
								Scene scene = new Scene(root,500,300);
								scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
								scene.setFill(Color.TRANSPARENT);
								primaryStage.initStyle(StageStyle.TRANSPARENT);
								primaryStage.setScene(scene);
								primaryStage.show();
								
								// prints the username from the document found above
								FindIterable<Document> find = coll.find(search).projection(Projections.include("Name"));
								for (Document name : find) {
									username = (String) name.get("Name");
									show.SetText();
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
				
				/*			
				if(db.Login.find({ "email."+emailField.getText() , { "$exists", true }})) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("");
					alert.setHeaderText(null);
					alert.setContentText("");
					alert.initOwner(pane.getScene().getWindow());
					alert.show();
					return;
				}
				 */
				
	//			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	//			alert.setTitle("Signed In");
	//			alert.setHeaderText(null);
	//			alert.setContentText("Info recieved");
	//			alert.initOwner(pane.getScene().getWindow());
	//			alert.show();
				
			}
		
		public String getUserName() {
			return username;
		}
		
		// Event Listener on Button.onAction
		@FXML
		public void CloseApp(ActionEvent event) {
			// TODO Autogenerated
			Platform.exit();
			System.exit(0);
		}
	}
