///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Square.java
// Purpose:	Represent a square piece in Traverse
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

public class Square extends Piece{

	Square(short color) {
		super(color);
		
		// Set appearance
		asciiImage = new String[]{	" ___ ",
									"|   |",
									"|   |",
									"|___|",
									"_____",
									};
		assignSide(color);
		
		// Set movement pattern
				moveShape = new boolean[][]{{false,true,false},
											{true,false,true},
											{false,true,false}};

	}
	
}
