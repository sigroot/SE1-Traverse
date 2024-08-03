///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Board.java
// Purpose:	Represent the board of the game
//
// Limitations:	Cannot check legal moves
//
// Development Computer: HP Pavilion HPE Series [h8-1124]
// Operating System: PopOS
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: Can set up the board
///////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	Piece[][] board = new Piece[10][10];
	Piece[][] playerPieces = new Piece[8][4];
	
	// Creates 8 pieces for each participating player and assigns 
	// them to locations on the board.
	Board() {
		//This space is intentionally left blank
	}
	
	// Generates the board and pieces.
	void createBoard(short players) {
		ArrayList<Piece> newPieces = new ArrayList<Piece>();
		
		for(short i = 0; i < players; i++) {
			
			// Set up the new pieces
			newPieces.add(new Circle(i));
			newPieces.add(new Circle(i));
			newPieces.add(new Square(i));
			newPieces.add(new Square(i));
			newPieces.add(new Diamond(i));
			newPieces.add(new Diamond(i));
			if(i < 2) {
				newPieces.add(new VertTriangle(i));
				newPieces.add(new VertTriangle(i));
			}else {
				newPieces.add(new HorzTriangle(i));
				newPieces.add(new HorzTriangle(i));
			}
			
			// Randomize new pieces
			Collections.shuffle(newPieces);
			
			// Add pieces to players and board
			for(short j = 0; j < 8; j++) {
				switch(i) {
					case 0:
						playerPieces[j][i] = newPieces.get(j);
						board[1+j][9] = newPieces.get(j);
						newPieces.get(i).setCoordinate(1+j, 9);
						break;
					case 1:
						playerPieces[j][i] = newPieces.get(j);
						board[1+j][0] = newPieces.get(j);
						newPieces.get(i).setCoordinate(1+j, 0);
						break;
					case 2:
						playerPieces[j][i] = newPieces.get(j);
						board[9][1+j] = newPieces.get(j);
						newPieces.get(i).setCoordinate(9, 1+j);
						break;
					case 3:
						playerPieces[j][i] = newPieces.get(j);
						board[0][1+j] = newPieces.get(j);
						newPieces.get(i).setCoordinate(0, 1+j);
						break;
					default:
						// Is a Stub
				}
			}
			// Empties List
			newPieces.clear();
		}
	}
	
	// Determines if a pieces movement is legal by the rules of 
	// Traverse.
	boolean checkLegal(String movement) {
		// Is a Stub
		return false;
	}
	
	// Swaps the contents of two items in board.
	boolean movePiece(String space1, String space2) {
		int xCoordinate1;
		int yCoordinate1;
		int xCoordinate2;
		int yCoordinate2;
		Piece piece1;
		Piece piece2;
		
		try {
			xCoordinate1 = ((int) space1.charAt(0))-97;
			yCoordinate1 = 10-Integer.parseInt(space1.substring(1));
			xCoordinate2 = ((int) space2.charAt(0))-97;
			yCoordinate2 = 10-Integer.parseInt(space2.substring(1));
			
			piece1 = board[xCoordinate1][yCoordinate1];
			piece2 = board[xCoordinate2][yCoordinate2];
			
			board[xCoordinate1][yCoordinate1] = piece2;
			board[xCoordinate2][yCoordinate2] = piece1;
			
			piece1.setCoordinate(xCoordinate2, yCoordinate2);
			piece2.setCoordinate(xCoordinate1, yCoordinate1);
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	// Makes a CPU move for a given player.
	boolean movePlayer(short player) {
		// Is a Stub
		return false;
	}
	
	// Returns an ASCII representation of the current board.
	String[] printBoard(){
		String[] tempBoard = new String[53];
		
		// Sets the board to empty Strings
		for(int i = 0; i < 53; i++) {
			tempBoard[i] = "";
		}
		
		// Creates top lettering
		tempBoard[0] += "  ";
		for(int i = 0; i < 10; i++) {
			tempBoard[0] += "   " + (char) (97 + i) + "  ";
		}
		
		tempBoard[1] += "  ";
		for(int j = 0; j < 10; j++) {
			tempBoard[1] += " _____";
		}
		for(int i = 0; i < 10; i++) {
			
			
			for(int j = 0; j < 5; j++) {
				// Creates left numbering
				if(j == 2) {
					tempBoard[i*5+j+2] += (10-i);
					if(i > 0) {
						tempBoard[i*5+j+2] += " ";
					}
				}else {
					tempBoard[i*5+j+2] += "  ";
				}
				
				// Fills in square appropriately
				for(int k = 0; k < 10; k++) {
					if(board[k][i] != null) {
						tempBoard[i*5+j+2] += "|";
						tempBoard[i*5+j+2] += board[k][i].GetAscii()[j];
					}else if(board[k][i] == null && j < 4) {
						tempBoard[i*5+j+2] += "|     ";
					}else {
						tempBoard[i*5+j+2] += "|_____";
					}
				}
				tempBoard[i*5+j+2] += "|";
				// Creates right numbering
				if(j == 2) {
					tempBoard[i*5+j+2] += (10-i);
				}else {
					tempBoard[i*5+j+2] += " ";
				}
			}
		}
		
		// Creates bottom lettering
		tempBoard[52] += "  ";
		for(int i = 0; i < 10; i++) {
			tempBoard[52] += "   " + (char) (97 + i) + "  ";
		}
		
		return tempBoard;
	}
	
	// Determines whether the board is in a game ending state.
	boolean checkGameEnd() {
		// Is a Stub
		return false;
	}
}
