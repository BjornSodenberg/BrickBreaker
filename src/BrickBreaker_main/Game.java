package BrickBreaker_main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	public MapGenerator map;
	
	
	public Game()
	{
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		
	}
	
	public void paint(Graphics g)
	{
		//bg
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		
		//ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		
		if(play){
			if(new Rectangle(ballposX,ballposY, 20, 20).intersects(
					new Rectangle(playerX, 550, 100,8))) {
				ballYdir = -ballYdir;
			}
			stopPoint :for(int i = 0; i <map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++ ) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth +80;
						int brickY = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							
							break stopPoint;
						}
					}
				}
			}
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void moveRight()
	{
		play = true;
		playerX+=20;
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;
	}

}
