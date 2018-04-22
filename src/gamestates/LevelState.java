package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import audio.GameAudio;
import backgrounds.Background;
import entities.Enemy;
import entities.Entity;
import entities.LivingBackground;
import entities.Player;
import hud.Hud;
import main.*;
import projectiles.*;
/**
 * 
 * @author Niklas Granskog
 *
 */
public class LevelState extends GameState {
	//fields
	
	private Background bg;
	private Player player;
	private BulletManager bulletmanager;
	private Hud playerHud;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Bullet> bullets = new ArrayList<>();
	private int enemyCount = 0;
	private static int enemyKilled = 0;
	private int health = 100;
	private long t;
	private GameAudio gaKilled, gaOut, gaExit;

	private LivingBackground livingBG;

	public LevelState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	/*
	 * Adds an enemy
	 */
	public void addEnemy(int x, int y, int width, int height, int speed){
		enemies.add(new Enemy(Entity.ENEMY, x, y, width, height, speed));
		enemyCount++;
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount += enemyCount;
	}

	public static int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		LevelState.enemyKilled += enemyKilled;
	}

	@Override
	public void init() {
		
		health = 100;
		player = new Player(loadImage("/pictures/SpaceShip.png"), Game.WIDTH/2 - 40, 550, 80, 80, 6);
		playerHud = new Hud(0, 600);
		bg = new Background("/pictures/levelbg.png");
		bulletmanager = new BulletManager(player);
		bulletmanager.setImage(Entity.BULLET);
		enemies.clear();	// so there should not be any enemies before the game starts
		
		gaKilled = new GameAudio("src/audiofiles/killed.mp3");
		gaOut = new GameAudio("src/audiofiles/killed.mp3");
		gaExit = new GameAudio("src/audiofiles/GameOver.wav");

		t = 0;

		livingBG = new LivingBackground(.8, 10);
		
	}

	@Override
	public void update() {
		livingBG.update();
		bullets = bulletmanager.getBulletArray();

		bg.update();
		player.update();
		bulletmanager.update();
		playerHud.update();
		setEnemyCount(enemies.size());
		t++;
		
		

		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
			enemies.get(i).move();
		}
		

		for(Enemy e : enemies)
		{
			
			for(Bullet b : bullets)
			{
				if(b.intersects(e))
				{
					//if enemy is hit
					e.remove();
					try {
						gaKilled.playSound();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					b.remove();
					setEnemyKilled(1);
					
					

				}
			}
			if(player.intersects(e))
			{
				//if player is hit by enemy
				health -= 20;
				e.remove();
				setEnemyKilled(1);
				
				
			}
		}

		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getY() >= Game.HEIGHT){
				enemies.get(i).remove(); //enemies that went outside of the screen
				health -= 20;
				
				try {
					gaOut.playSound();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			if(enemies.get(i).shouldBeRemoved()) {
				enemies.remove(i);
			}
		}

		for(int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).shouldBeRemoved()) {
				bullets.remove(i); //projectiles that hit the enemy
			}
		}
		/*
		 * lose / die
		 */
		if(health <= 0){
			player.setDead();
			try {
				gaExit.playSound();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			gsm.setState(GameStateManager.GAMEOVER);
		}
		/*
		 * different type of enemies set on different intervals 
		 */
		
		if(t % (60 * 2) == ((60 * 2) - 1)){
			addEnemy((int)(Math.random()*(Game.WIDTH - 80)), -100, 80, 60, 3);
			setEnemyCount(1);
			
		}
		
		if(t % (60 * 10) == ((60 * 10) - 1)){
			addEnemy((int)(Math.random()*(Game.WIDTH - 80)), -100, 100, 70, 2);
			setEnemyCount(1);
			
		}
		
		if(t % (60 * 20) == (60 * 20) - 1){
			addEnemy((int)(Math.random()*(Game.WIDTH - 80)), -50, 50, 70, 1);
			setEnemyCount(1);
			
		}
		
		if(t % (60 * 30) == (60 * 30) - 1){
			addEnemy((int)(Math.random()*(Game.WIDTH - 80)), -100, 150, 100, 3);
			setEnemyCount(1);
		}
	}

	@Override
	public void render(Graphics g) {
		bg.render(g);
		livingBG.render(g);
		player.render(g);
		bulletmanager.render(g);

		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).render(g);
		}
		
		/*
		 * prints player health
		 */
		playerHud.render(g);
		g.setColor(Color.GRAY);
		g.fillRect((int)playerHud.getX() + 104, 677, 492, 18);
		g.setColor(Color.RED);
		g.fillRect((int)playerHud.getX() + 104, 677, (int)(492*(health/100.0)), 18);
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_W){
			player.setUp(true);
		}
		if(k == KeyEvent.VK_S){
			player.setDown(true);
		}
		if(k == KeyEvent.VK_A){
			player.setLeft(true);
		}
		if(k == KeyEvent.VK_D){
			player.setRight(true);
		}
		if(k == KeyEvent.VK_F){
			player.setFiring(true);
		}
		
		if(k == KeyEvent.VK_P){
			gsm.setState(GameStateManager.PAUSESTATE);
		}
	}

	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_W){
			player.setUp(!true);
		}
		if(k == KeyEvent.VK_S){
			player.setDown(!true);
		}
		if(k == KeyEvent.VK_A){
			player.setLeft(!true);
		}
		if(k == KeyEvent.VK_D){
			player.setRight(!true);
		}
		if(k == KeyEvent.VK_F){
			player.setFiring(!true);
		}
	}

	private BufferedImage loadImage(String path){
		try{
			return ImageIO.read(getClass().getResourceAsStream(path));
		}catch (IOException e){
			System.err.println(e.getMessage());
			return null;
		}
	}
}

















