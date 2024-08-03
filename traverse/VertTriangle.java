///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: VertTriangle.java
// Purpose:	Represent a vertical triangle piece in Traverse
//
// Limitations:	
//
// Development Computer: HP Pavilion HPE Series [h8-1124]
// Operating System: PopOS
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: Fully operational
///////////////////////////////////////////////////////////////////

public class VertTriangle extends Piece{

	VertTriangle(short color) {
		super(color);
		
		// Set appearance
		// Face Triangle to the correct side
		if(color == 0) {
			asciiImage = new String[]{	"     ",
										"  ^  ",
										" | | ",
										"|___|",
										"_____",
										};
			// Set movement pattern
			moveShape = new boolean[][]{{true,false,true},
										{false,false,false},
										{false,true,false}};

		}else {
			asciiImage = new String[]{	" ___ ",
										"|   |",
										" | | ",
										"  v  ",
										"_____",
										};
			// Set movement pattern
			moveShape = new boolean[][]{{false,true,false},
										{false,false,false},
										{true,false,true}};

		}
		assignSide(color);
	}
	
}
