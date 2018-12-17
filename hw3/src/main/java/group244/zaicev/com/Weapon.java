package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Class for working with weapon */
public class Weapon implements Coordinate {
    private static final int GUN_SIZE = 20;

    private final GraphicsContext graphicsContext;

    private int x;
    private int y;
    private int fi;

    /** Constructor */
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
        fi--;
    }

    /** Method for lowering weapon */
    public void gunDown() {
        if (fi > 45) {
            return;
        }
        fi++;
    }

    /** Method for drawing weapon */
    public void draw() {
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setLineWidth(8);
        graphicsContext.strokeLine(x, y, x + Math.cos(Math.PI * fi / 180) * GUN_SIZE, y + Math.sin(Math.PI * fi / 180) * GUN_SIZE);
        int weaponSize = 40;
        graphicsContext.strokeLine(x - weaponSize / 3, y, x + weaponSize / 3, y);
    }

    /** Method that implements the shot */
    public Bullet shot() {
        double startSpeed = 5;
        double speedX = startSpeed * Math.cos(Math.PI * (-fi) / 180);
        double speedY = startSpeed * Math.sin(Math.PI * (-fi) / 180);

        double startFi = (Math.PI * fi / 180);
        Vec2d startCoord = new Vec2d((x + Math.cos(startFi) * GUN_SIZE) + 5, (y + Math.sin(startFi) * GUN_SIZE));

        return new Bullet(graphicsContext, startCoord, speedX * 2, speedY * 3);
    }
}