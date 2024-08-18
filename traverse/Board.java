///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: Board.java
// Purpose:	Represent the board of the game
//
// Limitations:	AI cannot complete the game
//
// Development Computer: Framework 16
// Operating System: Ubuntu 24.04
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: 	Can run the game, but AI can't complete it.
///////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	final int boardWidth = 10;
	final int boardHeight = 10;
	
	Piece[][] board = new Piece[boardWidth][boardHeight];
	Piece[][] playerPieces = new Piece[4][8];
	
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
						newPieces.get(j).setCoordinate(1+j, boardHeight-1);
						playerPieces[i][j] = newPieces.get(j);
						board[1+j][boardHeight-1] = newPieces.get(j);
						break;
					case 1:
						newPieces.get(j).setCoordinate(1+j, 0);
						playerPieces[i][j] = newPieces.get(j);
						board[1+j][0] = newPieces.get(j);
						break;
					case 2:
						newPieces.get(j).setCoordinate(boardWidth-1, 1+j);
						playerPieces[i][j] = newPieces.get(j);
						board[boardWidth-1][1+j] = newPieces.get(j);
						break;
					case 3:
						newPieces.get(j).setCoordinate(0, 1+j);
						playerPieces[i][j] = newPieces.get(j);
						board[0][1+j] = newPieces.get(j);
						break;
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
	boolean checkLegal(String movement, short player) {
		String[] moveSquares = movement.split("-");
		int[] xCoords = new int[moveSquares.length];
		int[] yCoords = new int[moveSquares.length];
		int xCoordinate = 0;
		int yCoordinate = 0;
		int relativeXMove = 0;
		int relativeYMove = 0;
		
		// Ensure the input uses more than 1 board space;
		if(moveSquares[0].length() < 2) {
			return false;
		}
		
		// Ensure correct range of inputs
		for(int i = 0; i < moveSquares.length; i++) {
			if(moveSquares[i].charAt(0) < 'a' || moveSquares[i].charAt(0) > ('a'+boardHeight-1)) {
				return false;
			}
			if(Integer.parseInt(moveSquares[i].substring(1)) < 1 || Integer.parseInt(moveSquares[i].substring(1)) > boardHeight) {
				return false;
			}
			
		}
		
		// Ensure legality
		// No move lands on top of a piece
		// Divide each move into x and y locations
		for (int i = 0; i < moveSquares.length; i++) {
			xCoordinate = ((int) moveSquares[i].charAt(0))-'a';
			yCoordinate = (boardHeight)-Integer.parseInt(moveSquares[i].substring(1));
			
			
			if(board[xCoordinate][yCoordinate] != null && i > 0) {
				return false;
			}
			xCoords[i] = xCoordinate;
			yCoords[i] = yCoordinate;
		}
		
		// Ensure start is a piece
		if(board[xCoords[0]][yCoords[0]] == null) {
			return false;
		}
		
		// Ensure piece is player's piece
		if(board[xCoords[0]][yCoords[0]].color != player) {
			System.out.println("player type fail");
			return false;
		}
		
		// Last move is not onto a corner
		if(xCoords[moveSquares.length-1] == 0 && yCoords[moveSquares.length-1] == 0) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == boardWidth-1 && yCoords[moveSquares.length-1] == 0) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == 0 && yCoords[moveSquares.length-1] == boardHeight-1) {
			return false;
		}
		if(xCoords[moveSquares.length-1] == boardWidth-1 && yCoords[moveSquares.length-1] == boardHeight-1) {
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
		
		for(int i = 0; i < moveSquares.length-1; i++) {
			// Ensures piece movement is in piece movement pattern
			relativeXMove = xCoords[i+1] - xCoords[i];
			relativeYMove = yCoords[i+1] - yCoords[i];
			
			// Makes sure piece can't move past what is possible in one move
			if(Math.abs(relativeYMove) > 2 || Math.abs(relativeYMove) > 2) {
				return false;
			}
			
			// Ensures every move is a jump in a long string
			if(!(Math.abs(relativeYMove) == 2 || Math.abs(relativeYMove) == 2) && moveSquares.length > 2) {
				return false;
			}
			
			// Makes sure general move is possible based on movement pattern
			if(!board[xCoords[0]][yCoords[0]].moveShape[(int) (Math.signum(relativeYMove) + 1)][(int) Math.signum(relativeXMove) + 1]) {
				return false;
			}
		
			// Ensures no knight movement
			if((Math.abs(relativeYMove) == 2 && Math.abs(relativeYMove) == 1) || 
					(Math.abs(relativeYMove) == 1 && Math.abs(relativeYMove) == 2)) {
				return false;
			}
			
			// Checks for a piece to jump over
			if((Math.abs(relativeYMove) == 2 || Math.abs(relativeYMove) == 2) && 
					(board[(int) (xCoords[i] + Math.signum(relativeXMove))][(int) (yCoords[i] + Math.signum(relativeYMove))] == null)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Swaps the contents of two spaces on the board
	// Swaps the contents of two items in board.
	boolean movePiece(String space1, String space2) {
		int xCoordinate1;
		int yCoordinate1;
		int xCoordinate2;
		int yCoordinate2;
		Piece piece1;
		Piece piece2;
		
	
		xCoordinate1 = ((int) space1.charAt(0))-'a';
		yCoordinate1 = (boardHeight)-(Integer.parseInt(space1.substring(1)));
		
		xCoordinate2 = ((int) space2.charAt(0))-'a';
		yCoordinate2 = (boardHeight-1)-(Integer.parseInt(space2.substring(1))-1);
		
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
	
	// Moves pieces based on a movement string
	boolean movePiece(String movement) {
		String[] moveSquares = movement.split("-");
		String space1 = moveSquares[0];
		String space2 = moveSquares[moveSquares.length-1];
		return movePiece(space1, space2);
	}
	
	
	// Makes a CPU move for a given player. (AI cannot complete game)
	boolean movePlayer(short player) {
		int[] currentCoords = new int[2];
		int relativeXMove = 0;
		int relativeYMove = 0;
		String currentMoveString;
		String tempString;
		String[] allMoveStrings = new String[512];
		String[] tempSplitString;
		int allMoveStringsPointer = 0;
		int tempStrength = 0;
		int currentMoveStrength = 0;
		int maxMoveStringStrength = 0;
		int endOfStrongMoveStrings = 0;
		int targetX = 0;
		int targetY = 0;
		
		// Makes a list of every legal movement string
		for(int i = 0; i < playerPieces[player].length; i++) {
			
			// Adds the starting location to the movement string
			currentCoords = playerPieces[player][i].getCoordinate();
			currentMoveString = (char) (currentCoords[0]+'a') + Integer.toString(boardHeight-currentCoords[1]);
			
			// Remove finished pieces from the running (helps wonky AI)
			if(player == 0 && currentCoords[1] == 0) {
				continue;
			}if(player == 1 && currentCoords[1] == boardHeight-1) {
				continue;
			}if(player == 2 && currentCoords[0] == 0) {
				continue;
			}if(player == 3 && currentCoords[0] == boardWidth-1) {
				continue;
			}
			
			allMoveStrings[allMoveStringsPointer] = currentMoveString;
			
			
			
			// Performs a breadth-first-search of all potential movements from the first movement string
			while(allMoveStrings[allMoveStringsPointer] != null) {
				
				// Gets end coordinate of next movement string
				currentMoveString = allMoveStrings[allMoveStringsPointer];
				tempString = currentMoveString.split("-")[currentMoveString.split("-").length-1];
				currentCoords[0] = (int) (tempString.charAt(0)-'a');
				currentCoords[1] = boardHeight-Integer.parseInt(tempString.substring(1));
				
				for(int j = 0; j < 8; j++) {
					// Checks all 8 moves starting from facing up and turning clockwise
					relativeXMove = -(int)(Math.signum(j%4) * Math.signum(j-4));
					relativeYMove = -(int)(Math.signum((j+2)%4) * Math.signum(j-2) * Math.signum(j-6));
					
					// Skips if move is off the board
					if (currentCoords[0] + relativeXMove > boardWidth-1 || 
							currentCoords[0] + relativeXMove < 0 || 
							currentCoords[1] + relativeYMove > boardHeight-1 ||
							currentCoords[1] + relativeYMove < 0 ) {
						continue;
					}
					
					// If a piece is on the attempted location, attempt a jump
					if (board[currentCoords[0] + relativeXMove][currentCoords[1] + relativeYMove] != null) {
						
						// Skips if jump is off the board
						if (currentCoords[0] + 2*relativeXMove > boardWidth-1 || 
								currentCoords[0] + 2*relativeXMove < 0 || 
								currentCoords[1] + 2*relativeYMove > boardHeight-1 ||
								currentCoords[1] + 2*relativeYMove < 0 ) {
							continue;
						}
						
						// Skips if the attempted jump location has a piece
						if (board[currentCoords[0] + 2*relativeXMove][currentCoords[1] + 2*relativeYMove] != null) {
							continue;
						}else {
							
							// If the attempted jump location is empty, check its legality
							tempString = currentMoveString + '-' + (char) ((currentCoords[0] + 2*relativeXMove)+'a') + Integer.toString(10-(currentCoords[1] + 2*relativeYMove));
							if(checkLegal(tempString, player)) {
								
								// All checks cleared, add it to the list
								allMoveStrings[allMoveStringsPointer] = tempString;
								allMoveStringsPointer++;
							}
							continue;
						}
					}else {
						
						// If the attempted location is empty, check its legality
						tempString = currentMoveString + '-' + (char) ((currentCoords[0]+relativeXMove)+'a') + Integer.toString(10-(currentCoords[1]+relativeYMove));
						
						if(checkLegal(tempString, player)) {
							
							// All checks cleared, add it to the list
							allMoveStrings[allMoveStringsPointer] = tempString;
							allMoveStringsPointer++;
						}
						continue;
					}
				}
				// If a string of two moves or more was not added, remove the piece from consideration
				if(currentMoveString.split("-").length < 2) {
					allMoveStrings[allMoveStringsPointer] = null;
				}
			}
		}
		
		// Filters the allMoveStrings list to only the highest strength
		maxMoveStringStrength = 9999;
		endOfStrongMoveStrings = 0;
		for(int i = 0; allMoveStrings[i] != null; i++) {
			
			
			currentMoveString = allMoveStrings[i];
			tempSplitString = currentMoveString.split("-");
			if(tempSplitString.length < 2) {
				continue;
			}
			currentCoords[0] = (int) (tempSplitString[tempSplitString.length-1].charAt(0)-'a');
			currentCoords[1] = 10-Integer.parseInt(tempSplitString[tempSplitString.length-1].substring(1));
			
			// Get strength of move string (low is strong)
			currentMoveStrength = 9999;
			for(int j = 0; j < boardWidth-2; j++) {
				switch(player) {
					case(0):
						targetX = 1+j;
						targetY = 0;
						break;
					case(1):
						targetX = 1+j;
						targetY = boardHeight-1;
						break;
					case(2):
						targetX = 0;
						targetY = 1+j;
						break;
					case(3):
						targetX = boardWidth-1;
						targetY = 1+j;
						break;
				}
				// Aim for spots without own piece
				if((board[targetX][targetY] != null && board[targetX][targetY].color != player) || board[targetX][targetY] == null) {
					
					// Moving Horizontal or vertical
					if(targetY == j+1) {
						// Ending location distance
						tempStrength = (int) Math.pow( 5*(Math.abs(targetX-currentCoords[0])) + (Math.abs(targetY-currentCoords[1])) ,2);
						// Subtract starting location distance
						tempStrength -= (int) Math.pow( 5*(Math.abs(targetX-(int)(tempSplitString[0].charAt(0)-'a'))) + (Math.abs(targetY-(boardHeight-Integer.parseInt(tempSplitString[0].substring(1))))) ,2);
						
						// Discourage waiting for opponent
						if((board[targetX][targetY] != null)) {
							tempStrength += 7;
						}
						
						if(tempStrength < currentMoveStrength) {
							currentMoveStrength = tempStrength;
						}
					}else {
						// Ending location distance
						tempStrength = (int) Math.pow( (Math.abs(targetX-currentCoords[0])) + 5*(Math.abs(targetY-currentCoords[1])) ,2);
						// Subtract starting location distance
						tempStrength -= (int) Math.pow( (Math.abs(targetX-(int)(tempSplitString[0].charAt(0)-'a'))) + 5*(Math.abs(targetY-(boardHeight-Integer.parseInt(tempSplitString[0].substring(1))))) ,2);
						
						// Discourage waiting for opponent
						if((board[targetX][targetY] != null)) {
							tempStrength += 7;
						}
						
						if(tempStrength < currentMoveStrength) {
							currentMoveStrength = tempStrength;
						}
					}
					
				}
			}
			
			// Compare string strength to old list overwriting move strings
			if(currentMoveStrength == maxMoveStringStrength) {
				allMoveStrings[endOfStrongMoveStrings] = currentMoveString;
				endOfStrongMoveStrings++;
			}
			if(currentMoveStrength < maxMoveStringStrength) {
				maxMoveStringStrength++;
				endOfStrongMoveStrings = 0;
				maxMoveStringStrength = currentMoveStrength;
				allMoveStrings[endOfStrongMoveStrings] = currentMoveString;
				endOfStrongMoveStrings++;
			}
		}
		
		if(endOfStrongMoveStrings > 0) {
			movePiece(allMoveStrings[(int)Math.random()*endOfStrongMoveStrings]);
			return true;
		}else {
			return false;
		}
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
	boolean checkGameEnd(short players) {
		int xRequired;
		int yRequired;
		int totalComplete;
		for(int i = 0; i < players; i++) {
			totalComplete = 0;
			xRequired = -1;
			yRequired = -1;
			switch(i) {
				case 0:
					yRequired = 0;
					break;
				case 1:
					yRequired = boardHeight-1;
					break;
				case 2:
					xRequired = 0;
					break;
				case 3:
					xRequired = boardWidth-1;
					break;
			}
			for(int j = 0; j < playerPieces[i].length; j++) {
				if(xRequired > -1 && playerPieces[i][j].getCoordinate()[0] == xRequired) {
					totalComplete++;
				}else if(yRequired > -1 && playerPieces[i][j].getCoordinate()[1] == yRequired) {
					totalComplete++;
				}
			}
			if(totalComplete >= playerPieces[i].length) {
				return true;
			}
		}
		return false;
	}
}
