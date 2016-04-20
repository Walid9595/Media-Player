package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class  TimeSlider_builder {
	
	private static HBox mediaBar;
	static ImageView high = new ImageView(new  Image("file:icons/high.png"));
	static ImageView low = new ImageView(new  Image("file:icons/mute.png"));
	TimeSlider_builder(){}

	static String formatTime(Duration elapsed, Duration
			duration) {
			int intElapsed = (int)Math.floor(elapsed.toSeconds());
			int elapsedHours = intElapsed / (60 * 60);
			if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
			}
			int elapsedMinutes = intElapsed / 60;
			int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
			- elapsedMinutes * 60;
			if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
			intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 *
			60 -
			durationMinutes * 60;
			if (durationHours > 0) {
			return String.format("%d:%02d:%02d/%d:%02d:%02d",
			elapsedHours, elapsedMinutes, elapsedSeconds,
			durationHours, durationMinutes, durationSeconds);
			} else {
			return String.format("%02d:%02d/%02d:%02d",
			elapsedMinutes, elapsedSeconds,durationMinutes,
			durationSeconds);
			}
			} else {
			if (elapsedHours > 0) {
			return String.format("%d:%02d:%02d", elapsedHours,
			elapsedMinutes, elapsedSeconds);
			} else {
			return
			String.format("%02d:%02d",elapsedMinutes,
			elapsedSeconds);
			}
			}
			}
	public  void update(MediaPlayer mediaPlayer,Slider positionSlider,Label currentTimeLabel ){
			if (currentTimeLabel != null ) {
			Platform.runLater(new Runnable() {
			public void run() {
				
				 mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
					 public void invalidated(Observable ov) {
				            Duration time = mediaPlayer.getCurrentTime();
				            Duration total =mediaPlayer.getTotalDuration();

				            if (!positionSlider.isValueChanging() &&
				                total.greaterThan(Duration.ZERO)){

				              positionSlider.setValue(time.toMillis() / total.toMillis() );
				            }

				            currentTimeLabel.setText(formatTime(time,total));
				        
				        }});

				  positionSlider.valueChangingProperty().addListener(new InvalidationListener() {
				        public void invalidated(Observable ov) {
				        	mediaPlayer.seek(mediaPlayer.getTotalDuration()
				                .multiply(positionSlider.getValue() / 100.0));
				        }
				    });
			
			}}
			);
			}
		}

	
	public static void volum(KeyEvent e){
		
		Main.volumeSlider.setValue(Main.volumeSlider.getMax());

			if(e.getCode().equals(KeyCode.UP) || e.getCode().equals(KeyCode.KP_UP))
					{
				
				Main.volumeSlider.setValue(Main.volumeSlider.getValue()+0.01);
				Main.mediaPlayer.setVolume(Main.volumeSlider.getValue()+0.01);
				
					}
			if(e.getCode().equals(KeyCode.DOWN) || e.getCode().equals(KeyCode.KP_DOWN))
			{ 
				Main.volumeSlider.setValue(Main.volumeSlider.getValue()-0.01);
				Main.mediaPlayer.setVolume(Main.volumeSlider.getValue()-0.01);
			
			}
			
	}		
	public static  HBox initView()  {
		mediaBar = new HBox();
		mediaBar.setAlignment(Pos.CENTER);
		mediaBar.setPadding(new Insets(5 , 10, 5,10));
		BorderPane.setAlignment(mediaBar, Pos.CENTER);
		
		Main. volumeSlider =createSlider("volumeSlider");
		Main.positionSlider = createSlider("positionSlider");
		Main.currentTimeLabel =createLabel("00:00:00", "mediaText");
			final ImageView vol = new ImageView(new Image("file:icons/vol.png"));
			vol.setId("volumeLow");
			final ImageView mute= new ImageView(new Image("file:icons/mute.png"));
			mute.setId("volumeHigh");
			final GridPane gp = new GridPane();
			/*gp.setHgap(1);
			gp.setVgap(1);
			gp.setPadding(new Insets(10));*/
			mediaBar.getChildren().add(Main.currentTimeLabel);
			HBox.setHgrow(Main.positionSlider, Priority.ALWAYS);
			Main.positionSlider.setMinWidth(50);
			Main.positionSlider.setMaxWidth(Double.MAX_VALUE);
			mediaBar.getChildren().add(Main.positionSlider);
			Main.volumeSlider.setPrefWidth(70);
			Main.volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
			Main.volumeSlider.setMinWidth(30);			
			mediaBar.getChildren().add(low);
			mediaBar.getChildren().add(Main.volumeSlider);
			mediaBar.getChildren().add(high);
			
			low.setOnMouseClicked(e->{
				try{
					Main.mediaPlayer.setVolume(0);
					Main.volumeSlider.setValue(0);
				}
				catch(NullPointerException ex)
				{
					Main.volumeSlider.setValue(0);
					//System.out.println("No file is open");
				}
			});
			
			
			high.setOnMouseClicked(e->{
				try{
					Main.mediaPlayer.setVolume(Double.MAX_VALUE);
					Main.volumeSlider.setValue(Double.MAX_VALUE);
				}
				catch(NullPointerException ex)
				{
					Main.volumeSlider.setValue(Double.MAX_VALUE);
					//System.out.println("No file is open");
				}
			});
			
			return mediaBar;
	}

	static Slider createSlider(String id) {
		final Slider slider = new Slider(0.0, 1.0, 0.1);
		slider.setId(id);
		slider.setValue(0);
		return slider;
		}
		static Label createLabel(String text, String styleClass) {
		final Label label = new Label(text);
		label.getStyleClass().add(styleClass);
		return label;
		}
	
	
}

