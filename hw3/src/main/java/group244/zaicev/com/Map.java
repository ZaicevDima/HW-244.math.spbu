package group244.zaicev.com;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/** The Map of Game. */
public class Map {
    private static final int HEIGHT = 700;
    private static final int BLOCK_SIZE = 100;
    private static final int[] MAP_Y = {0, 4, 3, 5, 4, 2, 2, 1, 3, 5, 3, 3, 0};
    private static final int[] MAP_X = {0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10};


    private static ArrayList<Double> MAP_MAX_X = new ArrayList<>();
    private static ArrayList<Double> MAP_MAX_Y = new ArrayList<>();

    private final GraphicsContext graphicsContext;

    /** Create Map. */
    Map(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /** Draw Map. */
    public void draw() {
        graphicsContext.setFill(Color.web("#4a8282"));
        graphicsContext.fillRect(0, 0, 1000, HEIGHT);
        double[] DMAP_X = Arrays.stream(MAP_X).mapToDouble(x -> (double) x)
                .map(x -> x * 100)
                .toArray();
        double[] DMAP_Y = Arrays.stream(MAP_Y).mapToDouble(x -> (double) x)
                .map(x -> 7 -x)
                .map(x -> x * 100)
                .toArray();
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillPolygon(DMAP_X, DMAP_Y, MAP_X.length);
        for (int i = 0; i <= 1000; i += 10) {

            if (getGroundY(i) < 300) {
                MAP_MAX_X.add((double)i);
                MAP_MAX_Y.add((double)getGroundY(i));
            }

            if ((getGroundY(i) > 300) && !MAP_MAX_X.isEmpty()) {
                graphicsContext.setFill(Color.WHITE);
                double[] DMAP_MAX_X = MAP_MAX_X.stream().mapToDouble(x -> x).toArray();
                double[] DMAP_MAX_Y = MAP_MAX_Y.stream().mapToDouble(x -> x).toArray();
                graphicsContext.fillPolygon(DMAP_MAX_X, DMAP_MAX_Y, MAP_MAX_X.size());
                MAP_MAX_X.clear();
                MAP_MAX_Y.clear();
            }
        }
    }

    /** Put object on the ground. */
    public void putOnTheGround(Coordinate obj) {
        obj.setY(getGroundY(obj.getX()));
    }

    /** Check object location. */
    public boolean isOnTheGround(Coordinate obj) {
        return obj.getY() > getGroundY(obj.getX());
    }

    private int getGroundY(int x) {
        final int blockNumber = x / BLOCK_SIZE;
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

        }

        return y;
    }
}
