package application;	
import java.io.File;
import javafx.scene.media.MediaPlayer.Status;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application 
{
	String path = "";
	Media media;
	MediaPlayer mediaPlayer ;
	@Override
	public void start(Stage primaryStage) {
		HBox hbox = new HBox();
		//set the window icon
		primaryStage.getIcons().add(new Image("file:logo.png"));
		
         //create the play image and imageView 
		File file1 = new File("icons/play.png");
		Image playButtonImage = new Image(file1.toURI().toString());
		ImageView playView = new ImageView(playButtonImage);
		
		//create the pause image and imageView
		File file2 = new File("icons/pause.png");
		Image pauseButtonImage = new Image(file1.toURI().toString());
		ImageView pauseView = new ImageView(playButtonImage);	
		Button playpauseButton = new Button();  //create the play&pause button
		playpauseButton.setGraphic(playView);
		//not working 
		//playpauseButton.setStyle("-fx-background-image: url('/icons/play.png');");
	
		hbox.getChildren().add(playpauseButton);   //add the button to the hbox	
		hbox.setAlignment(Pos.CENTER);       //align the hbox to the center		
		FileChooser fileChooser = new FileChooser();  //create a fileChooser object to open a window to select the file		
		File file = fileChooser.showOpenDialog(null);     //open an dialog to choose from	 
		
		path = file.getAbsolutePath();                      //get the path of the selected file                                                       
		path = path.replace("\\","/");	                           //adapt the path to java rules	                                                         
		media = new Media(new File(path).toURI().toString());      //create a media object
		
	    mediaPlayer = new MediaPlayer(media);    //set the media object to a mediaPllayer object
		mediaPlayer.setAutoPlay(true);	         //set autoPlay to true		
		
		MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
		hbox.getChildren().add(mediaView); //add the media view object to the hbox
		playpauseButton.setOnAction(e->          //when the button clicked
		{	
			//if the media status is stopped or paused start it and change the button graphic
			//note the button graphic is not working till i fix it
			if(mediaPlayer.getStatus().equals(Status.STOPPED)||mediaPlayer.getStatus().equals(Status.PAUSED))
				{
				          mediaPlayer.play();	
				playpauseButton.setGraphic(pauseView);
				}
			//else pause the current file and change the button graphic
			//note the button graphic is not working till i fix it
			else
			{
				mediaPlayer.pause();
				playpauseButton.setGraphic(playView);
			}
		});
		Scene scene = new Scene(hbox); //add the hbox to the scene
		primaryStage.setTitle("WMA Player");//set the stage Title
		primaryStage.setScene(scene);       //set the scene to the stage
		primaryStage.show();                //show the stage
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
