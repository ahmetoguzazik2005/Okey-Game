
import java.util.Scanner;

public class ApplicationMain {
    public static char devMode;
    public static boolean devModeOn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OkeyGame game = new OkeyGame();

        System.out.print("Please enter your name: ");
        String playerName = sc.next();
        

        game.setPlayerName(0, playerName);
        game.setPlayerName(1, "John");
        game.setPlayerName(2, "Jane");
        game.setPlayerName(3, "Ted");

        game.createTiles();
        game.shuffleTiles();
        game.distributeTilesToPlayers();

        // developer mode is used for seeing the computer players hands, to be used for debugging
        System.out.print("Play in developer's mode with other player's tiles visible? (Y/N): ");
        devMode = sc.next().charAt(0);
        devMode=Character.toUpperCase(devMode);
        devModeOn = devMode == 'Y';
        
        boolean firstTurn = true;
        boolean gameContinues = true;
        int playerChoice = -1;
        int firstRoundBuffer = 0;
        int tourCounter = 0;
        int turnCounter = 1;

        while(gameContinues) {
            System.out.println("----------------------------------------------------------------------------------------");
        
            
            int currentPlayer = game.getCurrentPlayerIndex();
            if(turnCounter == 5){turnCounter = 1;}
            System.out.println("Tour: " + ((tourCounter/4)+1) + "   Turn: " + turnCounter);
            tourCounter++;
            turnCounter++;
            System.out.println(game.getCurrentPlayerName() + "'s turn");
            
            if(currentPlayer == 0) {
                // this is the human player's turn
                    game.displayCurrentPlayersTiles();
                //game.displayDiscardInformation();
                game.displayDiscardInformation();
                
                System.out.println("What will you do?");

                if(!firstTurn) {
                    // after the first turn, player may pick from tile stack or last player's discard
                    System.out.println("1. Pick From Tiles");  
                    System.out.println("2. Pick From Discard");
                }
                else{
                    // on first turn the starting player does not pick up new tile
                    System.out.println("1. Discard Tile");
                }

                do{
                    if(firstRoundBuffer>0) {
                    System.out.print("Your choice: ");
                    playerChoice = sc.nextInt();
                    }
                    else{
                        playerChoice = 1;
                        firstRoundBuffer++;
                    }
                    
                    
                    if((playerChoice > 2 || playerChoice < 1)&&!firstTurn){
                        System.out.println();
                        System.out.println("Invalid choice!!");
                        System.out.println("Your choice should be 1 or 2");
                        System.out.println();
                    }
                    else if((playerChoice > 1 || playerChoice < 1)&&firstTurn){
                        System.out.println();
                        System.out.println("Invalid choice!!");
                        System.out.println("Your choice should be 1");
                        System.out.println();
                        playerChoice = 3;//to return the beginning of loop
                    }
                }
                while(playerChoice > 2 || playerChoice < 1);
                

                // after the first turn we can pick up
                if(!firstTurn) {
                    if(playerChoice == 1) {
                        System.out.println();
                        System.out.println("You picked up: " + game.getTopTile());
                        firstTurn = false;
                    }
                    else if(playerChoice == 2) {
                        System.out.println();
                        System.out.println("You picked up: " + game.getLastDiscardedTile()); 
                        game.players[game.currentPlayerIndex].addTile(game.lastDiscardedTile);


                    }

                    // display the hand after picking up new tile
                        game.displayCurrentPlayersTiles();
                }
                else{
                    // after first turn it is no longer the first turn
                    firstTurn = false;
                }


                gameContinues = !game.didGameFinish();


                if(gameContinues) {
                    // make sure the given index is correct, should be 0 <= index <= 14
                    do{ 
                        // if game continues we need to discard a tile using the given index by the player
                    System.out.println("Which tile you will discard?");
                    System.out.print("Discard the tile in index: ");
                    playerChoice = sc.nextInt();
                    

                    if(playerChoice  < 0 || playerChoice > 14){
                        System.out.println();
                        System.out.println("Invalid choice!!");
                        System.out.println("Your choice should be between 0 - 14");
                        System.out.println();
                    }
                    }
                    while(playerChoice  < 0 || playerChoice > 14);
                    game.discardTile(playerChoice);
                    System.out.println("Player discarded the tile " + game.lastDiscardedTile );
                    game.passTurnToNextPlayer();
                }
                else{

                }
            }
            //
            else{
                // this is the computer player's turn
                    if(devModeOn) {
                        game.displayCurrentPlayersTiles();
                    }
                    

                // computer picks a tile from tile stack or other player's discard
                game.pickTileForComputer();

                gameContinues = !game.didGameFinish();

                if(gameContinues) {
                    // if game did not end computer should discard
                    
                    game.discardTileForComputer();
                    
                    game.passTurnToNextPlayer();
                }

            }
        }
        sc.close();
    }
}
