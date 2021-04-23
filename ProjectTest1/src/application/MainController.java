package application;



import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MainController {
	private double xOffset = 0;
    private double yOffset = 0;
	
    @FXML
    private AnchorPane rootPane, page1;
    
    @FXML
    private MenuItem mi_signIn, mi_signOut, mi_newsFeed, mi_createReminder, 
    mi_createReminder1, mi_createReminder2, mi_mainPage, mi_newsfeed;
    
    public static UUID reminderID;
    
    @FXML
	private TextField name, place, comments;
	@FXML
	private JFXTimePicker startTime, endTime;
	@FXML
	private JFXDatePicker jfxDate;
	@FXML 
	private ChoiceBox<String> alert = new ChoiceBox<>(),  repeat = new ChoiceBox<>();
	@FXML
	private Label display, username;	
//	Notification n = new Notification();
    
    public void initialize() {
    	// populate choiceboxes and set default values
    	alert.getItems().addAll("No alert", "5 minutes before", "15 minutes before", "30 minutes before", "1 hour before", "1 day before", "1 week before");
    	alert.setValue("No alert");
    	repeat.getItems().addAll("Does not repeat", "Every day", "Every week", "Every month", "Every year");
    	repeat.setValue("Does not repeat");
    }
    
	public void SetText() {
		SignInController login = new SignInController();
		String info = login.getUserName();
		username.setText("Welcome " + info);
//		n.addNotifications();
	}
	
	public void SetMenuItem() {
		mi_createReminder.setVisible(true);
		mi_mainPage.setVisible(true);
		mi_signIn.setVisible(false);
	 }
	
	public void CreateReminder (ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewCreateReminder.fxml"));
		rootPane.getChildren().setAll(pane);
		page1.setVisible(false);
	}
	
	public void ReviewReminder (ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewReviewReminder.fxml"));
		rootPane.getChildren().setAll(pane);
		page1.setVisible(false);
		mi_mainPage.setVisible(true);
	}
			
	public void NewsFeed(ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewNewsFeed.fxml"));
		rootPane.getChildren().setAll(pane);
		page1.setVisible(false);
		mi_mainPage.setVisible(true);
		}
	
	public void NextToRe2(ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/NewCreateReminder2.fxml"));
		AnchorPane pane = loader.load();
		rootPane.getChildren().setAll(pane);
//		Controller2 show = (Controller2)loader.getController();
//		show.displayText(name.getText() + "\n" + date.getText() + "\n" + place.getText() + "\n" + comments.getText() + "\n" + 
//				start.getText() + " - " + end.getText());
		
//		Reminder reminder = new Reminder(name.getText(), date.getText(), place.getText(),comments.getText());
//		Reminder.main();	
		
//		SignInController id = new SignInController();
//		Reminder reminder = new Reminder(name.getText(), date.getText(), place.getText(),comments.getText(), id.getUserID(), 
//				start.getText(), end.getText(), alert.getSelectionModel().getSelectedItem(), repeat.getSelectionModel().getSelectedItem());
//		Reminder.main();	
		
	}
	
	public void SaveToDB() throws IOException {
		if(name.getText().isEmpty() || jfxDate.getValue() == null || place.getText().isEmpty() || comments.getText().isEmpty() ||
				startTime.getValue() == null || endTime.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Reminder error");
			alert.setHeaderText(null);
			alert.setContentText("Please complete all fields");
			alert.show();
		}
		
		// create unique reminderID
		reminderID = UUID.randomUUID();
		
		SignInController id = new SignInController();
		Reminder reminder = new Reminder(name.getText(), jfxDate.getValue().toString(), place.getText(),comments.getText(), id.getUserID(), 
				startTime.getValue().toString(), endTime.getValue().toString(), alert.getSelectionModel().getSelectedItem(), 
				repeat.getSelectionModel().getSelectedItem(), reminderID.toString());
		Reminder.main();
		
		// then go back to main
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewMainPage.fxml"));
		rootPane.getChildren().setAll(pane);	
		page1.setVisible(false);
	}

	public String getReminderID() {
		return reminderID.toString();
	}
	
	public void BackToMain(ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewMainPage.fxml"));
		rootPane.getChildren().setAll(pane);	
		page1.setVisible(false);
		mi_signIn.setVisible(false);
	}
	
	public void SignOut(ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewSignIn.fxml"));
		rootPane.getChildren().setAll(pane);
		page1.setVisible(false);
		mi_signIn.setVisible(true);
		}
	
	public void MinimizeApp(ActionEvent event) throws Exception{
		Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
		s.setIconified(true);	
	}
	
	public void CloseApp(ActionEvent event) throws Exception{
		Platform.exit();
		System.exit(0);}
		
}
