import java.util.*;
public class OkeyGame {
    Boolean start = true;
    Player[] players;
    Tile[] tiles;
    int index = 0;
    Tile tile1;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        
        players = new Player[4];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        players[2] = new Player("Player 3");
        players[3] = new Player("Player 4");
        tile1 = new Tile(30, 'B');// I gave a big number because I want it to be the last on the sorting etc.

    }

    public Tile[] createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
        return tiles;
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers(){ 
        for(int j = 0; j<4;j++){ //adds 14 tiles to each person
            for(int i = 0; i < 14; i++){
                players[j].playerTiles[i] = tiles[index];
                index++;
            }
        }
        //adds the last tile of player 1 
        players[0].playerTiles[14] = tiles[56];
        players[1].playerTiles[14] = tile1;//30, 'B'
        players[2].playerTiles[14] = tile1;//30, 'B'
        players[3].playerTiles[14] = tile1;//30, 'B'

        index++;
        start = false;

    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile(){ // Çağkan
           
        // Check the lastDiscarded Tile is available
            if (lastDiscardedTile != null) {
                //Player currentPlayer = players[currentPlayerIndex];//hey

                // add tile to current player's tile
                //currentPlayer.addTile(lastDiscardedTile); 
                
                // return
                return lastDiscardedTile.toString();
            } 
        return null;
            
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() { // Çağkan
        if (/*tiles == null ||*/ tiles.length == 0) { // If tiles are not available to take one
            index++;
        return null; // So tiles could not be taken,return null
        }
    
        // Find the tile whose index is biggest(length-1),(it is on the top)
        Tile topTile = tiles[index];
    
        // Remove the tile from list ,which we took.
        tiles[index] = tile1;

    
        // return topTile which we took.
        if (topTile != null) {

            Player currentPlayer = players[currentPlayerIndex];//hey
            currentPlayer.addTile(topTile);

            index ++;
            return topTile.toString();
        } 
        index++;
        return null;
        
    }

   /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() { 
        Random rand = new Random();
        
        for(int i = 0; i < tiles.length; i++){ //replaces tile in order with a tile at random index
            //creates random number that doesnt include indexes that has been used in order
            int randomNum = rand.nextInt(tiles.length - i)+i;
            //replaces tile with the help of temporary variable
            Tile temp = tiles[i];
            tiles[i] = tiles[randomNum];
            tiles[randomNum] = temp;
        }

    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() { //Furkan

         // Returns true if a player has winning hand
         if(players[getCurrentPlayerIndex()].isWinningHand()){
            System.out.println(players[getCurrentPlayerIndex()] + " has won !");
            return true;
        }

        // Returns true if there is no tile to pull
        else if(tiles[index].equals(null) && players[getCurrentPlayerIndex()].getTiles()[14]==null){
            System.out.println("Game is over ! Stalemate !");
            return true;
        }
        else{
            return false;
        }
		
        
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() { // Oğuz
        players[currentPlayerIndex].sortThePlayerHand();
        char color = lastDiscardedTile.color;
        int value = lastDiscardedTile.value;
        boolean controlY = false;
        boolean controlB = false;
        boolean controlR = false;
        boolean controlK = false;
        for ( int i = 0; players[currentPlayerIndex].getTiles().length - 1 > i ; i++ ){
            if ( players[currentPlayerIndex].getTiles()[i].getValue() == value ){
                if ( players[currentPlayerIndex].getTiles()[i].getColor() == 'Y' && players[currentPlayerIndex].getTiles()[i].getColor() != color){
                    controlY = true;
                }else if( players[currentPlayerIndex].getTiles()[i].getColor() =='B' && players[currentPlayerIndex].getTiles()[i].getColor() != color){
                    controlB = true;
                }else if (players[currentPlayerIndex].getTiles()[i].getColor() =='R' && players[currentPlayerIndex].getTiles()[i].getColor() != color){
                    controlR = true;

                }else if(players[currentPlayerIndex].getTiles()[i].getColor() =='K' && players[currentPlayerIndex].getTiles()[i].getColor() != color){
                    controlK = true;
                }
            }
        }
        boolean [] controlArr = { controlB,controlK,controlR,controlY};
        int booleanCounter = 0;
        for ( int i = 0; i < controlArr.length; i++ ){
            if ( controlArr[i] == true ){
                booleanCounter++;
            }
        }
        if( booleanCounter >= 2 ){
            System.out.println("Computer picked from the lastDiscardedTile.");
            System.out.println("Picked tile: " + lastDiscardedTile);
            players[currentPlayerIndex].addTile(lastDiscardedTile);

        }else{
            System.out.println("Computer picked from the tiles array.");
            System.out.println("Picked tile: " + tiles[index]);
            getTopTile();
        } 
    }
    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() { // Oğuz
        // Copies
        players[currentPlayerIndex].sortThePlayerHand();
        Tile[]playerTiles = players[currentPlayerIndex].getTiles();
        
        int position = 0;
        int lengthOfChain = 1;
        ArrayList <Integer> indexesOf = new ArrayList<>();
        ArrayList <Integer> howManyCopy = new ArrayList<>(); 
        while(position < playerTiles.length - 1){
            if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() != playerTiles[position + 1].getColor()){
                position++;
            }else if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() == playerTiles[position + 1].getColor()){
                position++;
                lengthOfChain++;
            }else if(playerTiles[position].getValue() != playerTiles[position + 1].getValue()){ 
                if(lengthOfChain >= 2){
                    indexesOf.add(position - 1);
                    howManyCopy.add(lengthOfChain);
                }
                lengthOfChain = 1;
                position++;
            }           
        }  
        for (int i = 0; i < howManyCopy.size() - 1; i++) { //Sorting the copies lengths and one the copies indexes
            for (int j = 0; j < howManyCopy.size() - 1 - i; j++) {
                if (howManyCopy.get(j) < howManyCopy.get(j + 1)) {
                    int temp = howManyCopy.get(j);
                    howManyCopy.set(j, howManyCopy.get(j + 1));
                    howManyCopy.set(j + 1, temp);

                    int temp2 = indexesOf.get(j);
                    indexesOf.set(j, indexesOf.get(j + 1));
                    indexesOf.set(j +1, temp2);
        
                }
            }
        }
        if ( !howManyCopy.isEmpty() ){
            if ( howManyCopy.get(0) >= 2){
                System.out.println("Discarded tile: "+playerTiles[indexesOf.get(0)]);
                discardTile(indexesOf.get(0));
                displayCurrentPlayersTiles();
                return;
            }

        }

        //Single tiles
        position = 0;
        lengthOfChain = 1;
        indexesOf.clear();
        while(position < playerTiles.length - 1){
            if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() != playerTiles[position + 1].getColor()){
                position++;
                lengthOfChain++;
            }else if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() == playerTiles[position + 1].getColor()){
                position++;
                lengthOfChain++;
            }else if(playerTiles[position].getValue() != playerTiles[position + 1].getValue()){ 
                if(lengthOfChain == 1){
                    indexesOf.add(position);
                }
                lengthOfChain = 1;
                position++;
            }           
        } 
        if ( indexesOf.size() > 0 ){
            System.out.println("Discarded tile: "+playerTiles[indexesOf.get(0)]);
            discardTile(indexesOf.get(0));
            displayCurrentPlayersTiles();
            return;
        }
        // Smallest chains
        howManyCopy.clear();
        position = 0;
        lengthOfChain = 1;
        indexesOf.clear();
        while(position < playerTiles.length - 1){
            if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() != playerTiles[position + 1].getColor()){
                position++;
                lengthOfChain++;
            }else if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() == playerTiles[position + 1].getColor()){
                position++;
            }else if(playerTiles[position].getValue() != playerTiles[position + 1].getValue()){ 
                if(lengthOfChain > 1){
                    indexesOf.add(position);
                    howManyCopy.add(lengthOfChain);
                }
                lengthOfChain = 1;
                position++;
            }           
        } 
        for (int i = 0; i < howManyCopy.size() - 1; i++) { //Sorting the chains lengths and one the element of the chains index
            for (int j = 0; j < howManyCopy.size() - 1 - i; j++) {
                if (howManyCopy.get(j) > howManyCopy.get(j + 1)) {
                    int temp = howManyCopy.get(j);
                    howManyCopy.set(j, howManyCopy.get(j + 1));
                    howManyCopy.set(j + 1, temp);

                    int temp2 = indexesOf.get(j);
                    indexesOf.set(j, indexesOf.get(j + 1));
                    indexesOf.set(j +1, temp2);

                }
            }
        }
        if ( indexesOf.size() > 0 ){
            System.out.println("Discarded tile: "+playerTiles[indexesOf.get(0)]);
            discardTile(indexesOf.get(0));
            displayCurrentPlayersTiles();
            return;
        }
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) { // Furkan
        lastDiscardedTile = players[getCurrentPlayerIndex()].getTiles()[tileIndex];
        System.out.println(lastDiscardedTile);

        for(int i=tileIndex;i<14;i++){
            players[getCurrentPlayerIndex()].getTiles()[i]=players[getCurrentPlayerIndex()].getTiles()[i+1];
        }
        players[getCurrentPlayerIndex()].displayTiles();
        
        //players[getCurrentPlayerIndex()].getTiles()[14] = tile1;
        
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
