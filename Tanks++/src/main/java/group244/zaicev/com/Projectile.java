package group244.zaicev.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * Class for working with projectiles
 */
public abstract class Projectile implements Coordinate {
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_HEIGHT = 700;

    private static int explodeRadius;
    private GraphicsContext graphicsContext;

    private boolean isDestroyed = false;

    private int x0;
    private int y0;
    private int x;
    private int y;

    private double timer = 0;
    private double speedX;
    private double speedY;
    private double g;

    private Paint paint;

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

    /**
     * Drawing projectile
     */
    public void draw() {
        graphicsContext.setFill(getPaint());
        graphicsContext.fillRect(x - bulletSize() / 2, y - bulletSize() / 2, bulletSize(), bulletSize());
        x = x0 + (int) (speedX * timer);
        y = (y0 - (int) (speedY * timer - g * timer * timer / 2));
        timer += 0.5;

        if ((x < 0) || (x > MAX_WIDTH) || (y < 0) || (y > MAX_HEIGHT)) {
            destroy();
        }
    }

    /**
     * Returns bullet size
     */
    protected abstract int bulletSize();

    /**
     * Destroyed the projectile
     */
    public void destroy() {
        isDestroyed = true;
    }

    /**
     * Checks whether the projectile is destroyed
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Sets explode radius
     */
    static void setExplodeRadius(int explodeRadius) {
        Projectile.explodeRadius = explodeRadius;
    }

    /**
     * Sets G
     */
    void setG(double g) {
        this.g = g;
    }

    /**
     * Sets GraphicsContext
     */
    void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Sets start x
     */
    void setX0(int x0) {
        this.x0 = x0;
    }

    /**
     * Sets start y
     */
    void setY0(int y0) {
        this.y0 = y0;
    }

    /**
     * Sets speed x
     */
    void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     * Sets speed y
     */
    void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    /**
     * Sets paint
     */
    void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * Returns paint
     */
    private Paint getPaint() {
        return this.paint;
    }

    /**
     * Returns explode radius
     */
    public int getRadius() {
        return explodeRadius;
    }
}
