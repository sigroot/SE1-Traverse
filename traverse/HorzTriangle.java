///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: HorzTriangle.java
// Purpose:	Represent a horizontal triangle piece in Traverse
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

public class HorzTriangle extends Piece{

	HorzTriangle(short color) {
		super(color);
		
		// Set appearance
		// Face Triangle to the correct side
		if(color == 2) {
			asciiImage = new String[]{	"     ",
										" ,-'|",
										"(   |",
										" '-,|",
										"_____",
									};
			// Set movement pattern
			moveShape = new boolean[][]{{true,false,false},
										{false,false,true},
										{true,false,false}};

		}else {
			asciiImage = new String[]{	"     ",
										"|'-, ",
										"|   )",
										"|,-' ",
										"_____",
									};
			// Set movement pattern
			moveShape = new boolean[][]{{false,false,true},
										{true,false,false},
										{false,false,true}};

		}
		assignSide(color);
	}
	
}
