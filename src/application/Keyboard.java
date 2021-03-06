package application;

import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class Keyboard extends Button_Builder{
	
	public Keyboard()
	{		
	}
	public void volum1(KeyEvent e,Slider volumeSlider,MediaPlayer mediaPlayer ) {
try{
		if(e.getCode().equals(KeyCode.UP) || e.getCode().equals(KeyCode.KP_UP))
				{			
			volumeSlider.setValue(volumeSlider.getValue()+0.01);
			mediaPlayer.setVolume(volumeSlider.getValue()+0.01);
			
				}
		if(e.getCode().equals(KeyCode.DOWN) || e.getCode().equals(KeyCode.KP_DOWN))
		{ 
			volumeSlider.setValue(volumeSlider.getValue()-0.01);
			mediaPlayer.setVolume(volumeSlider.getValue()-0.01);
		
		}
		if(volumeSlider.getValue()==0)
			mediaPlayer.setVolume(0.0);
		
}
catch(NullPointerException ex)
{
	System.out.println("No File is open");
}
	}


public void volum2(MouseEvent e,Slider volumeSlider,MediaPlayer mediaPlayer)
{	
	try{
		if(volumeSlider.getValue()==0)
mediaPlayer.setVolume(0.0);
else if (e.isDragDetect())
{
	volumeSlider.setValue(volumeSlider.getValue()-0.01);
    mediaPlayer.setVolume(volumeSlider.getValue()-0.01);}
else 
{
	volumeSlider.setValue(volumeSlider.getValue()+0.01);	
    mediaPlayer.setVolume(volumeSlider.getValue()+0.01);
}
	}
	catch(NullPointerException ex)
	{
	//System.out.println("No File is open");	
	}
	}
public void dragPosition(MouseEvent e,Slider slider,MediaPlayer mediaPlayer)
{		
    if (e.isDragDetect())
{
	slider.setValue(slider.getValue()-0.01);
	Duration du = mediaPlayer.getCurrentTime();
    mediaPlayer.seek(du.subtract(new Duration(0.01)));
    }
else 
{
	slider.setValue(slider.getValue()+0.01);	
	Duration du = mediaPlayer.getCurrentTime();
    mediaPlayer.seek(du.add(new Duration(0.01)));
}
	
	}
public void seek(KeyEvent key , MediaPlayer mediaPlayer)
{
	try{
	if(key.getCode()== KeyCode.CONTROL.RIGHT)
	{
		Duration x=mediaPlayer.getTotalDuration().multiply(0.05).add(mediaPlayer.getCurrentTime());
		mediaPlayer.seek(x);
	}
	else if (key.getCode()== KeyCode.CONTROL.LEFT){
		Duration x=mediaPlayer.getTotalDuration().multiply(-0.05).add(mediaPlayer.getCurrentTime());
		mediaPlayer.seek(x);					
	}
	}
	catch(NullPointerException e)
	{
		System.out.println("No File is open");
	}
}
	
}
