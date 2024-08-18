///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: GameController.java
// Purpose:	Manage the game operations
//
// Limitations:	Is a Singleton
//
// Development Computer: Framework 16
// Operating System: Ubuntu 24.04
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: Runs the game
///////////////////////////////////////////////////////////////////
import java.util.Scanner;

public class GameController {
	final String[] INTRO_MESSAGE = 	{
									 "\tStarting Menu (Enter the letter or number of your option)",
									 "1) Adjust the [P]layers.",
    								 	"Will there be a human player? (Y/N)",
    								 	"How many total players will there be? (2-4)",
    								 "2) Set the maximum [R]ounds.",
    								 	"How many rounds can take place? (0 for unlimited)",
    								 "3) Determine when the board will be [D]isplayed?\n\t(If there is a human, will defualt to every turn)",
    								 	"The board will be displayed...",
    								 	"1) ...at the end of every turn.",
    								 	"2) ...after the maximum number of rounds.",
    								 	"3) ...after the game ends.",
    								 "4) [E]nd set-up and prepare the board.",
    								 "<<<Invalid option, please try again.>>>",
    								 "Settings saved...",
    								 "Enter two coordinates separated by a dash (eg. :> b1-e1) \nto swap your pieces, or type \"start\" to start.",
    								 "<<<Corner Spaces are invalid, please try again.>>>"};
	final String[] GAME_MESSAGE = 	{"Player #",
									 "'s move:",
									 "Enter a string of coordinates separated by dashes to make\na legal move. (eg. :> c3-c5-e5)",
									 "Illegal move, you may land a piece on an empty, non-corner\nspace that it can reach with movement or jumping.",
									 "Type \"c\" to continue."};
	final String[] END_MESSAGE = 	{ "--------------------------------------------------------------------\n"
									+ "*****                       Game is over                       *****\n"
									+ "--------------------------------------------------------------------",
									  "--------------------------------------------------------------------\n"
								    + "*****            Game is over (Maximum rounds taken)           *****\n"
									+ "--------------------------------------------------------------------"};
	static Board board;
	static short players;
	static boolean humanPlayer;
	static int maximumRounds;
	static int currentTurns;
	static short boardDisplayMode;
	
	static Scanner kb = new Scanner(System.in);
	
	static GameController gameControllerSingleton = new GameController();
	
	// Generates a GameController
	private GameController(){
		
	}
	
	// Assigns the driver's board to the game controller's 
	// variable and returns the controller.
	public static GameController getGameController(Board board) {
		GameController.board = board;
		players = 4;
		humanPlayer = true;
		maximumRounds = 0;
		currentTurns = 0;
		boardDisplayMode = 1;
		return gameControllerSingleton;
	}
	
	// Outputs a prompt to the terminal and gets a sanitized 
	// string from the user.
	static String getKeyboard(String prompt) {
		String inputString;
		
		// Get string based on prompt
		System.out.print(prompt);
		inputString = kb.next();
		inputString = inputString.toLowerCase();
		
		// Clear buffer
		kb.nextLine();
		
		return inputString;
	}
	
	// Runs the game sequences in order.
	void run() {
		introSequence();
		gameSequence();
		endSequence();
	}
	
