/*Team Memmbers 
1-Walid Mohammed Ali Mohammed
2-Moistafa Abou El Wafa Moustafa
3-Ahmed Bahy Edin Mohammed
4-Walid Mohammed Osman */
package application;	
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * @author Walid
 *
 */
public class Main extends Application
{ 
	private TimeSlider_builder sldb;
	private final Button_Builder buttons = new Button_Builder();
	private final Keyboard keyboard = new Keyboard();
    private  Scene scene;
    Label label = new Label("WMA Player");
    private final Random random = new Random();
	private String path="";
	 static Label currentTimeLabel; 
	 static Slider volumeSlider ;
	static Slider positionSlider;
	private  Media media;
	static MediaPlayer mediaPlayer ;
	private ListView<String>listView = new ListView<String>();
    private  ArrayList<String>playList = new ArrayList<String>();
    private  MediaPlayer mp;
	
	private final Button playpauseButton = buttons.playPause();
	
	private final Button forwardButton = buttons.forwardButton();
	
	private final Button backwardButton = buttons.backwardButton();
	
	private final Button stopButton = buttons.stopButton();
	
	private final Button randomButton = buttons.randomButton();
	
	private final Button repeat = buttons.reloadButton();
	private final Button open = buttons.open();
	private final ImageView playView = buttons.playView();
	private final HBox hbox1 = new HBox();
	private final HBox hbox2 = new HBox();
	int j;
	int i;
	int count =0;
	String song;
	int songs = 0;
	Label Tracks  = new Label();
	//Label Time = new Label();
	int TTime;
	boolean timeFlag = true;
	boolean trackFlag;
	private File file;
	
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
		Menu menu = new Menu("Open");
		MenuItem item1 = new MenuItem("Open File");
		MenuItem item2 = new MenuItem("Open Folder");
	    menu.getItems().add(item1);
	    menu.getItems().add(item2);
	    MenuBar bar = new MenuBar(menu);	      
		
		
		//set the window icon
		primaryStage.getIcons().add(new Image("file:logo.png"));
		hbox1.setMinWidth(100);
		hbox1.setMaxWidth(Double.MAX_VALUE);
		hbox1.getChildren().addAll(repeat,open,backwardButton,playpauseButton,forwardButton,stopButton,randomButton);			
		hbox1.setAlignment(Pos.CENTER);       //align the hbox to the center		
		//working on it but it does not work now
		
		item1.setOnAction(e->{
			try{
				songs=0;
				songs = playList.size();
				mediaPlayer = buttons.openFile(e,mediaPlayer);
				media = buttons.getMedia();				
				MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
				hbox1.getChildren().add(mediaView); //add the media view object to the hbox		
				listView.getItems().clear();
				sldb = new TimeSlider_builder();                                    //set the mediaPlayer object to a mediaView object
				listView.getItems().add(buttons.getMediaName(mediaPlayer));
				playpauseButton.setGraphic(buttons.pauseView());
				label.setText(buttons.getMediaName(mediaPlayer));
				sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
				TTime += mediaPlayer.getTotalDuration().toHours();
				songs++;
				Tracks.setText("Tracks : "+songs);
				}
				catch(NullPointerException ex)
				{
					//System.out.println("No File selected");
				}			
		});
		
