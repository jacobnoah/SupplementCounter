package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// set a title for the Window
			primaryStage.setTitle("Choose Supplements and Date");
			
			// get an FXML loader and read in the fxml code
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/SupplementCounterWindow.fxml")); 
			AnchorPane mainLayout = (AnchorPane)loader.load();
			
			Image icon = new Image("application/pillbottle.jpg");
			primaryStage.getIcons().add(icon);
			
			// Create the scene with the layout in the fxml code, set the scene and show it
			Scene scene = new Scene(mainLayout);
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