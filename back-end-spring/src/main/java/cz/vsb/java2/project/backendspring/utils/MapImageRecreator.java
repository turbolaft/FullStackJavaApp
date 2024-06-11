package cz.vsb.java2.project.backendspring.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapImageRecreator {

    public byte[][] imageToByte2DArray(String inputFilePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputFilePath));
        int width = image.getWidth();
        int height = image.getHeight();

        byte[][] result = new byte[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                result[y][x] = (byte) color;
            }
        }

        return result;
    }

    public static byte[][] stringToByte2DArray(String str) {
        String[] rowsStr = str.split("\n");
        int rows = rowsStr.length;
        int cols = rowsStr[0].length();

        byte[][] result = new byte[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = Byte.parseByte(String.valueOf(rowsStr[i].charAt(j)));
            }
        }

        return result;
    }

    public static byte[] generateMapImage(byte[][] mapData, int tileWidth, int tileHeight) throws IOException {
        int mapHeight = mapData.length;
        int mapWidth = mapData[0].length;

        int snapshotWidth = mapWidth * tileWidth;
        int snapshotHeight = mapHeight * tileHeight;

        BufferedImage snapshot = new BufferedImage(snapshotWidth, snapshotHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage[] tiles = loadTileImages();

        Graphics2D graphics = snapshot.createGraphics();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int tileIndex = mapData[y][x];
                BufferedImage tileImage = tiles[tileIndex];
                graphics.drawImage(tileImage, x * tileWidth, y * tileHeight, null);
            }
        }

        graphics.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(snapshot, "png", baos);
        baos.flush();
        return baos.toByteArray();
    }

    public static BufferedImage[] loadTileImages() throws IOException {
        BufferedImage[] tiles = new BufferedImage[8];
        int targetWidth = 15;
        int targetHeight = 15;

        for (int i = 0; i < tiles.length; i++) {
            if (i == 1) {
                continue;
            }

            String filePath = "/cz/vsb/java2/project/backendspring/" + i + ".png";

            try (InputStream inputStream = MapImageRecreator.class.getResourceAsStream(filePath)) {
                if (inputStream == null) {
                    throw new IOException("Resource not found: " + filePath);
                }

                BufferedImage originalImage = ImageIO.read(inputStream);

                if (originalImage == null) {
                    throw new IOException("Failed to load tile image at path: " + filePath);
                }

                Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

                BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
                resizedImage.getGraphics().dispose();

                tiles[i] = resizedImage;
            }
        }

        return tiles;
    }
}
