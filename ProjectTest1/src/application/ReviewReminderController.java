package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReviewReminderController {
	SignInController result = new SignInController();
	ObservableList<ViewReminder> viewReminder = FXCollections.observableArrayList();	
	public static String rid;
	@FXML 
	private AnchorPane container;
	@FXML
	private TableView<ViewReminder> display;
	@FXML
	private TableColumn<ViewReminder, String> col;
	@FXML
    private AnchorPane rootPane;
	
	public void initialize(){
		//calls the information from the ViewReminder model class to fill the column of the table
		col.setCellValueFactory(new PropertyValueFactory<ViewReminder, String>("reminder"));
		//The ObservableList populates the table
		getDisplay().setItems(loadRemindersList());
	}
	
	// Adds all of a users reminders to a list
	public ObservableList<ViewReminder> loadRemindersList() {
		MongoClientURI uri = new MongoClientURI(
				  "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
				MongoClient mongoClient = new MongoClient(uri);
				MongoDatabase database = mongoClient.getDatabase("Notified");
		
		// searches for all reminders associated with a specific userID
		MongoCollection<Document> coll = (MongoCollection<Document>) database.getCollection("Notifications");			
		BasicDBObject search = new BasicDBObject().append("UserID", result.getUserID());
		FindIterable<Document> result = coll.find(search);
		
		for (Document list : result) {
			ViewReminder v = new ViewReminder();
			// sends the information to the ViewReminder model class, which will then be displayed
			// in a table view on ReviewReminderController
			v.setReminder(list.get("EventName") + "\n" + (String) list.get("Date") + "\n" + (String) list.get("Location") + "\n" + 
					(String) list.get("Comments") + "\n" + "Start Time: " + (String) list.getString("StartTime") + "\n" +
					"End Time: " + (String) list.getString("EndTime") + "\n" + "Alert: " + (String) list.getString("Alert") + "\n" + "Repeat: " +
					(String) list.getString("Repeat"));
			// adds all reminders for the user to a list 
			viewReminder.add(v);
		}
		return viewReminder;
	}

	// Event Listener on Button.onAction
	@FXML
	public void BacktoMain(ActionEvent event) throws Exception {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewMainPage.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	
	public void EditReminder() throws Exception {
		ViewReminder viewReminder = getDisplay().getSelectionModel().getSelectedItem();
		System.out.println(viewReminder.getReminder());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/EditReminder.fxml"));
		AnchorPane pane = loader.load();
		rootPane.getChildren().setAll(pane);
		EditReminderController edit = (EditReminderController)loader.getController();
		edit.loadEdit(viewReminder.getReminder());
	}

	public void RemoveFromTable() {
		getDisplay().getItems().remove(getDisplay().getSelectionModel().getSelectedItem());
	}
	
	public void DeleteReminder() throws Exception {
		// gets selected reminder from the table
		ViewReminder viewReminder = getDisplay().getSelectionModel().getSelectedItem();
		
		MongoClientURI uri = new MongoClientURI(
				  "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
				MongoClient mongoClient = new MongoClient(uri);
				MongoDatabase database = mongoClient.getDatabase("Notified");
		
		// searches for all reminders associated with a specific userID
		MongoCollection<Document> coll = (MongoCollection<Document>) database.getCollection("Notifications");
		
		// gets reminder info
		String reminder = viewReminder.getReminder();
		String lines[] = reminder.split("\\r?\\n");
		String name = lines[0]; String place = lines[2]; String comments = lines[3];
		
		// finds reminder in DB using info from above
		BasicDBObject search = new BasicDBObject().append("UserID", result.getUserID()).append("EventName", name)
				.append("Location", place).append("Comments", comments);
		FindIterable<Document> find = coll.find(search);
		
		// finds the reminderID of the reminder
		for (Document id : find) {
			 rid = id.get("ReminderID").toString();
		}
		
		// creates alert to verify if the user wants to delete the reminder
		ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING,
		        "Are you sure you want to delete this reminder?",
		        yes, no);

		alert.setTitle("Delete Reminder?");
		Optional<ButtonType> result = alert.showAndWait();

		// deletes reminder
		if (result.get() == yes) {
			RemoveFromTable();
			coll.deleteOne(eq("ReminderID", rid));
		}
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	public TableView<ViewReminder> getDisplay() {
		return display;
	}

	public void setDisplay(TableView<ViewReminder> display) {
		this.display = display;
	}
}
