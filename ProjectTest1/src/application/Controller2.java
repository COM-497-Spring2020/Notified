package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller2 {
	@FXML
	private Label display;
	
	public void displayText(String text) {
		display.setText(text);
	}
	
	public void BackToRe1(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/CreateReminder1.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();}
}
