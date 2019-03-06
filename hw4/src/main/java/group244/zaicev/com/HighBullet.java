package group244.zaicev.com;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class for working with big bullet
 */
class HighBullet extends Projectile {

    /**
     * Constructor
     */
    HighBullet(GraphicsContext graphicsContext, Vec2d startCoord, double speedX, double speedY) {
        setExplodeRadius(80);
        setPaint(Color.RED);
        setGraphicsContext(graphicsContext);

        setX0((int) startCoord.x);
        setY0((int) startCoord.y);

        setSpeedX(speedX);
        setSpeedY(speedY);
        setG(1.5);
    }

    @Override
    protected int bulletSize() {
        return 12;
    }
}
