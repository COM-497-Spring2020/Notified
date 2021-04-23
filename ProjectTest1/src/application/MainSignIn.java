package application;

import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;


public class MainSignIn extends Application {
	
	@FXML
    private AnchorPane rootPane;
	
	private double xOffset = 0;
    private double yOffset = 0;
    

	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/SignIn.fxml"));
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.95;
			double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.9;
			
			primaryStage.setX(x);
			primaryStage.setY(y);
			
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        }); 
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		/*	// When the stage closes store the current size and window location.
		     
	        primaryStage.setOnCloseRequest((final WindowEvent event) -> {
	            Preferences preferences = Preferences.userRoot();
	            preferences.putDouble(null, primaryStage.getX());
	            preferences.putDouble(null, primaryStage.getY());
	            //preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
	            //preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
	        }); */
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}