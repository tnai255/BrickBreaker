// Followed this youtube tutorial for interest sake! https://www.youtube.com/watch?v=K9qMm3JbOH0

package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		// creating the outer window
		JFrame obj = new JFrame();
		// creating an object gamePlay
		Gameplay gamePlay = new Gameplay();
		// adding gamePlay object inside JFrame
		obj.add(gamePlay);
		// setting size of window
		obj.setBounds(10, 10, 700, 600);
		// setting Title
		obj.setTitle("Brick Breaker");
		// fix size
		obj.setResizable(false);
		// set visibility
		obj.setVisible(true);
		// default when user closes operation
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
