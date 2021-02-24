package application;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {
	@FXML
	private TextField name;
	@FXML
	private TextField date;
	@FXML
	private TextField place;
	@FXML
	private TextField comments;
	@FXML
	private Label display;
//	@FXML
//	private Label displayDate;
//	@FXML
//	private Label displayLocation;
//	@FXML
//	private Label displayComments;
	
	public void CreateReminder (ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/CreateReminder1.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();}
			
	public void NewsFeed(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/NewsFeed.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();}
	
	public void NextToDeco(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/CreateReminder3.fxml"));
		Parent root = loader.load();
		Controller2 show = (Controller2)loader.getController();
		show.displayText(name.getText() + "\n" + date.getText() + "\n" + place.getText() + "\n" + comments.getText());
		Stage primaryStage = new Stage();
			//Parent root = FXMLLoader.load(getClass().getResource("/application/CreateReminder3.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		Reminder reminder = new Reminder(name.getText(), date.getText(), place.getText(),comments.getText());
		Reminder.main();	
	}
	
	/*public void BackToRe1(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/CreateReminder1.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();}*/
	
	public void BackToMain(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainPage.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();}
		
}
