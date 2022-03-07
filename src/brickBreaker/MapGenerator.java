package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

	public int map[][];
	public int brickWidth;
	public int brickHeight;

	// mapping the bricks
	public MapGenerator(int row, int col) {

		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				// this brick has not been intersected with the ball
				map[i][j] = 1;
			}
		}

		brickWidth = 540 / col;
		brickHeight = 150 / row;
	}

	// bricks drawn where there is a value of 1
	public void draw(Graphics2D g) {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {

				// creating brick
				if (map[i][j] > 0) {
					g.setColor(new Color(64, 0, 128));
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

					// brick outline
					g.setStroke(new BasicStroke(3));
					g.setColor(new Color(218, 179, 255));
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}

	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}

}
