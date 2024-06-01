package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class Stone extends Entity {
    private List<Point2D> objectsToMove;
    
    public Stone(byte tileCode, URL tilePath, Game game) {
        super(tileCode, tilePath, game);
        objectsToMove = new ArrayList<>();
    }

    public void simulate(double deltaT, byte[][] world) {

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == tileCode) {
                    if (world[i + 1][j] == 1) {
                        objectsToMove.add(new Point2D(j, i));
                        objectsToMove.add(new Point2D(0, 1));
                    } else if (world[i + 1][j] == tileCode) {
                        if (world[i][j - 1] == 1 && world[i + 1][j - 1] == 1) {
                            objectsToMove.add(new Point2D(j, i));
                            objectsToMove.add(new Point2D(-1, 0));
                        } else if (world[i][j + 1] == 1 && world[i + 1][j + 1] == 1) {
                            objectsToMove.add(new Point2D(j, i));
                            objectsToMove.add(new Point2D(1, 0));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < objectsToMove.size(); i += 2) {
            moveEntity(objectsToMove.get(i), objectsToMove.get(i + 1), world);
        }

        objectsToMove.clear();
    }
}
