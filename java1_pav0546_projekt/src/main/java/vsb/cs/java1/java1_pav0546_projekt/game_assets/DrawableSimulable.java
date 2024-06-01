package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import javafx.scene.canvas.GraphicsContext;

interface DrawableSimulable {
    void draw(GraphicsContext gc, double x, double y);
    void simulate(double deltaT, byte[][] world);
}