	// Runs the prompts and reads the inputs for the introduction
	// sequence of the program to gather each of the user's
	// settings.
	void introSequence() {
		String menuInput = "";
		boolean trySucceed = false;
		String[] printedBoard;
		String[] swapCoordinates;
		
		//First menu
		while (!menuInput.equals("e") && !menuInput.equals("4")) {
			
			System.out.println(INTRO_MESSAGE[0]);
			System.out.println(INTRO_MESSAGE[1]);
			System.out.println(INTRO_MESSAGE[4]);
			System.out.println(INTRO_MESSAGE[6]);
			System.out.println(INTRO_MESSAGE[11]);
			menuInput = getKeyboard(":> ");
			
			if(menuInput.equals("1") || menuInput.equals("p")) {
				menuInput = "";
				
				while(!menuInput.equals("y") && !menuInput.equals("n")) {
					System.out.println(INTRO_MESSAGE[2]);
					menuInput = getKeyboard(":> ");
					if(menuInput.equals("y")) {
						humanPlayer = true;
					}else if(menuInput.equals("n")) {
						humanPlayer = false;
					}else {
						System.out.println(INTRO_MESSAGE[12]);
					}
				}
				
				while(!menuInput.equals("2") && !menuInput.equals("3") && !menuInput.equals("4")) {
					System.out.println(INTRO_MESSAGE[3]);
					menuInput = getKeyboard(":> ");
					if(menuInput.equals("2")) {
						players = 2;
					}else if(menuInput.equals("3")) {
						players = 3;
					}else if(menuInput.equals("4")) {
						players = 4;
					}else {
						System.out.println(INTRO_MESSAGE[12]);
					}
				}
				
			}else if(menuInput.equals("2") || menuInput.equals("r")) {
				menuInput = "";
				trySucceed = false;
				
				while(!trySucceed) {
					
					System.out.println(INTRO_MESSAGE[5]);
					
					try {
						menuInput = getKeyboard(":> ");
						maximumRounds = Integer.parseInt(menuInput);
						maximumRounds = Math.abs(maximumRounds);
						trySucceed = true;
					}catch(NumberFormatException e) {
						System.out.println(INTRO_MESSAGE[12]);
					}
				}
				
			}else if(menuInput.equals("3") || menuInput.equals("d")) {
				menuInput = "";
				
				while(!menuInput.equals("1") && !menuInput.equals("2") && !menuInput.equals("3")) {
					
					System.out.println(INTRO_MESSAGE[7]);
					System.out.println(INTRO_MESSAGE[8]);
					System.out.println(INTRO_MESSAGE[9]);
					System.out.println(INTRO_MESSAGE[10]);
					menuInput = getKeyboard(":> ");
					
					if(menuInput.equals("1")) {
						boardDisplayMode = 1;
					}else if(menuInput.equals("2")) {
						boardDisplayMode = 2;
					}else if(menuInput.equals("3")) {
						boardDisplayMode = 3;
					}else {
						System.out.println(INTRO_MESSAGE[12]);
					}
					
				}
				
			}else if(menuInput.equals("4") || menuInput.equals("e")) {
				menuInput = "";
				System.out.println(INTRO_MESSAGE[13]);
				break;
			}else {
				menuInput = "";
				System.out.println(INTRO_MESSAGE[12]);
			}
			menuInput = "";
		}
		
		// If there is a human player, the board displays every 
		// turn.
		if(humanPlayer) {
			boardDisplayMode = 1;
		}
		
		// Set up the board
		board.createBoard(players);
		printedBoard = board.printBoard();
		for(int i = 0; i < printedBoard.length; i++) {
			System.out.println(printedBoard[i]);
		}
		while(!menuInput.equals("start") && humanPlayer){
			System.out.println(INTRO_MESSAGE[14]);
			menuInput = getKeyboard(":> ");
			if(menuInput.equals("start")) {
				break;
			}
			// Clean input
			swapCoordinates = board.cleanMoveString(menuInput).split("-");
			
			for(int i = 0; i < printedBoard.length; i++) {
				System.out.println(printedBoard[i]);
			}
			
			// Ensures there are only two spaces selected
			if(swapCoordinates.length != 2) {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}
			// Ensures the space string length is correct
			if(swapCoordinates[0].length() > 2 || swapCoordinates[1].length() > 2) {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}
			
			// checks for the piece being on the home row
			if(swapCoordinates[0].charAt(1) != '1' || swapCoordinates[1].charAt(1) != '1') {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}
			
			// Checks for illegal corner placements
			if(swapCoordinates[0].charAt(0) == 'a' || swapCoordinates[1].charAt(0) == 'a') {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[15]);
				continue;
			}if(swapCoordinates[0].charAt(0) == 'j' || swapCoordinates[1].charAt(0) == 'j') {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[15]);
				continue;
			}
			
