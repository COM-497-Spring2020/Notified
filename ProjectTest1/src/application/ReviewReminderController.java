package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReviewReminderController {
	@FXML
	private Label display1;
	@FXML
	private Label display2;
	@FXML
	private Label display3;
	@FXML 
	private AnchorPane container;
	@FXML
	private TableView<ViewReminder> display;
	@FXML
	private TableColumn<ViewReminder, String> col;
	
	public void initialize(){
		SignInController result = new SignInController();
		// adds the list from viewNotifs of the SignInController to an observable list
		ObservableList<ViewReminder> viewReminder = FXCollections.observableArrayList(result.viewNotifs());
		
		// calls the information from the ViewReminder model class to fill the column of the table
		col.setCellValueFactory(new PropertyValueFactory<ViewReminder, String>("reminder"));
		// The ObservableList populates the table
		display.setItems(viewReminder);
	}

	// Event Listener on Button.onAction
	@FXML
	public void BacktoMain(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainPage.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
}
