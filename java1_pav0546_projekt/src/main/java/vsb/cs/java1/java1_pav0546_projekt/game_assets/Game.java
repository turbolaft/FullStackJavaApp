package vsb.cs.java1.java1_pav0546_projekt.game_assets;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import vsb.cs.java1.java1_pav0546_projekt.entities.TopBarManager;

public class Game implements DrawableSimulable {

    private static Logger logger = Logger.getLogger(Game.class.getName());
    private final Canvas canvas;
    public final double blockSize;
    private double width;
    private double height;
    private Point2D mapSize;
    @Getter
    private byte[][] world;
    private byte[][] sceneWorld;
    private double cameraX;
    private double cameraY;
    private Entity[] objects;
    private GameStatus gameStatus;
    private TopBarManager topBarManager;
    private Map<String, URL> resources;

    public Game(double blockSize, double width, double height, Point2D mapSize, Canvas canvas, TopBarManager topBarManager, GameStatus gameStatus, byte[][] world) {
        this.blockSize = blockSize;
        this.width = width;
        this.height = height;
        this.mapSize = mapSize;
        this.cameraX = 0;
        this.cameraY = 0;
        this.world = world;
        this.gameStatus = gameStatus;
        this.canvas = canvas;
        world = new byte[(int) (mapSize.getY() / blockSize)][(int) (mapSize.getX() / blockSize)];
        sceneWorld = new byte[(int) (height / blockSize)][(int) (width / blockSize)];

        for (int i = 0; i < sceneWorld.length; i++) {
            for (int j = 0; j < sceneWorld[0].length; j++) {
                sceneWorld[i][j] = 2;
            }
        }

        logger.info("Size for Y is " + world.length + " Size for X " + world[1].length);
        objects = new Entity[7];
        resources = new HashMap<>();
        //scanResources();
        URL[] playerImages = {
            getClass().getResource("3d.png"),
            getClass().getResource("3l.png"),
            getClass().getResource("3r.png")
        };
        URL[] pointImages = {
            getClass().getResource("6t.png"),
            getClass().getResource("6m.png"),
            getClass().getResource("6b.png")
        };
        URL[] gateImages = {
            getClass().getResource("7.png"),
            getClass().getResource("2.jpg")
        };
        objects[0] = new Entity((byte) 0, getClass().getResource("0.jpg"), this);
        objects[1] = new Player((byte) 3, playerImages, this);
        objects[2] = new Entity((byte) 2, getClass().getResource("2.jpg"), this);
        objects[3] = new Entity((byte) 4, getClass().getResource("4.jpg"), this);
        objects[4] = new Stone((byte) 5, getClass().getResource("5.jpg"), this);
        objects[5] = new Point((byte) 6, pointImages, this, (Player) objects[1]);
        objects[6] = new Gate((byte) 7, gateImages, this);
        this.topBarManager = topBarManager;
        //initializeMap();
    }

    public void draw(GraphicsContext gc, double x, double y) {
        for (int i = 0; i < height / blockSize; i++) {
            for (int j = 0; j < width / blockSize; j++) {
                int shiftWidth = (int) (cameraX / blockSize);
                int shiftHeight = (int) (cameraY / blockSize);

                for (Entity obj : objects) {
                    if (world[i + shiftHeight][j + shiftWidth] == obj.getTileCode()) {
                        obj.draw(gc, j * blockSize, i * blockSize);
                    }
                }
            }
        }

        if (gameStatus == GameStatus.PREGAME) {
            Boolean isFirstInstance = true;

            for (int i = sceneWorld.length - 1; i >= 0; i--) {
                for (int j = sceneWorld[0].length - 1; j >= 0; j--) {
                    if (sceneWorld[i][j] == 2 && isFirstInstance) {
                        sceneWorld[i][j] = 1;
                        isFirstInstance = false;
                        continue;
                    }
                    if (sceneWorld[i][j] == 2) objects[2].draw(gc, j * blockSize, i * blockSize);
                }
            }

            if (sceneWorld[0][0] == 1) {
                gameStatus = GameStatus.PLAYABLE;
            }
        }
    }

    public void simulate(double deltaT, byte[][] world) {
        for (Entity obj : objects) {
            obj.simulate(deltaT, this.world);
        }
    }

