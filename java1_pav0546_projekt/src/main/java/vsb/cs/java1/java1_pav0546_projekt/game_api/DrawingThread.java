package vsb.cs.java1.java1_pav0546_projekt.game_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import vsb.cs.java1.java1_pav0546_projekt.game_assets.Game;
import vsb.cs.java1.java1_pav0546_projekt.game_assets.GameStatus;
import vsb.cs.java1.java1_pav0546_projekt.entities.Map;
import vsb.cs.java1.java1_pav0546_projekt.entities.TopBarManager;

import java.util.logging.Logger;

public class DrawingThread extends AnimationTimer {
    public static final double FPS = 60.0;
    private static final Logger logger = Logger.getLogger(DrawingThread.class.getName());

    private int blockSize;
    private Canvas canvas;
    private GraphicsContext gc;
    private long lastTime;
    private Game game;
    private Point2D mapSize;
    private Random random;
    private Text[] texts;
    private TopBarManager topBarManager;
    private List<Map> maps;
    private final String BearerToken;


    public DrawingThread(Canvas canvas, Text[] texts, String BearerToken) {
        this.canvas = canvas;
        this.gc = this.canvas.getGraphicsContext2D();
        blockSize = 40;
        this.maps = new ArrayList<>();
        this.topBarManager = new TopBarManager();
        this.game = gameGenerator(GameStatus.PLAYABLE);
        this.texts = texts;
        this.BearerToken = BearerToken;
    }

    @Override
    public void handle(long now) {
        if (topBarManager.getTimeLeft() == 0) {
            if (game.showGameChangeScene()) {
                gc.setFont(new javafx.scene.text.Font("Arial", blockSize));
                gc.setFill(Color.WHITE);
                gc.setStroke(Color.BLACK);
                gc.fillText("Game over: Time exceeded", 240, this.canvas.getHeight() / 2 - blockSize);
                gc.setFont(new javafx.scene.text.Font("Sant-Serif", blockSize / 2));
                gc.fillText("Collected points " + topBarManager.getScore(), 410, this.canvas.getHeight() / 2);
                topBarManager.draw(texts);
                logger.info("Game over: Time exceeded");
                this.maps.getLast().setMapAfter(game.getWorld());
                logger.info("The map after the change is:");
                logger.info(this.maps.getLast().getMapAfter());

                JSONObject recordDTO = new JSONObject();
                recordDTO.put("score", topBarManager.getScore());
                recordDTO.put("pointWeight", topBarManager.getPointWeight());

                JSONArray mapsJSON = new JSONArray();

                for (Map map : this.maps) {
                    JSONObject mapObject = new JSONObject();
                    mapObject.put("mapLevel", map.getMapLevel());
                    mapObject.put("mapBefore", map.getMapBefore());
                    mapObject.put("mapAfter", map.getMapAfter());

                    mapsJSON.put(mapObject);
                }

                JSONObject mainObject = new JSONObject();
                mainObject.put("recordDTO", recordDTO);
                mainObject.put("maps", mapsJSON);

                String jsonString = mainObject.toString();

                Pair<Integer, String> response = NetworkRequest.makeRequest("https://turbolaft.com/secured/createRecord", "POST",
                        "application/json", "application/json", BearerToken,
                        jsonString);

                if (response.getKey() == 200) {
                    logger.info(response.getValue());
                } else {
                    logger.warning(response.getValue());
                }

                stop();
            }
            return;
        }

        double deltaT = (now - lastTime) / 1e9;

        long sleepTime = Math.round(1000.0 / FPS - deltaT * 1e3);

        if (game.getGameStatus() != GameStatus.STOPPED) {
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (now > 0) {
                this.game.simulate(deltaT, null);
            }

            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            this.game.draw(this.gc, 0, 0);
            if (game.getGameStatus() == GameStatus.PLAYABLE) {
                topBarManager.simulateTime();
            }
            topBarManager.draw(texts);
        } else {
            if (game.showGameChangeScene()) {
                this.maps.getLast().setMapAfter(game.getWorld());
                logger.info("The map after the change is:");
                logger.info(this.maps.getLast().getMapAfter());
                this.game = gameGenerator(GameStatus.PREGAME);
            }
        }

        lastTime = now;
    }

    public Game getGame() {
        return this.game;
    }

    private Game gameGenerator(GameStatus gameStatus) {
        int lowerBoundWidth = (int) canvas.getWidth() / blockSize,
                upperBoundWidth = lowerBoundWidth * 2,
                lowerBoundHeight = (int) canvas.getHeight() / blockSize,
                upperBoundHeight = lowerBoundHeight * 2;
        random = new Random();

        mapSize = new Point2D(
                        (random.nextInt(upperBoundWidth - lowerBoundWidth + 1) + lowerBoundWidth) * blockSize,
                        (random.nextInt(upperBoundHeight - lowerBoundHeight + 1) + lowerBoundHeight) * blockSize
                    );
        this.maps.add(new Map(this.maps.size()));
        logger.info("The width is " + mapSize.getX());
        logger.info("The height is " + mapSize.getY());
        byte[][] map = this.maps.getLast().mapGenerate(mapSize, blockSize);
        Game generatedGame = new Game(blockSize, this.canvas.getWidth(), this.canvas.getHeight(), this.mapSize, this.canvas,
                this.topBarManager, gameStatus, map);

        logger.info("Map generated successfully!\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }

        logger.info(sb.toString());

        this.topBarManager.setGame(generatedGame);
        return generatedGame;
    }


}
