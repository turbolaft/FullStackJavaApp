package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Player extends Entity {

    private int playerState;
    private Point2D currentPos;
    private Image[] leftRightCase;

    public Player(byte tileCode, URL[] tilePath, Game game) {
        super(tileCode, tilePath[0], game);
        currentPos = new Point2D(1, 1);
        leftRightCase = new Image[2];
        leftRightCase[0] = new Image(tilePath[1].toExternalForm());
        leftRightCase[1] = new Image(tilePath[2].toExternalForm());
        playerState = 0;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        if (playerState == 0) {
            gc.drawImage(tileImage, x, y);
        } else {
            gc.drawImage(leftRightCase[playerState - 1], x, y);
        }
    }

    @Override
    public void simulate(double deltaT, byte[][] world) {
        this.game.getCanvas().requestFocus();
        this.game.getCanvas().setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case LEFT:
                    playerState = 1;
                    game.positionHandling(this, (game.getMapSize().getX() - game.getWidth() / 2) > (currentPos.getX() * game.blockSize) && game.getCameraX() > 0, 0, -1);
                    break;
                case RIGHT:
                    playerState = 2;
                    game.positionHandling(this, (currentPos.getX() * game.blockSize) > (game.getWidth() / 2) && (game.getCameraX() + game.getWidth()) < game.getMapSize().getX(), 0, 1);
                    break;
                case UP:
                    playerState = 0;
                    game.positionHandling(this, (game.getMapSize().getY() - game.getHeight() / 2) > (currentPos.getY() * game.blockSize) && game.getCameraY() > 0, -1, 0);
                    break;
                case DOWN:
                    playerState = 0;
                    game.positionHandling(this, (currentPos.getY() * game.blockSize) > (game.getHeight() / 2) && (game.getCameraY() + game.getHeight()) < game.getMapSize().getY(), 1, 0);
                    break;
            }
        });
    }
    
    public Point2D getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Point2D newObj) {
        this.currentPos = newObj;
    }

    public int getPlayerState() {
        return this.playerState;
    }
}
