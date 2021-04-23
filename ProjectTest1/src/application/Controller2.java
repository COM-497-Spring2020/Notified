package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller2 {
	@FXML
	private Label display;
	@FXML
    private AnchorPane rootPane;
	
	public void displayText(String text) {
		display.setText(text);
	}
	
	public void BackToRe1(ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewCreateReminder.fxml"));
		rootPane.getChildren().setAll(pane);}
	
	public void BackToRe2(ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewCreateReminder2.fxml"));
		rootPane.getChildren().setAll(pane);}
	
public void BackToMain (ActionEvent event) throws Exception{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/NewMainPage.fxml"));
		rootPane.getChildren().setAll(pane);}		
	
	public void CloseApp(ActionEvent event) throws Exception{
		Platform.exit();
		System.exit(0);}
}
