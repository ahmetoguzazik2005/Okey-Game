import java.util.Random;
// my class for trying my functions whether they work or not 
public class trial {
    public static void main(String[] args) {
        Player player1 = new Player("Hidayet");
        Tile[] tiles = new Tile[15];  // Array for 14 tiles
        int currentTile = 0;
        Random random = new Random();

        // Array of colors
        char[] colors = {'Y', 'B', 'R', 'K'};
        
        // Logic to fill the tile array
        for (int i = 1; i < 15; i++) {
            for (int j = 0; j < colors.length; j++) {
                if (currentTile < 15) {
                    tiles[currentTile++] = new Tile(i, colors[random.nextInt(4)]);
                }
            }
        }
        player1.playerTiles = tiles;
        player1.displayTiles();
        player1.sortThePlayerHand();
        player1.displayTiles();
        System.out.println(player1.getAndRemoveTile(4));
        player1.displayTiles();
        player1.addTile(new Tile(3, 'Y'));
        player1.displayTiles();
    }
}
