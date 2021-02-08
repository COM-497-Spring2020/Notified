package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
	
	public void CreateReminder (ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/CreateReminder1.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();}
			
	public void NewsFeed(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/NewsFeed.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
	}
	
}
