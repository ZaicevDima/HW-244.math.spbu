package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class for working with weapons
 */
public class Weapon implements Coordinate {
    private static final int GUN_SIZE = 20;
    private static final int VERTICAL_SHIFT = 2;

    private final GraphicsContext graphicsContext;

    private int x;
    private int y;
    private int fi;
    private static final int HORIZONTAL_SHIFT = 2;

    /**
     * Constructor
     */
    Weapon (GraphicsContext gc, int x, int y) {
        this.graphicsContext = gc;
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
        int maxWidth = 1000;
        if ((x < 0) || (x > maxWidth)) {
            return;
        }
        this.x = x;
    }

    @Override
    public void setY(int y) {
        int maxHeight = 700;
        if ((y < 0) || (y > maxHeight)) {
            return;
        }
        this.y = y;
    }

    /** Method for raising weapon */
    public void gunUp() {
        if (fi < -225) {
            return;
        }
        fi -= VERTICAL_SHIFT;
    }

    /** Method for lowering weapon */
    public void gunDown() {
        if (fi > 45) {
            return;
        }
        fi += VERTICAL_SHIFT;
    }

    /** Method for moving weapons to the right */
    public void moveRight() {
        this.setX(this.getX() + HORIZONTAL_SHIFT);
    }

    /** Method for moving weapons to the left */
    public void moveLeft() {
        this.setX(this.getX() - HORIZONTAL_SHIFT);
    }

    /**
     * Method for drawing weapon
     */
    public void draw() {
        graphicsContext.setLineWidth(8);
        graphicsContext.strokeLine(x, y, x + Math.cos(Math.PI * fi / 180) * GUN_SIZE,
                y + Math.sin(Math.PI * fi / 180) * GUN_SIZE);
        int weaponSize = 40;
        graphicsContext.strokeLine(x - weaponSize / 3, y, x + weaponSize / 3, y);
    }

    /**
     * Method, which realise shot
     */
    public Projectile shot(int typeProjectile) {
        double startSpeed = 7;
        double speedX = (startSpeed * Math.cos(Math.PI * (-fi) / 180));
        double speedY = (startSpeed * Math.sin(Math.PI * (-fi) / 180));

        double startFi = (Math.PI * fi / 180);
        Vec2d startCoord = new Vec2d((x + Math.cos(startFi) * GUN_SIZE) + 3, (y + Math.sin(startFi) * GUN_SIZE));
        if (typeProjectile == 0) {
            return new Bullet(graphicsContext, startCoord, speedX * 2, speedY * 3);
        } else {
            return new HighBullet(graphicsContext, startCoord, speedX * 2, speedY * 3);
        }
    }

    /**
     * Checks whether the weapon is destroyed
     */
    public boolean weaponDestroy(Projectile projectile) {
        return Math.pow(this.getX() - projectile.getX(), 2) + Math.pow(this.getY() - projectile.getY(), 2)
                < Math.pow(projectile.getRadius(), 2);
    }
}