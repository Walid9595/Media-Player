package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
	String path = "";
	Media media;
	MediaPlayer mediaPlayer ;
	@Override
	public void start(Stage primaryStage) {
		HBox hbox = new HBox();
		
		primaryStage.getIcons().add(new Image("file:logo.png"));		
		Scene scene = new Scene(hbox);
		primaryStage.setTitle("MediaPlayer");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