			// Attempts to move the piece
			if(!board.movePiece(swapCoordinates[0], swapCoordinates[1])) {
				// Reprint the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}
			
			printedBoard = board.printBoard();
			for(int i = 0; i < printedBoard.length; i++) {
				System.out.println(printedBoard[i]);
			}
			
		}
		
	}
	
	// Runs the board game in a loop, displaying the board 
	// and asking the user to continue or enter a move, 
	// until a game end state has been reached.
	void gameSequence() {
		boolean gameContinue = true;
		String gameInput = "";
		String[] printedBoard;
		short movingPlayer;
		
		// Ensure game is in playable state
		gameContinue = gameContinue && ((currentTurns/players < maximumRounds) || !(maximumRounds > 0));
		gameContinue = gameContinue && !board.checkGameEnd(players);
		
		while(gameContinue) {
			// Reset player input
			gameInput = "";
			
			switch(currentTurns%players) {
				case 0:
					movingPlayer = 0;
					break;
				case 1:
					if(players>2) {
						movingPlayer = 2;
					}else {
						movingPlayer = 1;
					}
					break;
				case 2:
					movingPlayer = 1;
					break;
				case 3:
					movingPlayer = 3;
					break;
				default:
					movingPlayer = 0;
					break;
			}
			
			if((movingPlayer) == 0 && humanPlayer == true) {
				// Print the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				// Print current player's turn
				System.out.print(GAME_MESSAGE[0]);
				System.out.print(currentTurns%players);
				System.out.println(GAME_MESSAGE[1]);
				
				// Player moves their piece
				while(!board.checkLegal(gameInput, (short) 0)) {
					System.out.println(GAME_MESSAGE[2]);
					gameInput = getKeyboard(":> ");
					if(gameInput.length() > 2) {
						gameInput = board.cleanMoveString(gameInput);
						
						if(board.checkLegal(gameInput, movingPlayer)) {
							board.movePiece(gameInput);
							
							printedBoard = board.printBoard();
							for(int i = 0; i < printedBoard.length; i++) {
								System.out.println(printedBoard[i]);
							}
							
							System.out.println(GAME_MESSAGE[4]);
							gameInput = getKeyboard(":> ");
							break;
						}
					}
					// Reset gameInput
					gameInput = "";
					
					// On error, reprint board and inform human player
					printedBoard = board.printBoard();
					for(int i = 0; i < printedBoard.length; i++) {
						System.out.println(printedBoard[i]);
					}
					// Print current player's turn
					System.out.print(GAME_MESSAGE[0]);
					System.out.print(currentTurns%players);
					System.out.println(GAME_MESSAGE[1]);
					System.out.println(GAME_MESSAGE[3]);
				}
				
			}else if(boardDisplayMode == 1){
				board.movePlayer((short) (movingPlayer));
				// Print the board
				printedBoard = board.printBoard();
				for(int i = 0; i < printedBoard.length; i++) {
					System.out.println(printedBoard[i]);
				}
				// Print current player's turn
				System.out.print(GAME_MESSAGE[0]);
				System.out.print(currentTurns%players);
				System.out.println(GAME_MESSAGE[1]);
				
				System.out.println(GAME_MESSAGE[4]);
				gameInput = getKeyboard(":> ");
				
			}else {
				board.movePlayer((short) (movingPlayer));
			}
			currentTurns++;
			
			// Check whether game has ended
			gameContinue = gameContinue && !((currentTurns/4 >= maximumRounds) && (maximumRounds > 0));
			gameContinue = gameContinue && !board.checkGameEnd(players);
			
			if(!gameContinue) {
				break;
			}
			
		}
	}
	
	// Runs the end of the game and informs the player of the conclusion
	void endSequence() {
		// Print the board
		String[] printedBoard = board.printBoard();
		for(int i = 0; i < printedBoard.length; i++) {
			System.out.println(printedBoard[i]);
		}
		if((currentTurns/4 >= maximumRounds) && (maximumRounds > 0)) {
			System.out.println(END_MESSAGE[1]);
		}else {
			System.out.println(END_MESSAGE[0]);
		}
	}
			
}
