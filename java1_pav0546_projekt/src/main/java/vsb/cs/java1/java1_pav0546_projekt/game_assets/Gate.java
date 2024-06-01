package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import javafx.scene.image.Image;

import java.net.URL;

public class Gate extends Entity {
    private Image[] pics;
    private long lastUpdate = System.currentTimeMillis();

    public Gate(byte tileCode, URL[] tilePath, Game game) {
        super(tileCode, tilePath[0], game);

        pics = new Image[2];
        pics[0] = new Image(tilePath[0].toExternalForm());
        pics[1] = new Image(tilePath[1].toExternalForm());
    }

    @Override
    public void simulate(double deltaT, byte[][] world) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate >= 150) {
            if (tileImage == pics[0]) {
                tileImage = pics[1];
            } else {
                tileImage = pics[0];
            }

            lastUpdate = currentTime;
        }
    }
}
