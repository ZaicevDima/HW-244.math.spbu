package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Bullet class. */
public class Bullet implements Coordinate {
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_HEIGHT = 765;
    private static final int BULLET_SIZE = 20;
    private final GraphicsContext gc;
    private final Image bullet;

    private boolean exploded = false;

    private int x0;
    private int y0;
    private int x;
    private int y;
    private double timer;
    private double speedX;
    private double speedY;

    /** Create Bullet. */
    Bullet(GraphicsContext gc, Vec2d startCoord, double speedX, double speedY) {
        this.gc = gc;
        this.speedX = speedX;
        this.speedY = speedY;
        this.timer = 0;
        this.x0 = (int) startCoord.x;
        this.y0 = (int) startCoord.y;
        bullet = new Image("bullet.png");
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
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    /** Draw Bullet. */
    public void draw() {
        gc.drawImage(bullet, x - BULLET_SIZE / 2, y - BULLET_SIZE / 2);
        x = x0 + (int) (speedX * timer);
        double g = 0.5;
        y = (y0 - (int) (speedY * timer - g * timer * timer / 2));
        timer += 0.5;

        if ((x < 0) || (x > MAX_WIDTH) || (y < 0) || (y > MAX_HEIGHT)) {
            explode();
        }
    }

    /** Explode Bullet. */
    public void explode() {
        exploded = true;
    }

    /** Check bullet's destruction. */
    public boolean isExploded() {
        return exploded;
    }
}