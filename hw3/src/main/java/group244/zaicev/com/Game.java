package group244.zaicev.com;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedList;

/** Main class for game */
public class Game extends Application {
    private static final int BASIC_WIDTH = 1000;
    private static final int BASIC_HEIGHT = 700;

    /** Start The Game. */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        LinkedList<KeyCode> keys = keyboardSettings(scene);

        int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight() + 40;
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.scale((double) screenWidth / BASIC_WIDTH, (double) screenHeight / BASIC_HEIGHT);

        gameMechanics(primaryStage, graphicsContext, keys);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /** Creates KeyCode list */
    private LinkedList<KeyCode> keyboardSettings(Scene scene) {
        LinkedList<KeyCode> input = new LinkedList<>();

        scene.setOnKeyPressed(
                keyEvent -> {
                    KeyCode code = keyEvent.getCode();

                    if (!input.contains(code)) {
                        input.add(code);
                    }
                });

        scene.setOnKeyReleased(
                keyEvent -> {
                    KeyCode code = keyEvent.getCode();
                    input.remove(code);
                });

        return input;
    }

    /**
     * Method that handles keyboard presses
     * @param primaryStage your Stage
     * @param graphicsContext your GraphicsContext
     * @param keys your KeyCode list
     */
    private void gameMechanics(Stage primaryStage, GraphicsContext graphicsContext, LinkedList<KeyCode> keys) {
        Map map = new Map(graphicsContext);
        int startX = 450;
        int startY = 0;
        Weapon weapon = new Weapon(graphicsContext, startX, startY);
        map.putOnTheGround(weapon);
        LinkedList<Bullet> bullets = new LinkedList<>();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                map.draw();

                if (keys.contains(KeyCode.LEFT)) {
                    weapon.setX(weapon.getX() - 1);
                    map.putOnTheGround(weapon);
                }

                if (keys.contains(KeyCode.RIGHT)) {
                    weapon.setX(weapon.getX() + 1);
                    map.putOnTheGround(weapon);
                }

                if (keys.contains(KeyCode.UP)) {
                    weapon.gunUp();
                }

                if (keys.contains(KeyCode.DOWN)) {
                    weapon.gunDown();
                }

                weapon.draw();

                if (keys.contains(KeyCode.ENTER)) {
                    bullets.add(weapon.shot());
                    keys.remove(KeyCode.ENTER);
                }

                cleanBullets(map, bullets);
                bullets.forEach(Bullet::draw);

                if (keys.contains(KeyCode.ESCAPE)) {
                    primaryStage.close();
                }
            }
        }.start();
    }

    /**
     * Cleans bullet
     * @param map map your world
     * @param bullets list of bullets
     */
    private void cleanBullets(Map map, LinkedList<Bullet> bullets) {
        LinkedList<Bullet> toRemove = new LinkedList<>();

        for (Bullet bullet : bullets) {
            if (map.isOnTheGround(bullet)) {
                bullet.destroy();
            }

            if (bullet.isDestroyed()) {
                toRemove.add(bullet);
            }
        }

        for (Bullet bullet : toRemove) {
            bullets.remove(bullet);
        }
    }
}