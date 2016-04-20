package application;	
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.MapChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
//import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application 
{ 
	TimeSlider_builder sldb;
    public static Scene scene;
    Label label = new Label("WMA Player");
    	
	String path="";
	public static Label currentTimeLabel; 
	public static Slider volumeSlider ;
	public static Slider positionSlider;
	public static Duration currentTime; 
	String s="";
	public  Media media;
	public	static MediaPlayer mediaPlayer ;
	boolean repeate=false;
	public Duration duration;
   // List<Media> mlist = new ArrayList<Media>();
	//List<Label> labelList = new ArrayList<Label>();
	ArrayList<File>arrayList = new ArrayList<>();
    ListView<String>listView = new ListView<String>();
    ArrayList<MediaPlayer>playList = new ArrayList<MediaPlayer>();
	int j;
	VBox vlist = new VBox();
	@Override
	public void start(Stage primaryStage) {
		Button_Builder buttons = new Button_Builder();
		Menu menu = new Menu("Open");
		MenuItem item1 = new MenuItem("Open File");
		MenuItem item2 = new MenuItem("Open Folder");
	    menu.getItems().add(item1);
	    menu.getItems().add(item2);
	    MenuBar bar = new MenuBar(menu);
	    
      
	    
	      
		/*
		List<MediaPlayer> table = new ArrayList<MediaPlayer>();
		List<Media>list = new ArrayList<Media>();
		
		
		
		 List<Label>playList = new ArrayList<Label>();
	   for(Media m:list)
	   {
		   table.add(new MediaPlayer(m));		   
	   }
	   
	   for(MediaPlayer m:table)
	   {
		   Media mp =  m.getMedia();
		   Label l = new Label();
		   l.setText(mp.getSource());
		   playList.add(l);
	   }
	   
	   for(int i=0;i<table.size()-1;i++)
	   {
		   
		   MediaPlayer player1 = table.get(i);
		   MediaPlayer player2 = table.get(i+1);
		   player1.play();
		   mlist.add(player1.getMedia());
		   mlist.add(player2.getMedia());
		  
		   player1.setOnEndOfMedia(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				player1.stop();
				player2.play();
				
			}
		});
	   }
	   for(j=0;j<mlist.size();j++)
	   {
		   MediaView mediaV = new MediaView(new MediaPlayer(mlist.get(j)));
		   mlist.get(j).getMetadata().addListener(new MapChangeListener<String, Object>()
		   {
				@Override
				public void onChanged(Change<?extends String,?extends Object>ch) {
					// TODO Auto-generated method stub
					if(ch.wasAdded())
					{
						labelList.get(j).setText(handleList(ch.getKey(),ch.getValueAdded()));
						vlist.getChildren().add(labelList.get(j));
					}				
				}
			});
	   }*/
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color:#b0c2c7");
		
		
		
		Button open = buttons.open();
		
		Button playpauseButton = buttons.playPause();
		
		Button forwardButton = buttons.forwardButton();
		
		Button backwardButton = buttons.backwardButton();
		
		Button stopButton = buttons.stopButton();
		
		Button shulffButton = buttons.shulffButton();
		
		Button repeat = buttons.reloadButton();
		
		ImageView playView = buttons.playView();
		ImageView pauseView = buttons.pauseView();
		
		HBox hbox1 = new HBox();
		
		//set the window icon
		primaryStage.getIcons().add(new Image("file:logo.png"));
		hbox1.setMinWidth(100);
		hbox1.setMaxWidth(Double.MAX_VALUE);
		hbox1.getChildren().addAll(repeat,open,backwardButton,playpauseButton,forwardButton,stopButton,shulffButton);			
		hbox1.setAlignment(Pos.CENTER);       //align the hbox to the center		
		item1.setOnAction(e->{
			try{
				mediaPlayer = buttons.openFile(e,mediaPlayer);
				media = buttons.getMedia();
				
				MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
				hbox1.getChildren().add(mediaView); //add the media view object to the hbox		
				
				sldb = new TimeSlider_builder();                                    //set the mediaPlayer object to a mediaView object
				listView.getItems().add(buttons.getMediaName());
				/*media.getMetadata().addListener(new MapChangeListener<String, Object>(){
					@Override
					public void onChanged(Change<?extends String,?extends Object>ch) {
						// TODO Auto-generated method stub
						if(ch.wasAdded())
						{
							handleMetadata(ch.getKey(),ch.getValueAdded());
						}				
					}
				});*/
				playpauseButton.setGraphic(buttons.pauseView());
				sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work           
				}
				catch(NullPointerException ex)
				{
					System.out.println("No File selected");
				}			
		});
		int count =0;
		item2.setOnAction(e->{	   
			   
		    DirectoryChooser dirch = new DirectoryChooser();
			File file = dirch.showDialog(null);
			listView.getItems().clear();
			 Media mm=null;
			 sldb = new TimeSlider_builder();
			 
			/* for(int i=0;i<file.length();i++)
			 {
				 playList.add(file.getPath());
				 
			 }*/
			 
			 
			for(File f:file.listFiles())
			{
				path = f.getAbsolutePath();
				path = path.replace("\\","/");
				
				if(f.getName().endsWith(".mp3"))
					{						
					 mm = new Media(new File(path).toURI().toString());
					 //arrayList.add(new File(path));					
					 listView.getItems().add(f.getName());
					 playList.add(new MediaPlayer(mm));				 
					}
				
				for(int i=0;i<playList.size()-1;i++)
				   {
					   
					   MediaPlayer player1 = playList.get(i);
					   MediaPlayer player2 = playList.get(i+1);
					   player1.play();
					   playpauseButton.setGraphic(buttons.pauseView());
					   sldb.update(player1,positionSlider,currentTimeLabel);//this is work 
					   player1.setOnEndOfMedia(new Runnable() {			
						@Override
						public void run() {
							// TODO Auto-generated method stub
							player1.stop();
							player2.play();
							playpauseButton.setGraphic(buttons.pauseView());
							sldb.update(player2,positionSlider,currentTimeLabel);//this is work 	
						}
					});				 
			}		
			}
			
		});
		open.setOnAction(e->
		{			
			try{
				mediaPlayer = buttons.openFile(e,mediaPlayer);
				media = buttons.getMedia();
				MediaView mediaView = new MediaView(mediaPlayer); //set the mediaPlayer object to a mediaView object
				hbox1.getChildren().add(mediaView); //add the media view object to the hbox		
				
				sldb = new TimeSlider_builder();                                    //set the mediaPlayer object to a mediaView object
				listView.getItems().add(buttons.getMediaName());
				playpauseButton.setGraphic(buttons.pauseView());
				sldb.update(mediaPlayer,positionSlider,currentTimeLabel);//this is work   
				//playSong( mediaPlayer ,  media ,  positionSlider , currentTimeLabel);
			        
			}
			catch(NullPointerException ex)
			{
				System.out.println("No File selected");
			}			
		});
		
		playpauseButton.setOnAction(e->          //when the button clicked
		{	
			try{
			buttons.playPauseEvent(mediaPlayer,playpauseButton);
			label.setText(s);
			
			}
			catch(NullPointerException exp)
			{
				System.out.println("No File Is Open");
			}
		});
		
		forwardButton.setOnAction(e->{
			try{
				buttons.forwardEvent(e,mediaPlayer);
				}
				catch(NullPointerException exp)
				{
					System.out.println("No File Is Open");
				}			
		});
		backwardButton.setOnAction(e->              //when the button clicked
		{
			try{
				buttons.backwardEvent(e,mediaPlayer);
			}
			catch(NullPointerException exp)
			{
				System.out.println("No File Is Open");
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
				System.out.println("No File Is Open");
			}
			});		
		repeat.setOnAction(e-> {            //when the button clicked						
			try{
			buttons.repeatEvent(e,mediaPlayer);	
			}
			catch(NullPointerException exp)
			{
				System.out.println("No File Is Open");
			}
			});
		
		HBox bp = TimeSlider_builder.initView();
		BorderPane.setAlignment(bp, Pos.CENTER);
		
		BorderPane bpane = new BorderPane();
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		label.setAlignment(Pos.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		vbox2.getChildren().add(bar);
		vbox1.getChildren().add(label);
		vbox1.getChildren().addAll(bp,hbox1);
		bpane.setTop(vbox2);
		bpane.setBottom(vbox1);
		//ScrollPane spane = new ScrollPane(vlist);
		/*ScrollBar sbar = new ScrollBar();
		sbar.setOrientation(Orientation.VERTICAL);
		bpane.setRight(sbar);*/
		
		bpane.setCenter(listView);
		
		Keyboard keyboard = new Keyboard();
		scene = new Scene(bpane); //add the hbox to the scene
		
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(500);
		volumeSlider.setValue(volumeSlider.getMax());
		scene.setOnKeyPressed(e->{
			keyboard.volum1(e, volumeSlider, mediaPlayer); 
			});
		volumeSlider.setOnDragDetected(e->{
			keyboard.volum2(e, volumeSlider, mediaPlayer);
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
      
		primaryStage.setTitle("WMA Player");//set the stage Title
		initSceneDragAndDrop(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);       //set the scene to the stage
		
		primaryStage.show();                //show the stage
		
	}
	/*public MediaPlayer playMultiple(Media mm, ArrayList<MediaPlayer>Mlist)
	{
	     	
	}*/
	private void initSceneDragAndDrop(Scene scene) {
		scene.setOnDragOver(event -> {
		Dragboard db = event.getDragboard();
		if (db.hasFiles() || db.hasUrl()) {
		event.acceptTransferModes(TransferMode.COPY);
		}
		event.consume();
		});
		scene.setOnDragDropped(event -> {
		Dragboard db = event.getDragboard();
		String url = null;
		if (db.hasFiles()) {
		url = db.getFiles().get(0).toURI().toString();
		} else if (db.hasUrl()) {
		url = db.getUrl();
		}
		if (url != null) {
			media = new Media(url);
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			sldb.update(mediaPlayer,positionSlider,currentTimeLabel);
			File file = new File(url);
			listView.getItems().add(file.getName());
		}			
		event.setDropCompleted(url != null);
		event.consume();
		/*media.getMetadata().addListener(new MapChangeListener<String, Object>(){
			@Override
			public void onChanged(Change<?extends String,?extends Object>ch) {
				// TODO Auto-generated method stub
				if(ch.wasAdded())
				{
					handleMetadata(ch.getKey(),ch.getValueAdded());
				}				
			}
		});		*/
		});
		
		}
	private void handleMetadata(String key, Object value) {
		switch (key)
		{
		case "title":
			s = value.toString();
			break;
		}
		label.setText(s);
	}
	private String handleList(String key, Object value) 
	{
		switch (key)
		{
		case "title":
			s = value.toString();
			break;
		}
		return s;
	}
	public void playSong(MediaPlayer mediaPlayer , Media media , Slider positionSlider , Label currentTimeLabel)
	{
		
		
			
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
