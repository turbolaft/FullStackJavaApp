package vsb.cs.java1.java1_pav0546_projekt.entities;

import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

public class Map {
    private int mapLevel;
    private int numberOfStones;
    private int numberOfPoints;
    @Getter
    private String mapBefore;
    private String mapAfter;
    Random random = new Random();

    public Map(int mapLevel) {
        this.mapLevel = mapLevel;
        this.numberOfPoints = 20;
    }

    public void setNumberOfStones(Point2D mapSize, int blockSize) {
        int stoneBount = (int) (mapSize.getX() / blockSize + mapSize.getY() / blockSize);
        this.numberOfStones = random.nextInt(stoneBount) + stoneBount;

    }

    public byte[][] mapGenerate(Point2D mapSize, int blockSize) {
        int width = (int) mapSize.getX();
        int height = (int) mapSize.getY();
        setNumberOfStones(mapSize, blockSize);
        Point2D[] stones = entityGenerator(this.numberOfStones + (this.numberOfStones / 3 * mapLevel), mapSize, blockSize);
        Point2D[] points = entityGenerator(numberOfPoints + (numberOfPoints / 3 * mapLevel), mapSize, blockSize);

        byte[][] map = new byte[height / blockSize][width / blockSize];

        for (int row = 0; row < height; row += blockSize) {
            for (int col = 0; col < width; col += blockSize) {
                byte writeValue = 0;

                if (row == blockSize && col == blockSize) {
                    writeValue = 3;
                } else if (row == 0 || col == 0 || row == height - blockSize || col == width - blockSize) {
                    writeValue = 2;
                } else if ((row == (height / 2) - (blockSize * 2) && col < width / 2) || (row == (height / 2) + (blockSize * 2) && col > width / 2)) {
                    writeValue = 4;
                } else if (row == height - blockSize * 3 && col == width - blockSize * 2) {
                    writeValue = 7;
                } else {
                    for (int i = 0; i < stones.length; i++) {
                        if (stones[i].getX() == (col / blockSize) && stones[i].getY() == (row / blockSize)) {
                            writeValue = 5;
                        }
                    }

                    for (int i = 0; i < points.length; i++) {
                        if (points[i].getX() == (col / blockSize) && points[i].getY() == (row / blockSize)) {
                            writeValue = 6;
                        }
                    }
                }

                // Assign the writeValue to the corresponding position in the map array
                map[row / blockSize][col / blockSize] = writeValue;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }
        this.mapBefore = sb.toString();

        return map;
    }

    private Point2D[] entityGenerator(int size, Point2D mapSize, int blockSize) {
        Point2D[] stones = new Point2D[size];

        for (int i = 0; i < size; i++) {
            stones[i] = new Point2D(random.nextInt((int) (mapSize.getX() / blockSize) - 1) + 1, random.nextInt((int) (mapSize.getY() / blockSize) - 1) + 1);
        }

        return stones;
    }

    public int getMapLevel() {
        return this.mapLevel;
    }

    public void setMapAfter(byte[][] mapAfter) {
        this.mapAfter = "";
        for (int i = 0; i < mapAfter.length; i++) {
            for (int j = 0; j < mapAfter[i].length; j++) {
                this.mapAfter += mapAfter[i][j];
            }
            this.mapAfter += "\n";
        }
    }

    public String getMapAfter() {
        return this.mapAfter;
    }
}
