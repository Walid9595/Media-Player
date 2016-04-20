package application;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;

public class Button_Builder extends Main{
FileChooser fileChooser = new FileChooser();  //create a fileChooser object to open a window to select the file			
	String path = "";
	/*MediaPlayer mediaPlayer ;*/
	Media media;
	boolean b=false;
	Main m = new Main();
	String name;
	public Button_Builder()
	{
		
	}
	HBox hbox = new HBox();
	//create the play image and imageView 
	public ImageView playView()
	{
		File file1 = new File("icons/play.png");
		Image playButtonImage = new Image(file1.toURI().toString());
		ImageView playView = new ImageView(playButtonImage);
		playView.setFitWidth(40);
		playView.setFitHeight(40);
		return playView;
	}
			
			public ImageView pauseView()
			{
				//create the pause image and imageView
				File file2 = new File("icons/pause.png");
				Image pauseButtonImage = new Image(file2.toURI().toString());
				ImageView pauseView = new ImageView(pauseButtonImage);
				return pauseView;
			}
			
			public ImageView forwardView()
			{
				File file3 = new File("icons/right.png");
				Image forwardButtonImage = new Image(file3.toURI().toString());
				ImageView forwardView = new ImageView(forwardButtonImage);
				return forwardView;
			}
			
			public ImageView backwardView()
			{
				File file4 = new File("icons/left.png");
				Image backwardButtonImage = new Image(file4.toURI().toString());
				ImageView backwardView = new ImageView(backwardButtonImage);
				return backwardView;
			}
			
			public ImageView stopView()
			{
				File file5 = new File("icons/stop.png");
				Image stopButtonImage = new Image(file5.toURI().toString());
				ImageView stopView = new ImageView(stopButtonImage);
				return stopView;
			}
			
			public ImageView reloadView()
			{
				File file7 = new File("icons/repeat.png");
				Image reloadButtonImage = new Image(file7.toURI().toString());
				ImageView reloadView = new ImageView(reloadButtonImage);
				return reloadView;
			}
			
			public ImageView shulffView()
			{
				File file7 = new File("icons/shulff.png");
				Image shulffButtonImage = new Image(file7.toURI().toString());
				ImageView shulff = new ImageView(shulffButtonImage);
				return shulff;
			}
			
			//Create the buttons control 
			public Button forwardButton()
			{
				Button forwardButton = new Button();  //create the forward button
				forwardButton.setPrefWidth(30);
				forwardButton.setPrefHeight(30);
			    forwardButton.setGraphic(forwardView());
			    forwardButton.setTooltip(new Tooltip("Next File"));
			    return forwardButton;
			}
			
			public Button backwardButton()
			{
				 Button backwardButton = new Button();  //create the backward button
				 backwardButton.setPrefWidth(30);
				 backwardButton.setPrefHeight(30);
				    backwardButton.setGraphic(backwardView());
				    backwardButton.setTooltip(new Tooltip("Previous File"));
				    return backwardButton;
			}
			
			public Button stopButton()
			{
				Button stopButton = new Button();  //create stop button
				stopButton.setPrefWidth(30);
				stopButton.setPrefHeight(30);
			    stopButton.setGraphic(stopView());
			    stopButton.setTooltip(new Tooltip("Stop"));
			    return stopButton;
			}
			
			public Button reloadButton()
			{
				Button reloadButton = new Button();  //create reload button
				reloadButton.setPrefWidth(30);
				reloadButton.setPrefHeight(30);
			    reloadButton.setGraphic(reloadView());
			    reloadButton.setTooltip(new Tooltip("Repeat"));
			    return reloadButton;
			}
			
			public Button shulffButton()
			{
				Button shulff = new Button();  //create reload button
				shulff.setPrefWidth(30);
				shulff.setPrefHeight(30);
			    shulff.setGraphic(shulffView());
			    shulff.setTooltip(new Tooltip("Shuffle on/off"));
			    return shulff;
			}
			
