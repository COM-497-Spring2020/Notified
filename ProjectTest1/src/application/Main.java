package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;


public class Main extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;
    
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/NewDesign4.fxml"));
			Scene scene = new Scene(root,650,460);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.getIcons().add(new Image("/images/NotifyMe icon.png"));
			
			//Set window to show up at the right bottom corner
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
