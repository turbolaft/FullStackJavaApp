package vsb.cs.java1.java1_pav0546_projekt.entities;

import javafx.scene.text.Text;
import lombok.NoArgsConstructor;
import vsb.cs.java1.java1_pav0546_projekt.entities.Map;
import vsb.cs.java1.java1_pav0546_projekt.game_assets.Game;

public class TopBarManager {
    private int score;
    private int prevScore;
    private int timeLeft;
    private int pointWeight;
    private int lastTimeTakePoint;
    private Boolean isUpToDate;
    private long lastUpdate = System.currentTimeMillis();
    private Game game;


    public TopBarManager() {
        this.timeLeft = 60;
        this.score = 0;
        this.prevScore = -1;
        this.lastTimeTakePoint = 1000;
        this.pointWeight = 10;
        this.isUpToDate = true;
    }

    public void simulate() {
        prevScore = score;
        score += pointWeight;

        if (lastTimeTakePoint - timeLeft <= 1) {
            pointWeight += 5;
        }

        lastTimeTakePoint = timeLeft;
        isUpToDate = true;
    }

    public void simulateTime() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate >= 1_000) {
            timeLeft--;
            lastUpdate = currentTime;
            isUpToDate = true;
        }

        if (timeLeft == 0) {
            game.gameChangePrepare();
        }
    }

    public void draw(Text[] points) {
        if (isUpToDate) {
            if (prevScore == -1) {
                points[2].setText("Points: " + Integer.toString(score));
            } else {
                points[2].setText("Points: " + Integer.toString(score) + " +" + Integer.toString(score - prevScore));
            }
        
            points[1].setText("Time left: " + Integer.toString(timeLeft));
        
            points[0].setText("Point weight: " + Integer.toString(pointWeight));
            isUpToDate = false;
        }
    }

    public int getTimeLeft() {
        return this.timeLeft;
    }

    public int getScore() {
        return this.score;
    }

    public int getPointWeight() {
        return this.pointWeight;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
