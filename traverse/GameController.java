///////////////////////////////////////////////////////////////////
// Student name: Samuel Armstrong
// Course: COSC 3403
// Project 4 - First Software Increment
// File name: GameController.java
// Purpose:	Manage the game operations
//
// Limitations:	Cannot run the game or ending sequences
//
// Development Computer: HP Pavilion HPE Series [h8-1124]
// Operating System: PopOS
// Integrated Development Environment (IDE): Eclipse 4.32.0
// Compiler: Java JDK 17
// Build Directions: See the Traverse class
// Operational Status: Runs the introduction
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
	final String[] GAME_MESSAGE = 	{"",
									 ""};
	final String[] END_MESSAGE = 	{"",
									 ""};
	Board board;
	int sequenceNumber;
	short players;
	boolean humanPlayer;
	int maximumRounds;
	int currentTurns;
	short boardDisplayMode;
	
	static Scanner kb = new Scanner(System.in);
	
	// Assigns the driver's board to the game controller's 
	// variable.
	public GameController(Board board){
		this.board = board;
		sequenceNumber = 1;
		players = 4;
		humanPlayer = true;
		maximumRounds = 10;
		currentTurns = 0;
		boardDisplayMode = 1;
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
		System.out.println("Terminating...");
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
		if(humanPlayer == true) {
			boardDisplayMode = 1;
		}
		
		// Set up the board
		board.createBoard(players);
		
		while(!menuInput.equals("start")){
			printedBoard = board.printBoard();
			for(int i = 0; i < printedBoard.length; i++) {
				System.out.println(printedBoard[i]);
			}
			System.out.println(INTRO_MESSAGE[14]);
			menuInput = getKeyboard(":> ");
			if(menuInput.equals("start")) {
				break;
			}
			swapCoordinates = menuInput.split("-");
			// Clean input
			// TODO ADD 10 FUNCTIONALITY
			if(swapCoordinates.length != 2) {
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}if(swapCoordinates[0].length() > 2 || swapCoordinates[1].length() > 2) {
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}if(swapCoordinates[0].charAt(1) != '1' || swapCoordinates[1].charAt(1) != '1') {
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}if(swapCoordinates[0].charAt(0) == 'a' || swapCoordinates[1].charAt(0) == 'a') {
				System.out.println(INTRO_MESSAGE[15]);
				continue;
			}if(swapCoordinates[0].charAt(0) == 'j' || swapCoordinates[1].charAt(0) == 'j') {
				System.out.println(INTRO_MESSAGE[15]);
				continue;
			}if(!board.movePiece(swapCoordinates[0], swapCoordinates[1])) {
				System.out.println(INTRO_MESSAGE[12]);
				continue;
			}
			
		}
		
	}
	
	// Runs the board game in a loop, displaying the board 
	// and asking the user to continue or enter a move, 
	// until a game end state has been reached.
	void gameSequence() {
		// Is a Stub
	}
	
	void endSequence() {
		// Is a Stub
	}
			
}
