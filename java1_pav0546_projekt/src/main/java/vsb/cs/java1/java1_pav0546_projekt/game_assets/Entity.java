package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.net.URL;

class Entity implements DrawableSimulable {
    protected byte tileCode;
    protected Image tileImage;
    protected final Game game;

    public Entity(byte tileCode, URL tilePath, Game game) {
        this.tileCode = tileCode;
        this.tileImage = new Image(tilePath.toExternalForm());
        this.game = game;
    }

    public void draw(GraphicsContext gc, double x, double y) {
        gc.drawImage(tileImage, x, y);
    }

    public void simulate(double deltaT, byte[][] world) {
        
    }

    public void moveEntity(Point2D coord, Point2D changeCoord, byte[][] world) {
        world[(int) coord.getY()][(int) coord.getX()] = 1;
        world[(int) coord.getY() + (int) changeCoord.getY()][(int) coord.getX() + (int) changeCoord.getX()] = tileCode;
    }

    public byte getTileCode() {
        return this.tileCode;
    }
}
