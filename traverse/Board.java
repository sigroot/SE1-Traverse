///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Board.java
// Purpose:	Represent the board of the game
//
// Limitations:	Cannot check moves involving jumps
//
// Development Computer: Framework 16
// Operating System: Ubuntu 24.04
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: 	Can set up the board and run the game
//						Without jumps
///////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	final int boardWidth = 10;
	final int boardHeight = 10;
	
	Piece[][] board = new Piece[boardWidth][boardHeight];
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
						newPieces.get(j).setCoordinate(1+j, 9);
						playerPieces[j][i] = newPieces.get(j);
						board[1+j][9] = newPieces.get(j);
						break;
					case 1:
						newPieces.get(j).setCoordinate(1+j, 0);
						playerPieces[j][i] = newPieces.get(j);
						board[1+j][0] = newPieces.get(j);
						break;
					case 2:
						newPieces.get(j).setCoordinate(9, 1+j);
						playerPieces[j][i] = newPieces.get(j);
						board[9][1+j] = newPieces.get(j);
						break;
					case 3:
						newPieces.get(j).setCoordinate(0, 1+j);
						playerPieces[j][i] = newPieces.get(j);
						board[0][1+j] = newPieces.get(j);
						break;
					default:
						// Is a Stub
				}
			}
			// Empties List
			newPieces.clear();
		}
	}
	
	// Does basic cleaning of the inputed movement string
	String cleanMoveString(String dirtyMove) {
		String cleanMove = "";
		String[] moveSquares = {};
		moveSquares = dirtyMove.trim().toLowerCase().split("-");
		for(int i = 0; i < moveSquares.length; i++) {
			if(moveSquares[i].length() > 2){
				cleanMove += moveSquares[i].substring(0, 3);
			}else {
				cleanMove += moveSquares[i].substring(0, 2);
			}
			if(i+1 < moveSquares.length) {
				cleanMove += "-";
			}
		}
		return cleanMove;
	}
	
	// Determines if a piece's movement is legal by the rules of 
	// Traverse.
	boolean checkLegal(String movement) {
		String[] moveSquares = movement.split("-");
		int[] xCoords = new int[movement.length()/3+1];
		int[] yCoords = new int[movement.length()/3+1];
		int xCoordinate = 0;
		int yCoordinate = 0;
		int relativeXMove = 0;
		int relativeYMove = 0;
		
		// Ensure the input uses correct grid spaces;
		if(moveSquares.length < 2) {
			return false;
		}
		
		for(int i = 0; i < moveSquares.length; i++) {
			if(moveSquares[i].charAt(0) < 'a' || moveSquares[i].charAt(0) > 'j') {
				return false;
			}
			if(moveSquares[i].length() > 2) {
				if(!moveSquares[i].substring(1,3).equals("10")){
					return false;
				}
			}else {
				if(moveSquares[i].charAt(1) < '1' || moveSquares[i].charAt(1) > '9') {
					return false;
				}
			}
		}
		
		// Ensure legality
		// No move lands on top of a piece
		for (int i = 0; i < moveSquares.length; i++) {
			xCoordinate = ((int) moveSquares[i].charAt(0))-'a';
			if(moveSquares[i].length() > 2) {
				yCoordinate = 0;
			}else {
				yCoordinate = 9-((int) moveSquares[i].charAt(1)-'1');
			}
			
			if(board[xCoordinate][yCoordinate] != null && i > 0) {
				return false;
			}
			xCoords[i] = xCoordinate;
			yCoords[i] = yCoordinate;
		}
		
		// Last move is not onto a corner
		if(xCoords[moveSquares.length-1] == 0 && yCoords[moveSquares.length-1] == 0) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == 9 && yCoords[moveSquares.length-1] == 0) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == 0 && yCoords[moveSquares.length-1] == 9) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == 9 && yCoords[moveSquares.length-1] == 9) {
			return false;
		}
		
		// Same space is not repeated
		for (int i = 0; i < xCoords.length; i++) {
			for (int j = i+1; j < xCoords.length; j++) {
				if(xCoords[i] == xCoords[j] && yCoords[i] == yCoords[j]) {
					return false;
				}
			}
		}
		
		// TODO Add jumps
		// Ensures piece movement is in piece movement pattern
		relativeXMove = xCoords[1] - xCoords[0];
		relativeYMove = yCoords[1] - yCoords[0];
		// Makes sure piece can't move past what is possible in one move
		if(Math.abs(relativeYMove) > 2) {
			return false;
		}
		if(Math.abs(relativeYMove) > 2) {
			return false;
		}
		
		// Makes sure general move is possible based on movement pattern
		if(Math.abs(relativeXMove) > 1) {
			relativeXMove /= 2;
		}
		if(Math.abs(relativeYMove) > 1) {
			relativeYMove /= 2;
		}
		
		if(!board[xCoords[0]][yCoords[0]].moveShape[relativeYMove + 1][relativeXMove + 1]) {
			return false;
		}
		
		
		return true;
	}
	
	// Swaps the contents of two items in board.
	boolean movePiece(String space1, String space2) {
		int xCoordinate1;
		int yCoordinate1;
		int xCoordinate2;
		int yCoordinate2;
		Piece piece1;
		Piece piece2;
		
	
		xCoordinate1 = ((int) space1.charAt(0))-'a';
		if(space1.length() > 2) {
			yCoordinate1 = 0;
		}else {
			yCoordinate1 = 9-((int) space1.charAt(1)-'1');
		}
		xCoordinate2 = ((int) space2.charAt(0))-'a';
		if(space2.length() > 2) {
			yCoordinate2 = 0;
		}else {
			yCoordinate2 = 9-((int) space2.charAt(1)-'1');
		}
		
		piece1 = board[xCoordinate1][yCoordinate1];
		piece2 = board[xCoordinate2][yCoordinate2];
		
		board[xCoordinate1][yCoordinate1] = piece2;
		board[xCoordinate2][yCoordinate2] = piece1;
		
		if(piece1 != null) {
			piece1.setCoordinate(xCoordinate2, yCoordinate2);
		}
		if(piece2 != null) {
			piece2.setCoordinate(xCoordinate1, yCoordinate1);
		}
		
		return true;
	}
	
	// Moves a piece using a full movement string
	boolean movePiece(String movement) {
		String[] moveSquares = movement.split("-");
		String space1 = moveSquares[0];
		String space2 = moveSquares[moveSquares.length-1];
		return movePiece(space1, space2);
	}
	
	// Makes a CPU move for a given player.
	boolean movePlayer(short player) {
		// TODO Is a Stub
		Piece movingPiece;
		int tempRandom1 = (int) Math.floor(Math.random()*8);
		int tempRandom2 = (int) Math.floor(Math.random()*8);
		int relativeXMove = 0;
		int relativeYMove = 0;
		String moveString;
		
		for(int i = (tempRandom1+1)%8; i != tempRandom1; i = (i + 1) % 8) {
			movingPiece = playerPieces[i][player];
			System.out.println(movingPiece.getCoordinate()[1]);
			for(int j = (tempRandom2+1)%8; j != tempRandom2; j = (j + 1) % 8) {
				switch(j) {
					case 0:
						relativeXMove = 0;
						relativeYMove = -1;
						break;
					case 1:
						relativeXMove = 1;
						relativeYMove = -1;
						break;
					case 2:
						relativeXMove = 1;
						relativeYMove = 0;
						break;
					case 3:
						relativeXMove = 1;
						relativeYMove = 1;
						break;
					case 4:
						relativeXMove = 0;
						relativeYMove = 1;
						break;
					case 5:
						relativeXMove = -1;
						relativeYMove = 1;
						break;
					case 6:
						relativeXMove = -1;
						relativeYMove = 0;
						break;
					case 7:
						relativeXMove = -1;
						relativeYMove = -1;
						break;
				}
				
				if(movingPiece.moveShape[relativeYMove + 1][relativeXMove + 1] && 
						movingPiece.getCoordinate()[0] + relativeXMove > 0 && 
						movingPiece.getCoordinate()[0] + relativeXMove < 10 && 
						10-movingPiece.getCoordinate()[1] + relativeYMove > 0 && 
						10-movingPiece.getCoordinate()[1] + relativeYMove < 10) {
					
					moveString = "";
					moveString += (char)(movingPiece.getCoordinate()[0] + 'a');
					moveString += Integer.toString(10-movingPiece.getCoordinate()[1]);
					moveString += "-";
					moveString += (char)(movingPiece.getCoordinate()[0] + 'a' + relativeXMove);
					moveString += Integer.toString(10-movingPiece.getCoordinate()[1] + relativeYMove);
					
					//TODO remove testing output
					System.out.println(moveString);
					
					if(checkLegal(moveString)) {
						movePiece(moveString);
						return true;
					}
				}
			}
		}
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
		// TODO Is a Stub
		return false;
	}
}
