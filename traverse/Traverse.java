///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Traverse.java
// Purpose:	Is the driver for the Traverse video game program.
//
// Limitations:	
//
// Development Computer: HP Pavilion HPE Series [h8-1124]
// Operating System: PopOS
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: javac Traverse.java GameController.java Board.java Piece.java Circle.java Square.java HorzTriangle.java VertTriangle.java Diamond.java
// Operational Status: Fully operational
///////////////////////////////////////////////////////////////////

public class Traverse {
	static GameController gameController;
	static Board board;
	
	// Runs gameController
	public static void main(String[] args) {
		board = new Board();
		gameController = new GameController(board);
		
		gameController.run();
	}

}
