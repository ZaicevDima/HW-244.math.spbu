package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;

/** Turret class. */
public class Turret implements Coordinate {
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_HEIGHT = 700;
    private static final int TURRET_SIZE = 40;
    private static final int GUN_SIZE = 20;
    private final GraphicsContext gc;

    private int x;
    private int y;
    private int fi;

    /** Create Turret. */
    Turret(GraphicsContext gc, int x, int y) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.fi = -45;

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        if ((x < 0) || (x > MAX_WIDTH)) {
            return;
        }
        this.x = x;
    }

    @Override
    public void setY(int y) {
        if ((y < 0) || (y > MAX_HEIGHT)) {
            return;
        }
        this.y = y;
    }

    /** Up the turret's gun. */
    public void gunUp() {
        if (fi < -225) {
            return;
        }
        fi--;
    }

    /** Down the turret's gun. */
    public void gunDown() {
        if (fi > 45) {
            return;
        }
        fi++;
    }

    /** Draw Turret. */
    public void draw() {
        gc.setLineWidth(8);
        gc.strokeLine(x, y, x + Math.cos(Math.PI * fi / 180) * GUN_SIZE, y + Math.sin(Math.PI * fi / 180) * GUN_SIZE);
        gc.strokeLine(x - TURRET_SIZE / 3, y, x + TURRET_SIZE / 3, y);
    }

    /** Gun shot. */
    public Bullet shot() {
        double startSpeed = 5;
        double speedX = startSpeed * Math.cos(Math.PI * (-fi) / 180);
        double speedY = startSpeed * Math.sin(Math.PI * (-fi) / 180);

        double startFi = (Math.PI * fi / 180);
        Vec2d startCoord = new Vec2d((x + Math.cos(startFi) * GUN_SIZE) + 5, (y + Math.sin(startFi) * GUN_SIZE));

        return new Bullet(gc, startCoord, speedX * 2, speedY * 3);
    }
}