package audio;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

@SuppressWarnings({ "restriction" })
public class GameAudio {

	// fields
	private MediaPlayer mediaPlayer;
	private Media m;



	/**
	 * 
	 * @param path used to get a audio file
	 */
	public GameAudio(String path){
		new JFXPanel();
		m = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(m);
		mediaPlayer.setRate(1);

	}


	/*
	 * Before it tries to play the sound again the time is set to zero so the sound can be played more than once
	 */
	public void playSound() throws Exception {
		try{		
			mediaPlayer.seek(javafx.util.Duration.ZERO);
			mediaPlayer.play();

		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}
	}
}
