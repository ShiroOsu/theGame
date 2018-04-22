package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.*;
import backgrounds.Background;


public class MenuState extends GameState {
	
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {"Start", "Help", "Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	

	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		
		try{
			bg = new Background("/pictures/menubg.png");
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 50);
			font = new Font("Arial", Font.PLAIN, 30);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		init();
	}

	
	@Override
	public void init() {
	
	}

	
	@Override
	public void update() {
		bg.update();
		
	}

	
	@Override
	public void render(Graphics g) {
		bg.render(g);
		
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Shooter", Game.WIDTH/2 - 90, 80);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++){
			if(i == currentChoice){
				g.setColor(Color.CYAN);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], Game.WIDTH/2 - 30, 160 + i * 50);
		}
		
	}
	
	private void select(){
		if(currentChoice == 0){
			gsm.setState(GameStateManager.LEVEL1STATE);
			
		}
		if(currentChoice == 1){
			gsm.setState(GameStateManager.HELPSTATE);
			
		}
		
		if(currentChoice == 2){
			System.exit(0);
		}
	}

	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		
		if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length -1;
			}
		}
		
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}
	}

	
	@Override
	public void keyReleased(int k) {
		
		
	}
}
