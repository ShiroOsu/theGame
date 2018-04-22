package hud;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import backgrounds.Background;

public class Hud {
	
	//fields
	private Background bg;
	private BufferedImage playerIcon;
	private double x, y;
	
	/*
	 * Head-up display that shows health 
	 */
	public Hud(double x, double y){
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init(){
		playerIcon = loadImage("/pictures/SpaceShip.png");
		bg = new Background("/pictures/hud.png");
		bg.setPosition(x, y);
		
	}
	
	private BufferedImage loadImage(String path){
        try{
            return ImageIO.read(getClass().getResourceAsStream(path));
        }catch (IOException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void render(Graphics g){
		bg.render(g);
		g.drawImage(playerIcon, 0, 600, 100, 100, null);
	}
	
	public void update(){
		bg.update();
	}
}
