package group244.zaicev.com;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/** Class for using Map */
public class Map {
    private static final int HEIGHT = 700;
    private static final int WIDTH = 1000;

    private static final int BLOCK_SIZE = 100;

    private static final int[] MAP_Y = {0, 5, 2, 5, 4, 2, 2, 1, 4, 5, 3, 3, 0};
    private static final int[] MAP_X = {0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10};

    private final GraphicsContext graphicsContext;

    private static ArrayList<Double> maxX = new ArrayList<>();
    private static ArrayList<Double> maxY = new ArrayList<>();

    /**
     * Constructor
     */
    Map(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Method for drawing the map
     */
    public void draw() {
        graphicsContext.setFill(Color.web("#4a8282"));
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
        double[] DMAP_X = Arrays.stream(MAP_X).mapToDouble(x -> (double) x)
                .map(x -> x * BLOCK_SIZE)
                .toArray();
        double[] DMAP_Y = Arrays.stream(MAP_Y).mapToDouble(x -> (double) x)
                .map(x -> 7 -x)
                .map(x -> x * BLOCK_SIZE)
                .toArray();
        graphicsContext.setFill(Color.web("#4a4446"));
        graphicsContext.fillPolygon(DMAP_X, DMAP_Y, MAP_X.length);
        drawSnow();
    }

    /**
     * Method for drawing the snow
     */
    private void drawSnow() {
        int maxHeight = 300;
        for (int i = 0; i <= WIDTH; i += 10) {
            if (getGroundY(i) <= maxHeight) {
                if (i == 0) {
                    maxX.add((double) 0);
                    maxY.add((double) maxHeight);
                }
                maxX.add((double)i);
                maxY.add((double)getGroundY(i));
            }

            if ((getGroundY(i) > maxHeight) && !maxX.isEmpty()) {
                graphicsContext.setFill(Color.WHITE);
                double[] doubleMaxX = maxX.stream().mapToDouble(x -> x).toArray();
                double[] doubleMaxY = maxY.stream().mapToDouble(x -> x).toArray();
                graphicsContext.fillPolygon(doubleMaxX, doubleMaxY, maxX.size());
                maxX.clear();
                maxY.clear();
            }
        }
    }

    /** Method that putting object on the ground */
    public void putOnTheGround(Coordinate obj) {
        obj.setY(getGroundY(obj.getX()));
    }

    /** Method that checks whether an object is on the ground */
    public boolean isOnTheGround(Coordinate obj) {
        return obj.getY() > getGroundY(obj.getX());
    }

    /**
     * Returns the y coordinate on earth at the x coordinate
     */
    private int getGroundY(int x) {
        int blockNumber = x / BLOCK_SIZE;
        int y = HEIGHT - MAP_Y[blockNumber + 1] * BLOCK_SIZE;

        switch (MAP_Y[blockNumber + 2] - MAP_Y[blockNumber + 1]) {
            case 1:
                y -= x - blockNumber * BLOCK_SIZE;
                break;
            case -1:
                y += x - blockNumber * BLOCK_SIZE;
                break;
            case 2:
                y -= 2 * (x - blockNumber * BLOCK_SIZE);
                break;
            case -2:
                y += 2 * (x - blockNumber * BLOCK_SIZE);
                break;
            case 3:
                y -= 3 * (x - blockNumber * BLOCK_SIZE);
                break;
            case -3:
                y += 3 * (x - blockNumber * BLOCK_SIZE);
                break;
        }

        return y;
    }
}
