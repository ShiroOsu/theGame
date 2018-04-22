package projectiles;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.GameAudio;
import entities.Player;
import main.*;

public class BulletManager {
	
	private Player player;
	private BufferedImage image;
	private GameAudio ga;
	
	
	private ArrayList<Bullet> bullets = new ArrayList<>();
	
	public BulletManager(Player player){
		this.player = player;
		ga = new GameAudio("src/audiofiles/Firing.mp3");
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void render(Graphics g){
		/*
		 * draw the projectiles from the middle of the player
		 */
		for(int i = 0; i < bullets.size(); i++){
			g.drawImage(image, (int)bullets.get(i).getX() + 27, (int)bullets.get(i).getY() - 15,
					(int)bullets.get(i).getWidth(), (int)bullets.get(i).getHeight(), null);
		}
	}
	
	public int getBullets(){
		return bullets.size();
	}
	
	/*
	 * return a pointer of the bullet array
	 */
	public ArrayList<Bullet> getBulletArray(){
		return bullets;
	}
	
	
	/*
	 * create a bullet at x, y
	 */
	public void addBullet(int x, int y){
		bullets.add(new Bullet(x, y));
	}
	
	
	/*
	 * update the bullets
	 */
	public void update(){
		if(player.isFiring()){
			bullets.add(new Bullet((int)player.getX(), (int)player.getY()));
			try {
				ga.playSound();
			} catch (Exception e) {
				e.printStackTrace();
			}
			player.setFiring(false);	
			
		}
		/*
		 * if a bullet has gone out of the screen
		 */
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).move();
			if(bullets.get(i).getY() < 0){
				bullets.remove(i);
			}	
		}
	}
}
