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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration; 
/**
* @authors 
* 1-Walid Mohammed Ali Mohammed
* 2-Moustafa Abu El Wafa Moustafa
* 3-Ahmed Bahy Edin Mohammed
* 4-Walid Mohammed Osman 
*/
public class Main extends Application
{
/**
 * TSB object of TimeSlider_builder class to access its method 
 * and set some Actions of The sliders 
 */

private TimeSlider_builder TSB;
/**
 * buttons is an object of Button_Builder class to access its methods 
 * and initiate all the buttons in the scene 
 */
private final Button_Builder buttons = new Button_Builder();
/**
 * keyboard is an object of Keyboard class to access its methods 
 * which care about handling the action of pressed keyboard key  
 */
private final Keyboard keyboard = new Keyboard();
/**
* scene is the window that hold all nodes 
*/
private  Scene scene;
/**
* label hold default value "WMA Player" in static case and
*  hold the media name in the dynamic one 
*/
Label label = new Label("WMA Player");
/**
* random variable the helps to make the medias playing randomly 
*/
private final Random random = new Random();

/** @Description xxxxxxxxxx
 * path : hold the Path of the current media 
 */
private String path="";
 /**
 *  @Description xxxxxxxxxx
 * currentTimeLabel : Label in the down-left side of the scene 
 * that show the timer of the media duration 
 */
static Label currentTimeLabel; 
 /**
 * volumeSlider : Slider for Showing and changing the volume value 
 */

static Slider volumeSlider ;
/**
 * positionSlider : Slider for Showing and seeking the media timer
 */
static Slider positionSlider;
/**
 * media : is an object of the media class that is loaded by the media path
 */
private  Media media;
/**
 * mediaPlayer : is an object of the mediaplayer class that is loaded by the media object 
 * holding the songe 
 */
static MediaPlayer mediaPlayer ;
/**
 * listView : list of name of the current medias 
 */
private ListView<String>listView = new ListView<String>();
/**
* playList : list that hold the pathes of the current medias
*/
private  ArrayList<String>playList = new ArrayList<String>();
/**
* mp : aux in playpause button 
*/
private  MediaPlayer mp;

/**
 * playpauseButton : object of button class that iniate a button for play/pause the current media
 */
private final Button playpauseButton = buttons.playPause();

/**
 *  forwardButton : object of button class that iniate a button for forward seek of the current media
 */
private final Button forwardButton = buttons.forwardButton();

/**
 * backwardButton : object of button class that iniate a button for back seek of the current media
 */
private final Button backwardButton = buttons.backwardButton();

/**
 * stopButton : object of button class that iniate a button for stoping the current media
 */
private final Button stopButton = buttons.stopButton();

/**
 *  randomButton : object of button class that iniate a button for random repeating
 *  of the current media list
 */
private final Button randomButton = buttons.randomButton();

/**
 *  repeat : object of button class that iniate a button for repeating the current media 
 */
private final Button repeat = buttons.reloadButton();
/**
 * open : object of button class that iniate a button for opening a media
 */
private final Button open = buttons.open();
/**
 * get an image view on button (getting play.png image)
 */
private final ImageView playView = buttons.playView();
/**
 * hbox1 : pane that hold the medai names horozentally in shape of list
 */
private final HBox hbox1 = new HBox();

HBox hbox;
/**
 * meneu : object of menu to set a drop down menu with two items folder and file chooser
 */
Menu menu = new Menu("File");
/**
 * item2 : menue item to choose a file of the  media
 */
MenuItem item1 = new MenuItem("Open File");
/**
 * item2 : menue item to choose a folder that holde more than one media 
 */
MenuItem item2 = new MenuItem("Open Folder");
/**
 * 
 */
MenuItem item3 = new MenuItem("Exit");

 /**
 * 
 */

