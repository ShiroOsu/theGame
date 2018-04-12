package backgrounds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.*;

public class Background {
	private double x, y;

	private BufferedImage image;


	public Background(String path){
		try{			
			image = ImageIO.read(getClass().getResourceAsStream(path));

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/*
	 * Sets the position of the image it should be rendered (in chase it would be needed)
	 */
	public void setPosition(double x, double y){
		this.x = (x % Game.WIDTH);
		this.y = (y % Game.HEIGHT);

	}

	public void update(){

	}

	public void render(Graphics g){
		g.drawImage(image, (int)x, (int)y, null);

	}
}
