///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Piece.java
// Purpose:	Represent an abstract piece in Traverse
//
// Limitations:	
//
// Development Computer: Framework 16
// Operating System: Ubuntu 24.04
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: Fully operational
///////////////////////////////////////////////////////////////////

public abstract class Piece {
	String[] asciiImage;
	boolean[][] moveShape;
	int[] coordinate;
	short color;
	
	// Creates a piece with a color, an x and y coordinate, and a 
	// movement shape based on its subclass and starting side of the 
	// board. It also changes the ASCII representation for the piece 
	// based on the board side and the color.
	Piece(short color){
	}
	
	// Returns the ASCII representation.
	String[] GetAscii(){
		return asciiImage;
	}
	
	void assignSide(short color) {
		switch(color) {
			case 0:
				asciiImage[2] = asciiImage[2].substring(0, 2) + "G" + asciiImage[2].substring(3);
				break;
			case 1:
				asciiImage[2] = asciiImage[2].substring(0, 2) + "R" + asciiImage[2].substring(3);
				break;
			case 2:
				asciiImage[2] = asciiImage[2].substring(0, 2) + "Y" + asciiImage[2].substring(3);
				break;
			case 3:
				asciiImage[2] = asciiImage[2].substring(0, 2) + "B" + asciiImage[2].substring(3);
				break;
		}
	}
	
	// Sets the coordinate variable to new values.
	void setCoordinate(int xCoordinate, int yCoordinate) {
		coordinate = new int[] {xCoordinate, yCoordinate};
	}
	
	// Returns the current value of the coordinate variable.
	int[] getCoordinate() {
		return coordinate;
	}
	
	// Returns the locations the piece can move to.
	boolean[][] moveShape(){
		return moveShape;
	}
}
