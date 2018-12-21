package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Class for working with Bullet */
public class Bullet implements Coordinate {
    private final GraphicsContext graphicsContext;
    private final Image bullet;

    private boolean isDestroyed = false;

    private int x0;
    private int y0;
    private int x;
    private int y;
    private double timer;
    private double speedX;
    private double speedY;

    /** Constructor */
    Bullet(GraphicsContext gc, Vec2d startCoord, double speedX, double speedY) {
        this.graphicsContext = gc;
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

    /** Metod for drawing bullet */
    public void draw() {
        int bulletSize = 10;
        graphicsContext.drawImage(bullet, x - bulletSize / 2, y - bulletSize / 2);
        x = x0 + (int) (speedX * timer);
        double g = 0.5;
        y = (y0 - (int) (speedY * timer - g * timer * timer / 2));
        timer += 0.5;

        int maxWidth = 1000;
        int maxHeight = 700;
        if ((x < 0) || (x > maxWidth) || (y < 0) || (y > maxHeight)) {
            destroy();
        }
    }

    /** Destruction bullet */
    public void destroy() {
        isDestroyed = true;
    }

    /** Method for check bullet's destruction */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}