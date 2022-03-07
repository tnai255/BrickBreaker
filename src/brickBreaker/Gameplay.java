package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

// JPanel makes Gameplay a panel
// KeyListener from moving arrow keys and ActionLister from moving the ball
public class Gameplay extends JPanel implements KeyListener, ActionListener {

	// stating game should not play by itself
	private boolean play = false;
	// initialising score to zero
	private int score = 0;

	// number of Bricks (7 x 3 grid hence 21)
	private int totalBricks = 21;

	private Timer timer;
	// speed of the ball
	private int delay = 8;

	// starting position of slider
	private int playerX = 310;

	// starting position of ball
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private MapGenerator map;

	// adding a constructor to add the above values when the object will be created
	// in main
	public Gameplay() {

		// creating new object map with rows 3 and col 7
		map = new MapGenerator(3, 7);

		// key listener properties
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

	}

	// Adding ball, slider, bricks etc.
	public void paint(Graphics g) {

		// background
		g.setColor(new Color(218, 179, 255));
		g.fillRect(1, 1, 700, 592);

		// drawing map
		map.draw((Graphics2D) g);

		// borders
		g.setColor(new Color(131, 0, 179));
		g.fillRect(0, 0, 5, 590);
		g.fillRect(0, 0, 695, 5);
		g.fillRect(695, 0, 5, 590);

		// score
		g.setColor(new Color(131, 0, 179));
		g.setFont(new Font("sans serif", Font.BOLD, 35));
		g.drawString("" + score, 635, 40);

		// the slider
		g.setColor(new Color(64, 0, 128));
		g.fillRect(playerX, 550, 100, 8);

		// the ball
		g.setColor(new Color(0, 255, 0));
		g.fillOval(ballposX, ballposY, 20, 20);

		// if game finished i.e user won
		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(new Color(131, 0, 179));
			g.setFont(new Font("sans serif", Font.BOLD, 35));
			g.drawString("CONGRATS YOU WON!", 140, 350);

			g.setFont(new Font("sans serif", Font.BOLD, 25));
			g.drawString("PRESS ENTER TO RESTART", 190, 400);
		}

		// user lost
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(new Color(131, 0, 179));
			g.setFont(new Font("sans serif", Font.BOLD, 35));
			g.drawString("GAME OVER! SCORE: " + score, 140, 350);

			g.setFont(new Font("sans serif", Font.BOLD, 25));
			g.drawString("PRESS ENTER TO RESTART", 190, 400);

		}

		g.dispose();

	}

	// Adding the KeyListener and ActionListner Methods
	@Override
	public void actionPerformed(ActionEvent e) {
		// resets slider after moving left or right
		timer.start();
		if (play) {

			// intersect of slider and ball
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}

			// intersect of ball and brick
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}

							// taking out of both for loops
							break A;
						}
					}
				}
			}

			// ball interacting with background (top, left, right)
			ballposX += ballXdir;
			ballposY += ballYdir;

			if (ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if (ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}

		repaint();
	}

	// not necessary but will give an error if removed
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		// detecting right key
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// checking it stays within border
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		// left key
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {

				// reset
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);

				repaint();

			}
		}
	}

	// creating moving particular directions method for slider
	public void moveRight() {
		// set to false initially
		play = true;
		// moving slider 20 pixels to the right
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

}
