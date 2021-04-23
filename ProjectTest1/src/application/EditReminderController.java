package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.Document;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EditReminderController {
	@FXML
	private TextField name, place, comments;
	@FXML
	private JFXDatePicker jfxDate;
	@FXML
	private JFXTimePicker startTime, endTime;
	@FXML
	private ChoiceBox<String> alert = new ChoiceBox<>(), repeat = new ChoiceBox<>();
	@FXML
    private AnchorPane rootPane;
	SignInController result = new SignInController();
	
	public void loadEdit(String reminder) throws IOException {
		// populate choice boxes
    	alert.getItems().addAll("No alert", "5 minutes before", "15 minutes before", "30 minutes before", "1 hour before", "1 day before", "1 week before");
    	repeat.getItems().addAll("Does not repeat", "Every day", "Every week", "Every month", "Every year");
    	
    	// splits the reminder string based on each new line
		String lines[] = reminder.split("\\r?\\n");
		
		// splits the date line of the reminder by each "-"
		String dates[] = lines[1].split("-");
		int mdy[] = new int[5];
		// parses values from dates[] to int and adds to a new int array
		for (int i = 0; i < dates.length; i++) {
			mdy[i] = Integer.parseInt(dates[i]);
		}
		
		// create an array for the start time values
		String start[] = lines[4].split(":\\s*");
		// create another array that will hold the actual time values from the "Start Time:" line
		String time[] = {start[1], start[2]};
		// parse values from time[] to int and add to int array
		int timeStart[] = new int[5]; 
		for (int i = 0; i < time.length; i++) {
			timeStart[i] = Integer.parseInt(time[i]);
		}
		
		// create an array for the end time values
		String end[] = lines[5].split(":\\s*");
		//create another array that will hold the actual time values from the "End Time:" line
		String time2[] = {end[1], end[2]};
		// parse values from time2[] to int and add to int array
		int timeEnd[] = new int[5];
		for (int i = 0; i < time2.length; i++) {
			timeEnd[i] = Integer.parseInt(time2[i]);
		}
		
		// get the value of the choice boxes
		String alerts[]  = lines[6].split(":\\s*");
		String repeats[] = lines[7].split(":\\s*");
		
		// set the values of all fields based on the existing reminder
		name.setText(lines[0]);
		jfxDate.setValue(LocalDate.of(mdy[0], mdy[1], mdy[2]));
		place.setText(lines[2]);
		comments.setText(lines[3]);
		startTime.setValue(LocalTime.of(timeStart[0], timeStart[1]));
		endTime.setValue(LocalTime.of(timeEnd[0], timeEnd[1]));
		alert.setValue(alerts[1]);
		repeat.setValue(repeats[1]);		
	}
	
	public void UpdateReminder() throws Exception {
		if(name.getText().isEmpty() || jfxDate.getValue() == null || place.getText().isEmpty() || comments.getText().isEmpty() ||
				startTime.getValue() == null || endTime.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Reminder error");
			alert.setHeaderText(null);
			alert.setContentText("Please complete all fields");
			alert.show();
		}
		else {
			SignInController id = new SignInController();	
			
			MongoClientURI uri = new MongoClientURI(
					  "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
					MongoClient mongoClient = new MongoClient(uri);
					MongoDatabase database = mongoClient.getDatabase("Notified");
			
			MongoCollection<Document> coll = (MongoCollection<Document>) database.getCollection("Notifications");
			
			coll.replaceOne(eq("UserID", id.getUserID()),new Document("UserID", id.getUserID()).append("EventName", name.getText())
					.append("Date", jfxDate.getValue().toString()).append("Location", place.getText()).append("Comments", comments.getText())
					.append("StartTime", startTime.getValue().toString()).append("EndTime", endTime.getValue().toString())
					.append("Alert", alert.getSelectionModel().getSelectedItem())
					.append("Repeat", repeat.getSelectionModel().getSelectedItem()).append("ReminderID", ReviewReminderController.rid));
			
			// return to Review Reminder after editing
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/NewReviewReminder.fxml"));
			AnchorPane pane = loader.load();
			rootPane.getChildren().setAll(pane);
		}
	}
	
	public void ReviewReminder (ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/NewReviewReminder.fxml"));
		AnchorPane pane = loader.load();
		rootPane.getChildren().setAll(pane);
	}
}