			public Button playPause()
			{
				Button playpauseButton = new Button();  //create the play&pause button
				playpauseButton.setGraphic(playView());
				playpauseButton.setPrefWidth(60);
				playpauseButton.setPrefHeight(60);
				playpauseButton.setTooltip(new Tooltip("Play/Pause"));
				return playpauseButton;				
			}
			
			public Button open()
			{
				File file3 = new File("icons/open.png");
				Image openButtonImage = new Image(file3.toURI().toString());
				ImageView openView = new ImageView(openButtonImage);
				openView.setFitWidth(30);
				openView.setFitHeight(30);
				Button open = new Button();
				open.setGraphic(openView);
				open.setPrefWidth(openView.getFitWidth());
				open.setPrefHeight(openView.getFitHeight());
				open.setTooltip(new Tooltip("Open File"));
				return open;
			}
			//create the buttons events
			public void playPauseEvent(MediaPlayer mediaPlayer,Button b)
			{
				 //if the media status is stopped or paused start it and change the button graphic
				if(mediaPlayer.getStatus().equals(Status.PLAYING)||mediaPlayer.getStatus().equals(Status.READY))
				{
				          mediaPlayer.pause();
				         //playView.setImage(pauseButtonImage);
				          b.setGraphic(playView());		          
				}
			//else pause the current file and change the button graphic
			//note the button graphic is not working till i fix it
			else
			{
				
				mediaPlayer.play();
				//playView.setImage(playButtonImage);
				b.setGraphic(pauseView());
			}
			}	
			public MediaPlayer playMultiple(Media m , MediaPlayer mediaPlayer)
			{				
					
				
				mediaPlayer = new MediaPlayer(m);    //set the media object to a mediaPllayer object				   
				mediaPlayer.setAutoPlay(true);	         //set autoPlay to true	
				
				return mediaPlayer;
			}
			public MediaPlayer openFile(ActionEvent e ,MediaPlayer mediaPlayer)
			{
				fileChooser.setTitle("Open File");
			if(b)
			{			
				System.out.println("Error ");
				//System.exit(0);
				
				File file = fileChooser.showOpenDialog(null);     //open an dialog to choose from	 
				
				path = file.getAbsolutePath();                      //get the path of the selected file                                                       
				path = path.replace("\\","/");	                           //adapt the path to java rules	                                                         
				
				media = new Media(new File(path).toURI().toString());      //create a media object
				
				mediaPlayer.stop();
			    mediaPlayer = new MediaPlayer(media);    //set the media object to a mediaPllayer object
			   
				mediaPlayer.setAutoPlay(true);	         //set autoPlay to true	
			}
			else{
			File file = fileChooser.showOpenDialog(null);     //open an dialog to choose from	 
			
			path = file.getAbsolutePath();                      //get the path of the selected file                                                       
			path = path.replace("\\","/");	                           //adapt the path to java rules	                                                         
			name = file.getName();
			media = new Media(new File(path).toURI().toString());      //create a media object
			
		    mediaPlayer = new MediaPlayer(media);    //set the media object to a mediaPllayer object
		    
			mediaPlayer.setAutoPlay(true);	         //set autoPlay to true		
						
			b=true;
			}
			return mediaPlayer;
			}
			public Media getMedia()
			{
				return media;
			}
			
			public String getMediaName()
			{
				return name; 
			}
			public void forwardEvent(ActionEvent e ,MediaPlayer m)
			{				
				m.seek(m.getCurrentTime().multiply(1.2));				
			}
			public void backwardEvent(ActionEvent e,MediaPlayer m)
			{
				m.seek(m.getCurrentTime().divide(1.2));
			}
			public void stopEvent(ActionEvent e ,MediaPlayer m)
			{
				m.seek(m.getTotalDuration());
				m.stop();				
			}
			public void repeatEvent(ActionEvent e ,MediaPlayer m )
			{
				
						positionSlider.setValue(0);
						m.seek(m.getStartTime());						
				    
			}
}
