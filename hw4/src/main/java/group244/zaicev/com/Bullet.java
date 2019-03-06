package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class for working with mini bullet
 */
class Bullet extends Projectile {

    /**
     * Constructor
     */
    Bullet(GraphicsContext graphicsContext, Vec2d startCoord, double speedX, double speedY) {
        setExplodeRadius(30);
        setPaint(Color.BLACK);
        setGraphicsContext(graphicsContext);

        setX0((int) startCoord.x);
        setY0((int) startCoord.y);

        setSpeedX(speedX);
        setSpeedY(speedY);
        setG(1);
    }

    @Override
    protected int bulletSize() {
        return 8;
    }
}