		item2.setOnAction(e->{	
			try{
				songs=0;
				chooseDirec();
				for(i=0;i<playList.size();i++)
				{
					songs++;
				}
				Tracks.setText("Tracks : "+(songs));
			   }
			
			catch(IndexOutOfBoundsException exp)
			{
				
				int answer = JOptionPane.showConfirmDialog(null,"No Files Found...!! Select another Folder ??" );
				if (answer == JOptionPane.YES_OPTION)
				{
					chooseDirec();
					
				}
				else
				{
				listView.getItems().add("No Files Found...!!");
				playpauseButton.setGraphic(buttons.playView());
				mediaPlayer=null;
				label.setText("WMA Player");
				}
			}
			catch(NullPointerException ex)
			{
				
			}
		});	
		
listView.setOnMouseClicked(e->{
			
			count = listView.getSelectionModel().getSelectedIndex();			
				try{
						if(mediaPlayer!=null)
							mediaPlayer.stop();
				playpauseButton.setGraphic(buttons.pauseView());
				sldb = new TimeSlider_builder();
				path = playList.get(count);   			
				media = new Media(new File(path).toURI().toString());
		        mediaPlayer = new MediaPlayer(media);
		        mediaPlayer.play();
		       
		        label.setText(buttons.getMediaName(mediaPlayer));
		        sldb = new TimeSlider_builder();  
	            sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
			 mediaPlayer.setOnEndOfMedia(new Runnable() {			
					@Override
					public void run() {
						// TODO Auto-generated method stub						
						mediaPlayer.stop();
						path = playList.get(count+1);
						media = new Media(new File(path).toURI().toString());
				        mediaPlayer = new MediaPlayer(media);
				        mediaPlayer.play();
				        label.setText(buttons.getMediaName(mediaPlayer));
				        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
				       
				        label.setText(buttons.getMediaName(mediaPlayer));
					}
				});
					
					}
				
				catch(Exception ex)
				{
					
				}
			}
		);


listView.setOnKeyPressed(e->{
	
	count = listView.getSelectionModel().getSelectedIndex();			
		try{
			if(e.getCode().equals(KeyCode.ENTER))
			{
				if(mediaPlayer!=null)
					mediaPlayer.stop();
				
		playpauseButton.setGraphic(buttons.pauseView());
		sldb = new TimeSlider_builder();
		path = playList.get(count);   			
		media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
       
        label.setText(buttons.getMediaName(mediaPlayer));
        sldb = new TimeSlider_builder();  
        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
	 mediaPlayer.setOnEndOfMedia(new Runnable() {			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub						
				mediaPlayer.stop();
				path = playList.get(count+1);
				media = new Media(new File(path).toURI().toString());
		        mediaPlayer = new MediaPlayer(media);
		        mediaPlayer.play();
		        label.setText(buttons.getMediaName(mediaPlayer));
		        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
		       
		        label.setText(buttons.getMediaName(mediaPlayer));
			}
		});
			}
			}
		
		catch(Exception ex)
		{
			
		}
	}
);

		open.setOnAction(e->
		{			
			try{
				songs=0;
				mediaPlayer = buttons.openFile(e,mediaPlayer);
				media = buttons.getMedia();				
				MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
				hbox1.getChildren().add(mediaView); //add the media view object to the hbox		
				listView.getItems().clear();
				if(mediaPlayer!=null)
				{
					playList = null;
		            playList = new ArrayList<String>();
				}
				sldb = new TimeSlider_builder();                                    //set the mediaPlayer object to a mediaView object
				listView.getItems().add(buttons.getMediaName(mediaPlayer));
								
					TTime += mediaPlayer.getTotalDuration().toHours();
					songs++;
				
				playpauseButton.setGraphic(buttons.pauseView());
				label.setText(buttons.getMediaName(mediaPlayer));
				sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
				Tracks.setText("Tracks : "+songs);
				}
					
			
			catch(NullPointerException ex)
			{
				//System.out.println("No File selected");
			}			
		});
		playpauseButton.setOnAction(e->          //when the button clicked
		{	
			try{
				if(mediaPlayer==null)
					mediaPlayer = mp;
			buttons.playPauseEvent(mediaPlayer,playpauseButton);
			label.setText(buttons.getMediaName(mediaPlayer));		
			}
			catch(NullPointerException exp)
			{
				if(mediaPlayer!=null)
					{
					mediaPlayer.pause();
				    mediaPlayer.play();
					}
				else
				{
					try{				
						mediaPlayer = buttons.openFile(e,mediaPlayer);
						media = buttons.getMedia();			
						MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
						hbox1.getChildren().add(mediaView); //add the media view object to the hbox		
						listView.getItems().clear();
						sldb = new TimeSlider_builder();                                    //set the mediaPlayer object to a mediaView object
						listView.getItems().add(buttons.getMediaName(mediaPlayer));
						playpauseButton.setGraphic(buttons.pauseView());
						label.setText(buttons.getMediaName(mediaPlayer));
						sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work           
						}
							
					
					catch(NullPointerException ex)
					{
					}		
				}
			}
		});
		forwardButton.setOnAction(e->{
			try{
				
					if (count < playList.size()-1)
				 {
			            count++;
			            mediaPlayer.stop();
			            path = playList.get(count);
			            File f = new File(path);
			            media = new Media(f.toURI().toString());
			            mediaPlayer = new MediaPlayer(media);
			            mediaPlayer.play();
			            label.setText(buttons.getMediaName(mediaPlayer));
			            
			            playpauseButton.setGraphic(buttons.pauseView());
			            sldb = new TimeSlider_builder();  
			            sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work			            
				}				 
             			 mediaPlayer.setOnEndOfMedia(new Runnable() {			
						@Override
						public void run() {
						// TODO Auto-generated method stub
							mediaPlayer.stop();
							try{
							path = playList.get(count+1);
							media = new Media(new File(path).toURI().toString());
					        mediaPlayer = new MediaPlayer(media);
					        mediaPlayer.play();
					        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
							}
							catch(Exception ex)
							{
								System.out.println("Exception occures ");
							}
						}
					});
				
			}		
				catch(NullPointerException exp)
				{
					//System.out.println("No File Is Open");
				}			
		});
		
		
		backwardButton.setOnAction(e->              //when the button clicked
		{
			try{
				
				if (count > 0) {
		            --count;
		            mediaPlayer.stop();
		            path = playList.get(count);
		            media = new Media(new File(path).toURI().toString());
		            mediaPlayer = new MediaPlayer(media);
		            mediaPlayer.play();
		            label.setText(buttons.getMediaName(mediaPlayer));
		            
		            playpauseButton.setGraphic(buttons.pauseView());
		            sldb = new TimeSlider_builder();  
		            sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 		            
			        }
				 mediaPlayer.setOnEndOfMedia(new Runnable() {			
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mediaPlayer.stop();
							path = playList.get(count+1);
							media = new Media(new File(path).toURI().toString());
					        mediaPlayer = new MediaPlayer(media);
					        
					        mediaPlayer.play();
					        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
						}
					});
				
				
			}
			catch(NullPointerException exp)
			{
				//System.out.println("No File Is Open");
			}
			});
		
			stopButton.setOnAction(e-> {                //when the button clicked
			try{
				buttons.stopEvent(e,mediaPlayer);
			playpauseButton.setGraphic(playView);
			label.setText("WMA Player");
			}
			catch(NullPointerException exp)
			{
			    //System.out.println("No File Is Open");
			}
			});		
		repeat.setOnAction(e-> {            //when the button clicked						
			try{
			buttons.repeatEvent(e,mediaPlayer);	
			}
			catch(NullPointerException exp)
			{
				//System.out.println("No File Is Open");
			}
			});
		randomButton.setOnAction(e->{
			try{				
				mediaPlayer.stop();
				int rand = random.nextInt(playList.size()-1);
	            String path = playList.get(rand);
	            media = new Media(new File(path).toURI().toString());
	            mediaPlayer = new MediaPlayer(media);
	            mediaPlayer.play();
	            playpauseButton.setGraphic(buttons.pauseView());
	            sldb = new TimeSlider_builder();  
	            label.setText(buttons.getMediaName(mediaPlayer));
	            sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
				}
				catch(NullPointerException exp)
				{
					//System.out.println("No File Is Open");
				}
			
		});
		Tracks.setOnMouseClicked(e->{
			if(trackFlag)
			{
				Tracks.setText("Tracks : "+songs);
				trackFlag = false;
			}
			else
			{
				Tracks.setText("Time : "+TTime);
				trackFlag = true;
			}
		});
		
		Tracks.setText("Tracks : "+songs);
		//Time.setText("Time : "+TTime);
		//Tracks.setStyle("");
		Tracks.setId("Tracks");
		//Time.setId("Time");
		HBox hbox = TimeSlider_builder.initView();
		BorderPane.setAlignment(hbox, Pos.CENTER);		
		BorderPane bpane = new BorderPane();
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		label.setAlignment(Pos.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setId("label");
		vbox1.getChildren().add(bar);		
		vbox2.getChildren().add(label);
		//BorderPane.setAlignment(Tracks,Pos.BASELINE_LEFT);
		//Tracks.setAlignment(Pos.BASELINE_LEFT);
		Tracks.setAlignment(Pos.CENTER);
		Tracks.setMaxWidth(Double.MAX_VALUE);
		//BorderPane.setAlignment(Time,Pos.CENTER_RIGHT);
		
		//hbox2.getChildren().add(Tracks);
		
		//vbox2.getChildren().add(hbox2);
		vbox2.getChildren().add(Tracks);
		vbox2.getChildren().addAll(hbox,hbox1);
		bpane.setId("bpane");
		bar.setId("bar");
		bpane.setTop(vbox1);
		bpane.setBottom(vbox2);
		bpane.setCenter(listView);		
		
		scene = new Scene(bpane,500,400); //add the hbox to the scene
		//primaryStage.setFullScreen(true);
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(500);
		//primaryStage.initStyle(StageStyle.UTILITY); //to hide the maximize and minimize button in the window
		volumeSlider.setValue(volumeSlider.getMax());
		scene.setOnKeyPressed(e->{
			try{
			keyboard.volum1(e, volumeSlider, mediaPlayer); 
			}
			catch(Exception ex)
			{}
			});
		volumeSlider.setOnDragDetected(e->{
			try{
			keyboard.volum2(e, volumeSlider, mediaPlayer);
			}
			catch(Exception ex)
			{				
			}
		});
	
positionSlider.setOnKeyPressed(e->		
		{
			try{
				keyboard.seek(e, mediaPlayer);
			}
				catch(NullPointerException exp)
			{
					System.out.println("No File Is Open");
				}
		});
positionSlider.setOnMouseDragged(e->		
{
	try{
		keyboard.dragPosition(e, positionSlider, mediaPlayer);
	}
		catch(NullPointerException exp)
		{
			System.out.println("No File Is Open");
		}
});
		primaryStage.setTitle("WMA Player");//set the stage Title
		initSceneDragAndDrop(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);       //set the scene to the stage
		
		primaryStage.show();                //show the stage
		
	}
	/**
	 * This function show the dialog to choose directory from ,
	 * and does the functions of the open directory button event
	 * 
	 */
	public void chooseDirec()
	{
		try{
		DirectoryChooser dirch = new DirectoryChooser();
		file = dirch.showDialog(null);
		if (mediaPlayer != null) 
	    {
            mediaPlayer.stop();
            playList = null;
            playList = new ArrayList<String>();
            
        }			 
		
		sldb = new TimeSlider_builder();
		listView.getItems().clear();
			getFiles(file);
			count=0;
			if(playList.size()>0)
			{
				
			path = playList.get(count);   			
			media = new Media(new File(path).toURI().toString());
	        mediaPlayer = new MediaPlayer(media);
	        mediaPlayer.play();
	        playpauseButton.setGraphic(buttons.pauseView());
	        label.setText(buttons.getMediaName(mediaPlayer));
	        sldb = new TimeSlider_builder();  
            sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
		 mediaPlayer.setOnEndOfMedia(new Runnable() {			
				@Override
				public void run() {
					// TODO Auto-generated method stub						
					mediaPlayer.stop();
					count++;
					path = playList.get(count);
					media = new Media(new File(path).toURI().toString());
			        mediaPlayer = new MediaPlayer(media);
			        mediaPlayer.play();
			        label.setText(buttons.getMediaName(mediaPlayer));
			        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
			       
			        label.setText(buttons.getMediaName(mediaPlayer));
				}
			});	
			}
			
			else
			 throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
			
		}
		catch(NullPointerException ex)
		{
			if(mediaPlayer!=null)
			{
				mediaPlayer.stop();
			}
			label.setText("WMA Player");
			playpauseButton.setGraphic(buttons.playView());
			mediaPlayer=null;
		}
	}
	/**
	 * @param File root
	 * this method take a file as a parameter and check if the the parameter is file or directory
	 * and add files found in the directory and sub directory to the playList to be run
	 */
	public void getFiles(File root)
	{
		for(File f:root.listFiles())
		{
			if(f.isDirectory())
			{
				getFiles(f);
			}
			else if(f.isFile())
			{
				path = f.getAbsolutePath();
			if(path.endsWith(".mp3"))
				{										
				 listView.getItems().add(f.getName());
				 playList.add(path);				 
				}
			}
			
			
		}	
	}
	private void initSceneDragAndDrop(Scene scene) {
		scene.setOnDragOver(event -> {
		Dragboard db = event.getDragboard();
		if (db.hasFiles() || db.hasUrl()) {
		event.acceptTransferModes(TransferMode.COPY);
		}
		event.consume();
		});
		scene.setOnDragDropped(event -> {
			if(mediaPlayer!=null)
				mediaPlayer.stop();
			
			listView.getItems().clear();
			sldb = new TimeSlider_builder();  
		Dragboard db = event.getDragboard();
		String url = null;

		if (db.hasFiles()) {
			for(File f:db.getFiles())
				{
				playList.add(f.getAbsolutePath());
				listView.getItems().add(f.getName());
				}
		//url = db.getFiles().get(0).toURI().toString();
		} 
		else if (db.hasUrl()) {
		url = db.getUrl();
		}
		if (playList != null) 
		{
			File f = new File(playList.get(0));
			media = new Media(f.toURI().toString());			
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			playpauseButton.setGraphic(buttons.pauseView());
			label.setText(buttons.getMediaName(mediaPlayer));
			sldb.update(mediaPlayer,positionSlider,currentTimeLabel);
			
			mediaPlayer.setOnEndOfMedia(new Runnable() {			
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mediaPlayer.stop();
					path = playList.get(count+1);
					media = new Media(new File(path).toURI().toString());
			        mediaPlayer = new MediaPlayer(media);
			        label.setText(buttons.getMediaName(mediaPlayer));
			        mediaPlayer.play();
			        playpauseButton.setGraphic(buttons.pauseView());
			        sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
				}
			});
		}			
		event.setDropCompleted(url != null);
		event.consume();		
		});		
		}
	/**
	 * @return the currentTimeLabel
	 */
	public static Label getCurrentTimeLabel() {
		return currentTimeLabel;
	}
	/**
	 * @return the volumeSlider
	 */
	public static Slider getVolumeSlider() {
		return volumeSlider;
	}
	/**
	 * @return the positionSlider
	 */
	public static Slider getPositionSlider() {
		return positionSlider;
	}
	/**
	 * @return the media
	 */
	public Media getMedia() {
		return media;
	}
	/**
	 * @return the mediaPlayer
	 */
	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	/**
	 * @return the mp
	 */
	public MediaPlayer getMp() {
		return mp;
	}
	/**
	 * @return the playpauseButton
	 */
	public Button getPlaypauseButton() {
		return playpauseButton;
	}
	/**
	 * @return the forwardButton
	 */
	public Button getForwardButton() {
		return forwardButton;
	}
	/**
	 * @return the backwardButton
	 */
	public Button getBackwardButton() {
		return backwardButton;
	}
	/**
	 * @return the stopButton
	 */
	public Button getStopButton() {
		return stopButton;
	}
	/**
	 * @return the randomButton
	 */
	public Button getRandomButton() {
		return randomButton;
	}
	/**
	 * @return the repeat
	 */
	public Button getRepeat() {
		return repeat;
	}
	/**
	 * @return the open
	 */
	public Button getOpen() {
		return open;
	}
	/**
	 * @param currentTimeLabel the currentTimeLabel to set
	 */
	public static void setCurrentTimeLabel(Label currentTimeLabel) {
		Main.currentTimeLabel = currentTimeLabel;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(Media media) {
		this.media = media;
	}
	/**
	 * @param mediaPlayer the mediaPlayer to set
	 */
	public static void setMediaPlayer(MediaPlayer mediaPlayer) {
		Main.mediaPlayer = mediaPlayer;
	}
	/**
	 * @param mp the mp to set
	 */
	public void setMp(MediaPlayer mp) {
		this.mp = mp;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
		
	}	
}
