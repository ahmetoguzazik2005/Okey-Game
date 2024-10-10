import java.util.*;
public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
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
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers(){ // Yusuf
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile(){ // Çağkan
        return null;
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() { // Çağkan
        return null;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() { //Yusuf

    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() { //Furkan
        return false;
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() { // Oğuz
        char color = lastDiscardedTile.color;
        int value = lastDiscardedTile.value;
        boolean controlY = false;
        boolean controlB = false;
        boolean controlR = false;
        boolean controlK = false;
        boolean [] controlArr = { controlB,controlK,controlR,controlY};
        for ( int i = 0; players[currentPlayerIndex].getTiles().length > i; i++ ){
            if ( players[currentPlayerIndex].getTiles()[i].getValue() == value ){
                if ( players[currentPlayerIndex].getTiles()[i].getColor() =='Y' && players[currentPlayerIndex].getTiles()[i].getColor() != color){
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
        int booleanCounter = 0;
        for ( int i = 0; i < controlArr.length; i++ ){
            if ( controlArr[i] == true ){
                booleanCounter++;
            }
        }
        if( booleanCounter >= 2 ){
            getLastDiscardedTile();
            System.out.println("Computer picked from the lastDiscardedTile.");

        }else{
            getTopTile();
            System.out.println("Computer picked from the tiles array.");
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
        
        // Tile[] playerTiles = players[currentPlayerIndex].getTiles();
        Tile[]playerTiles = trial.player1.getTiles();// just for debugging
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
                    indexesOf.add(position);
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
        if ( howManyCopy.get(0) >= 2){
            System.out.println("Discarded tile: "+playerTiles[indexesOf.get(0)]);
            discardTile(indexesOf.get(0));
            return;
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
            return;
        }
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) { // Furkan

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
