import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TileMap {


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;
    private int y;

    public int getTileSize() {
        return tileSize;
    }

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private BufferedImage tileset;
    private Tile[][] tiles;

    public TileMap(String s, int tileSize) {
        this.tileSize = tileSize;

        try {
            BufferedReader br = new BufferedReader(new FileReader(s));

            mapHeight = Integer.parseInt(br.readLine());
            mapWidth = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            String delimiters = "\\s+"; //any whitespace
            for (int row = 0; row < mapHeight; row++) {

                String line = br.readLine();
                String[] tokens = line.split(delimiters);

                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception ups) {
            ups.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int rc = map[row][col];

                int r = rc / tiles[0].length;
                int c = rc % tiles[0].length;

                g.drawImage(
                        tiles[r][c].getImage(),
                        x+col * tileSize,
                        y+row * tileSize,
                        null
                );

            }
        }
    }

    public void loadTiles(String s){
        try{
            tileset = ImageIO.read(new File(s));
            int numTilesAcross = (tileset.getWidth() + 1) / (tileSize + 1);
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileset.getSubimage(
                        col * tileSize + col,
                        0,
                        tileSize,
                        tileSize
                );
                tiles[0][col] = new Tile(subimage, false);
                subimage = tileset.getSubimage(
                        col * tileSize + col,
                        tileSize + 1,
                        tileSize,
                        tileSize
                );
                tiles[1][col] = new Tile(subimage, true);
            }
        }catch (Exception ups){
            ups.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getColTile(int x) {
        return x / tileSize;
    }

    public int getRowTile(int y) {
        return y / tileSize;
    }


    public boolean isBlocked(int row, int col){
        if(row > 0 && row < map.length && col > 0 && col < map[row].length) {
            int rc = map[row][col];
            int r = rc / tiles[0].length;
            int c = rc % tiles[0].length;
            return tiles[r][c].isBlocked();
        }else{
            return true;
        }
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int[][] getMap() {
        return map;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