    public void gameChangePrepare() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        draw(gc, 0, 0);
        gameStatus = GameStatus.STOPPED;

        for (int i = 0; i < sceneWorld.length; i++) {
            for (int j = 0; j < sceneWorld[0].length; j++) {
                sceneWorld[i][j] = 1;
            }
        }
    }

    public void positionHandling(Player player, Boolean condition, int changeY, int changeX) {
        if (gameStatus != GameStatus.PLAYABLE) return;
        byte blockType = world[(int) player.getCurrentPos().getY() + changeY][(int) player.getCurrentPos().getX() + changeX];

        if (blockType > 1 && blockType != 5 && blockType != 7) return;

        if (blockType == 5) {
            for (Entity en : objects) {
                if (en instanceof Stone stone) {
                    Point2D stonePos = new Point2D(player.getCurrentPos().getX() + changeX, player.getCurrentPos().getY() + changeY);

                    if (world[(int) stonePos.getY() + changeY][(int) stonePos.getX() + changeX] == 1) {
                        stone.moveEntity(
                            new Point2D(stonePos.getX(), stonePos.getY()),
                            new Point2D(changeX, changeY),
                            world
                        );
                    } else return;
                }
            }
        }

        if (condition) {
            if (changeX == 0) {
                if (changeY > 0) { cameraY += blockSize; } else { cameraY -= blockSize; }
            } else {
                if (changeX > 0) { cameraX += blockSize; } else { cameraX -= blockSize; }
            }
        }
        
        player.moveEntity(
            new Point2D(player.getCurrentPos().getX(), player.getCurrentPos().getY()),
            new Point2D(changeX, changeY),
            world
        );

        player.setCurrentPos(new Point2D(player.getCurrentPos().getX() + changeX, player.getCurrentPos().getY() + changeY));

        if (blockType == 7) {
            this.gameChangePrepare();
        }
    }

    public Boolean showGameChangeScene() {
        for (int i = 0; i < height / blockSize; i++) {
            for (int j = 0; j < width / blockSize; j++) {

                if (sceneWorld[i][j] == 1) {
                    sceneWorld[i][j] = 2;

                    objects[2].draw(canvas.getGraphicsContext2D(), j * blockSize, i * blockSize);
                    return false;
                }
            }
        }
        
        return true;
    }

//    private void initializeMap() {
//        URL fileName = getClass().getResource("boundBlockPattern.txt");
//        try {
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fileName.openStream()));
//            String line;
//            int j = 0;
//            while ((line = reader.readLine()) != null) {
//                for (int k = 0; k < line.length(); k++) {
//                    world[j][k] = (byte) (line.charAt(k) - '0');
//                    if (world[j][k] == 10) {
//                        System.out.print("New line");
//                    }
//                }
//                j++;
//            }
//
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (int k = 0; k < mapSize.getY() / blockSize; k++) {
//            for (int j = 0; j < mapSize.getX() / blockSize; j++) {
//                System.out.print(world[k][j]);
//            }
//            System.out.println();
//        }
//    }

    private void scanResources() {
        // Get the class loader
        ClassLoader classLoader = getClass().getClassLoader();

        // Attempt to load resources from the classpath
        try {
            Enumeration<URL> resources = classLoader.getResources("");
            while (resources.hasMoreElements()) {
                URL resourceUrl = resources.nextElement();
                if (resourceUrl.getProtocol().equals("file")) {
                    File folder = new File(resourceUrl.toURI());
                    for (final File fileEntry : folder.listFiles()) {
                        if (fileEntry.isFile() && !fileEntry.getName().endsWith(".class")) {
                            this.resources.put(fileEntry.getName(), fileEntry.toURI().toURL());
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to scan resources", e);
        }

        // Print out the scanned resources
        for (Map.Entry<String, URL> entry : this.resources.entrySet()) {
            System.out.println("Resource: " + entry.getKey() + ", URL: " + entry.getValue());
        }
    }
    
    public double getBlockSize() {
        return blockSize;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    public double getCameraX() {
        return cameraX;
    }

    public void setCameraX(double cameraX) {
        this.cameraX = cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Point2D getMapSize() {
        return mapSize;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public TopBarManager getTopBarManager() {
        return this.topBarManager;
    }

    public Map<String, URL> getResources() {
        return resources;
    }
}
