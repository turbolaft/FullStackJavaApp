package vsb.cs.java1.java1_pav0546_projekt.bdash;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import vsb.cs.java1.java1_pav0546_projekt.game_api.DrawingThread;

public class GameController {
    @FXML
    private Canvas canvas;
    @FXML
    private Text pointsAvail;
    @FXML
    private Text time;
    @FXML
    private Text points;

    private DrawingThread timer;

    public GameController() {}

    void start(String BearerToken) {
        timer = new DrawingThread(canvas, new Text[]{pointsAvail, time, points}, BearerToken);
        timer.start();
    }

    void stop() {
        timer.stop();
    }
}
