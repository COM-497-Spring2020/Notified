package createReminder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ReminderForm extends Application {
	private Stage stage;
	private DatePicker checkInDatePicker;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Create a Reminder");
		//the grid pane
		GridPane gridPane = createRegistrationFormPane();
		//add ui controls to reminder form grid pane
		addUIControls(gridPane);
		//create scene with reminder form grid pane as root node
		Scene scene = new Scene(gridPane, 800, 500);
		//set scene in primary stage
		primaryStage.setScene(scene);
		
		
		
		primaryStage.show();
	}
	
	private GridPane createRegistrationFormPane() {
		//instantiate new grid pane
		GridPane gridPane = new GridPane();
		//position pane at center of screen
		gridPane.setAlignment(Pos.CENTER);
		//set padding 
		gridPane.setPadding(new Insets(40, 40, 40, 40));
		//set horizontal gap between columns
		gridPane.setHgap(10);
		//set vertical gap between rows
		gridPane.setVgap(10);
		
		//Column constraints
		//column one constraints is for nodes placed in column one
		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);
		//column two constraints is for nodes placed in column two
		ColumnConstraints columnTwoConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstraints.setHalignment(HPos.RIGHT);
		
		gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints);
		
		return gridPane;
	}
	
	private void addUIControls(GridPane gridPane) {
		Label headerLabel = new Label("Create a Reminder");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		headerLabel.setStyle("-fx-text-fill: green; -fx-font-size: 30px; -fx-font-weight: bold;");
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
		
		//add name label
		Label nameLabel = new Label("Event Name: ");
		nameLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 16px;");
		gridPane.add(nameLabel, 0,  1);
		
		//add name text field
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);
		gridPane.add(nameField, 1, 1);
		
		//add email label
		Label descriptionLabel = new Label("Description: ");
		descriptionLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 16px;");
		gridPane.add(descriptionLabel, 0, 2);
		
		//add email text field
		TextField descriptionField = new TextField();
		descriptionField.setPrefHeight(40);
		gridPane.add(descriptionField, 1, 2);
		
		//add name label
		Label dateLabel = new Label("Date: ");
		dateLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 16px;");
		gridPane.add(dateLabel, 0, 3);
		
		//add email text field
		TextField dateField = new TextField();
		dateField.setPrefHeight(40);
		gridPane.add(dateField, 1, 3);
		
		//add name label
		Label timeLabel = new Label("Time: ");
		timeLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 16px;");
		gridPane.add(timeLabel, 0, 4);
				
		//add email text field
		TextField timeField = new TextField();
		timeField.setPrefHeight(40);
		gridPane.add(timeField, 1, 4);
		
/*		//date picker
		VBox vbox = new VBox(20);
		vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene); 

        checkInDatePicker = new DatePicker();

        Label checkInlabel = new Label("Check-In Date:");
        gridPane.add(checkInlabel, 0, 0);

        gridPane.add(checkInDatePicker, 0, 1);
        vbox.getChildren().add(gridPane); */
		
/*		//add password label
		Label passwordLabel = new Label("Password: ");
		gridPane.add(passwordLabel, 0, 3);
		
		//add password field
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		gridPane.add(passwordField, 1, 3); */
		
		//add submit button
		Button submitButton = new Button("Submit");
		submitButton.setPrefHeight(40);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);
		gridPane.add(submitButton, 0, 5, 2, 1);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(nameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
							"Form ERROR!", "Please Enter your name");
					return;
				}
				if(descriptionField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
							"Form ERROR!", "Please enter your email id");
					return;
				}
				if(dateField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
							"Form ERROR!", "Please enter the date");
					return;
				}
				if(timeField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),
							"Form ERROR!", "Please enter the time");
					return;
				}
				
				showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(),
						"Registration Successful!", "Welcome " + nameField.getText());
			}
		});
	}
	
	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