 /**
 * bar : MenuBar object that create bar that hold the menu
 */
MenuBar bar ;
/**
 * count : counter for playing list of songs 
 */
int count =0;
private BorderPane bpane = new BorderPane();
int j;
int i;

String song;
int songs = 0;
Label Tracks  = new Label();
//Label Time = new Label();
int TTime;
boolean timeFlag = true;
boolean trackFlag;
private File file;
String hms;
long millis;
 
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
	    menu.getItems().add(item1);
	    menu.getItems().add(item2);
	    menu.getItems().add(item3);
	    	    
	   
	    
	    
	    bar = new MenuBar(menu);	      
	    
		
	    hbox = TimeSlider_builder.initView();
		primaryStage.getIcons().add(new Image("file:logo.png"));
		hbox1.setMinWidth(100);
		hbox1.setMaxWidth(Double.MAX_VALUE);
		hbox1.getChildren().addAll(repeat,open,backwardButton,playpauseButton,forwardButton,stopButton,randomButton);			
		hbox1.setAlignment(Pos.CENTER);       //align the hbox to the center		
		//working on it but it does not work now
				listView.setStyle("-fx-cell-hover-color:#0093ff; -fx-text-fill:black");
		
		item1.setOnAction(e->{
			try{
				songs=0;
				millis = 0;
				chooseFile();	
				for(i=0;i<playList.size();i++)
				{
				songs++;
				Media m = new Media(new File(playList.get(i)).toURI().toString());
				MediaPlayer mp = new MediaPlayer(m);
				 mp.setOnReady(new Runnable() {			
					@Override
					public void run() 
					{
						// TODO Auto-generated method stub
						 millis += (long) m.getDuration().toMillis();
						 hms = TimeConv(millis);
					}
				});
				}
				playpauseButton.setGraphic(buttons.pauseView());
				label.setText(buttons.getMediaName(mediaPlayer));
				TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
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
				millis = 0;
				chooseDirec();
				for(i=0;i<playList.size();i++)
					{
					songs++;
					Media m = new Media(new File(playList.get(i)).toURI().toString());
					MediaPlayer mp = new MediaPlayer(m);
					 mp.setOnReady(new Runnable() {			
						@Override
						public void run() 
						{
							// TODO Auto-generated method stub
							 millis += (long) m.getDuration().toMillis();
							 hms = TimeConv(millis);
						}
					});
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
item3.setOnAction(e->{
	 System.exit(0);
});

listView.setOnMouseClicked(e->{
			
			count = listView.getSelectionModel().getSelectedIndex();			
				try{
						if(mediaPlayer!=null)
							mediaPlayer.stop();
				playpauseButton.setGraphic(buttons.pauseView());
				TSB = new TimeSlider_builder();
				path = playList.get(count);   			
				media = new Media(new File(path).toURI().toString());
		        mediaPlayer = new MediaPlayer(media);
		        mediaPlayer.play();
		       
		        label.setText(buttons.getMediaName(mediaPlayer));
		        TSB = new TimeSlider_builder();  
	            TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
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
				        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
				       
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
		TSB = new TimeSlider_builder();
		path = playList.get(count);   			
		media = new Media(new File(path).toURI().toString());
       mediaPlayer = new MediaPlayer(media);
       mediaPlayer.play();
      
       label.setText(buttons.getMediaName(mediaPlayer));
       TSB = new TimeSlider_builder();  
       TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
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
		        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
		       
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
				millis = 0;
				chooseFile();	
				for(i=0;i<playList.size();i++)
				{
				songs++;
				Media m = new Media(new File(playList.get(i)).toURI().toString());
				MediaPlayer mp = new MediaPlayer(m);
				 mp.setOnReady(new Runnable() {			
					@Override
					public void run() 
					{
						// TODO Auto-generated method stub
						 millis += (long) m.getDuration().toMillis();
						 hms = TimeConv(millis);
					}
				});
				}
				playpauseButton.setGraphic(buttons.pauseView());
				label.setText(buttons.getMediaName(mediaPlayer));
				TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
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
						songs=0;
						millis = 0;
						chooseFile();	
						for(i=0;i<playList.size();i++)
						{
						songs++;
						Media m = new Media(new File(playList.get(i)).toURI().toString());
						MediaPlayer mp = new MediaPlayer(m);
						 mp.setOnReady(new Runnable() {			
							@Override
							public void run() 
							{
								// TODO Auto-generated method stub
								 millis += (long) m.getDuration().toMillis();
								 hms = TimeConv(millis);
							}
						});
						}
						playpauseButton.setGraphic(buttons.pauseView());
						label.setText(buttons.getMediaName(mediaPlayer));
						TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
						Tracks.setText("Tracks : "+songs);          
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
			            TSB = new TimeSlider_builder();  
			            TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work			            
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
					        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
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
		            TSB = new TimeSlider_builder();  
		            TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 		            
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
					        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
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
	            TSB = new TimeSlider_builder();  
	            label.setText(buttons.getMediaName(mediaPlayer));
	            TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
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
				Tracks.setText("Time : "+hms);
				trackFlag = true;
			}
		});
		
		volumeSlider.setOnDragDetected(e->{
			try{
			keyboard.volum2(e, volumeSlider, mediaPlayer);
			}
			catch(Exception ex)
			{				
			}
		});
		// Mouse Actions On Both Position And Volume Sliders 

				positionSlider.setOnMouseClicked(e->		
				{
					try{
						positionSlider.setValue(positionSlider.getValue());
						Duration du = mediaPlayer.getCurrentTime();
					    mediaPlayer.seek(du.subtract(new Duration(1)));
						positionSlider.setOnMouseDragged(c->{
							mediaPlayer.seek(mediaPlayer.getTotalDuration()
					                .multiply(positionSlider.getValue()));
							
						});
					}
						catch(NullPointerException exp)
						{
							System.out.println("No File Is Open");
						}
				});
				positionSlider.setOnKeyPressed(e->{
					keyboard.seek(e, mediaPlayer);
				});
				positionSlider.setOnMousePressed(e->{
					mediaPlayer.seek(mediaPlayer.getTotalDuration()
			                .multiply(positionSlider.getValue()));
					
				});
volumeSlider.setOnMouseDragged(e->{
	
	mediaPlayer.setVolume(volumeSlider.getValue());
	
});
volumeSlider.setOnMouseDragged(e->{
	
	mediaPlayer.setVolume(volumeSlider.getValue());
	
});

		Tracks.setText("Tracks : "+songs);
		Tracks.setId("Tracks");
		
		BorderPane.setAlignment(hbox, Pos.CENTER);		
		
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		label.setAlignment(Pos.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setId("label");
		vbox1.getChildren().add(bar);		
		vbox2.getChildren().add(label);
		Tracks.setAlignment(Pos.CENTER);
		Tracks.setMaxWidth(Double.MAX_VALUE);
	
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
		
		primaryStage.setTitle("WMA Player");//set the stage Title
		initSceneDragAndDrop(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);       //set the scene to the stage
		
		primaryStage.show();                //show the stage
		scene.setOnKeyPressed(e->{
			try{
			keyboard.volum1(e, volumeSlider, mediaPlayer); 
			}
			catch(Exception ex)
			{}
			});
	}
	public static String TimeConv(long milliseconds) {
        String format = String.format("%%0%dd", 2);
        long elapsedTime = milliseconds / 1000;
        String seconds = String.format(format, elapsedTime % 60);
        String minutes = String.format(format, (elapsedTime % 3600) / 60);
        String hours = String.format(format, elapsedTime / 3600);
        String time =  hours + ":" + minutes + ":" + seconds;
        return time;
    }
	public void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		try{
		 fileChooser.setTitle("Open File");
		 fileChooser.getExtensionFilters().add(new ExtensionFilter("Media Files","*.mp3"));
		File file = fileChooser.showOpenDialog(null);     //open an dialog to choose from	 
		if (mediaPlayer != null) 
	    {
            mediaPlayer.stop();
            playList = null;
            playList = new ArrayList<String>();          
        }
		TSB = new TimeSlider_builder();
		listView.getItems().clear();
		getFile(file);
		if(playList.size()>0)
		{			
		path = playList.get(0);  			
		media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);	         //set autoPlay to true	
        playpauseButton.setGraphic(buttons.pauseView());
        label.setText(buttons.getMediaName(mediaPlayer));
        TSB = new TimeSlider_builder();  
        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
        
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
		
		TSB = new TimeSlider_builder();
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
	        TSB = new TimeSlider_builder();  
	        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
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
			        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work 
			       
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
	public void getFile(File root)
	{
		
		 if(root.isFile())
			{
				path = root.getAbsolutePath();
			if(path.endsWith(".mp3"))
				{										
				 listView.getItems().add(root.getName());
				 playList.add(path);				 
				}
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
			TSB = new TimeSlider_builder();  
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
			TSB.update(mediaPlayer,positionSlider,currentTimeLabel);
			
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
			        TSB.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work  
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
