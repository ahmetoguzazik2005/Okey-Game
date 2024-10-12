public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;
    int howManySets;


    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {// Hidayet
        Tile discardedTile = playerTiles[index]; 
        for(int i = index; i < playerTiles.length - 1; i++){
            playerTiles[i] = playerTiles[i + 1];
        }
        return discardedTile;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {// Hidayet
            playerTiles[playerTiles.length - 1] = t;
            sortThePlayerHand();
        
    }

    public void sortThePlayerHand(){
        boolean swapped = true;
        for(int i = 0; i < playerTiles.length - 1 && swapped; i++){
            swapped = false;
            for(int j = 0; j < playerTiles.length - 1 - i; j++){
                if(playerTiles[j].compareTo(playerTiles[j + 1]) == 1){
                    Tile toKeep = playerTiles[j];
                    playerTiles[j] = playerTiles[j + 1];
                    playerTiles[j + 1] = toKeep;
                    swapped = true;
                }
            }
        }
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {// Hidayet
        int position = 0;
        int numberOfChain = 0;// must be cancelled at the end
        int lengthOfChain = 1;
        while(position < playerTiles.length - 1){
            if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() != playerTiles[position + 1].getColor()){
                lengthOfChain++;
                position++;
            }else if(playerTiles[position].getValue() == playerTiles[position + 1].getValue() && playerTiles[position].getColor() == playerTiles[position + 1].getColor()){
                position++;
            }else if(playerTiles[position].getValue() != playerTiles[position + 1].getValue()){ 
                if(lengthOfChain >= 4){
                    numberOfChain++;
                }
                lengthOfChain = 1;
                position++;
            }           
        }

        if(numberOfChain >= 3){
            numberOfChain = 0;
            lengthOfChain = 1;
            return true;
        }
        //for checking from scracth
        numberOfChain = 0;
        lengthOfChain = 1;

        return false;
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        sortThePlayerHand();
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < 15; i++) {
            if ( playerTiles[i].getValue() != 30){
                System.out.print( (i) + ")"+playerTiles[i].toString() + " ");
            }
            
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
