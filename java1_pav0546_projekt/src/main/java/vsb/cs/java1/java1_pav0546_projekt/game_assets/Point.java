package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Point extends Entity {
    private Image[] pics;
    private int currentPic;
    private final Player player;
    private long lastUpdate = System.currentTimeMillis();

    public Point(byte tileCode, URL[] tilePath, Game game, Player player) {
        super(tileCode, tilePath[0], game);
        this.pics = new Image[tilePath.length];
        this.player = player;
        for (int i = 0; i < tilePath.length; i++) {
            this.pics[i] = new Image(tilePath[i].toExternalForm());
        }
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.drawImage(pics[currentPic], x, y);
    }

    @Override
    public void simulate(double deltaT, byte[][] world) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate >= 100) {
            currentPic++;
            if (currentPic == 3) currentPic = 0;

            lastUpdate = currentTime;
        }

        Point2D collisionCoord = getColission(world);
        if (collisionCoord != null) {
            world[(int) collisionCoord.getY()][(int) collisionCoord.getX()] = 1;
            game.getTopBarManager().simulate();
        }
    }

    private Point2D getColission(byte[][] world) {
        Point2D[] directions = {new Point2D(1, 0), new Point2D(0, 1), new Point2D(-1, 0), new Point2D(0, -1)};

        for (int i = 0; i < directions.length; i++) {
            int y = (int) player.getCurrentPos().getY() + (int) directions[i].getY();
            int x = (int) player.getCurrentPos().getX() + (int) directions[i].getX();
            if (world[y][x] == tileCode) {
                return new Point2D(x, y);
            }
        }

        return null;
    }
}
