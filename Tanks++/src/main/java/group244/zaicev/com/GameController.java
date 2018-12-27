package group244.zaicev.com;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static group244.zaicev.com.ExitWindows.*;
import static java.util.stream.Collectors.toList;

/** Game Controller */
public class GameController extends Application {
    private static Button serverButton;
    private static Button clientButton;
    private static Button connect;
    private static TextField ipAddress;
    private static GridPane connectScreen = new GridPane();
    private Scene scene = new Scene(connectScreen, 500, 300);
    private static final int BASIC_WIDTH = 1000;
    private static final int BASIC_HEIGHT = 700;
    private static final Set<KeyCode> SUPPORTED_KEYS = EnumSet.of(
            KeyCode.LEFT,
            KeyCode.RIGHT,
            KeyCode.ENTER,
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.ESCAPE
    );

    private static int typeProjectile = 0;

    private volatile Game game;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final int START_X = 250;
    private static final int START_Y = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connecting");
        primaryStage.setResizable(false);

        initialize();

        serverButtonAction();

        clientButton.setOnAction(event -> {
            clientButton.setDisable(true);
            serverButton.setDisable(true);
            connect.setDisable(false);
            ipAddress.setDisable(false);
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        connectButtonAction();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /** Creates list of KeyCode */
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
     * Returns a list of received values
     */
    private List<KeyCode> counterpartyKeyboardSettings(Game game) {
        List<KeyCode> input = new CopyOnWriteArrayList<>();

        executor.submit(() -> {
            while (true) {
                KeyCode key = game.receive();
                if (key != null) {
                    input.add(key);
                }

            }
        });

        return input;
    }

    /**
     * Method for realise game mechanics
     * @param primaryStage yor stage
     * @param graphicsContext your graphics context
     * @param keys your code keys
     * @param counterpartyKeys code keys your counterparty
     * @param weapon1 your weapon
     * @param weapon2 weapon your counterparty
     */
    private void gameMechanics(Stage primaryStage, GraphicsContext graphicsContext,
                               List<KeyCode> keys, List<KeyCode> counterpartyKeys, Weapon weapon1, Weapon weapon2) {
        Map map = new Map(graphicsContext);
        map.putOnTheGround(weapon1);
        map.putOnTheGround(weapon2);
        List<Projectile> projectiles = new LinkedList<>();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                map.draw();
                List<KeyCode> appliedKeys = updateWeapon(map, weapon1, keys, primaryStage, projectiles);
                updateWeapon(map, weapon2, counterpartyKeys, primaryStage, projectiles);
                for (Projectile projectile : projectiles) {
                    if (map.isOnTheGround(projectile)) {

                        if (weapon1.weaponDestroy(projectile)) {
                            primaryStage.close();
                            exitWindow("lose", primaryStage.getTitle());
                        }

                        if (weapon2.weaponDestroy(projectile)) {
                            primaryStage.close();
                            exitWindow("win", primaryStage.getTitle());
                        }
                    }
                }

                cleanBullets(map, projectiles);
                projectiles.forEach(Projectile::draw);

                interactWithCounterparty(appliedKeys);
            }
        }.start();
    }

    /**
     * Apply processCommand to keys
     */
    private void interactWithCounterparty(List<KeyCode> keys) {
        List<KeyCode> codes = keys.stream().filter(SUPPORTED_KEYS::contains).collect(toList());
        processCommand(codes);
        codes.clear();
    }

    /**
     * Sends commands
     */
    private void processCommand(List<KeyCode> commands) {
        game.send(commands);
    }

    /**
     * Method for update weapon
     * @return list from KeyCode commands
     */
    private List<KeyCode> updateWeapon(Map map, Weapon weapon, List<KeyCode> keys, Stage primaryStage, List<Projectile> projectiles) {
        List<KeyCode> appliedKeys = new ArrayList<>();

        if (keys.contains(KeyCode.LEFT)) {
            weapon.moveLeft();
            map.putOnTheGround(weapon);
            appliedKeys.add(KeyCode.LEFT);
            keys.remove(KeyCode.LEFT);
        }

        if (keys.contains(KeyCode.RIGHT)) {
            weapon.moveRight();
            map.putOnTheGround(weapon);
            appliedKeys.add(KeyCode.RIGHT);
            keys.remove(KeyCode.RIGHT);
        }

        if (keys.contains(KeyCode.UP)) {
            weapon.gunUp();
            appliedKeys.add(KeyCode.UP);
            keys.remove(KeyCode.UP);
        }

        if (keys.contains(KeyCode.DOWN)) {
            weapon.gunDown();
            appliedKeys.add(KeyCode.DOWN);
            keys.remove(KeyCode.DOWN);
        }

        weapon.draw();

        if (keys.contains(KeyCode.ENTER)) {
            projectiles.add(weapon.shot(typeProjectile));
            appliedKeys.add(KeyCode.ENTER);
            keys.remove(KeyCode.ENTER);
        }

        if (keys.contains(KeyCode.ESCAPE)) {
            appliedKeys.add(KeyCode.ESCAPE);
            keys.remove(KeyCode.ESCAPE);
            primaryStage.close();
            escapeeWindow();
        }

        if (keys.contains(KeyCode.TAB)) {
            typeProjectile = typeProjectile == 0 ? 1 : 0;
            appliedKeys.add(KeyCode.TAB);
            keys.remove(KeyCode.TAB);
        }
        return appliedKeys;
    }

    /**
     * Cleans bullets, which is on the ground
     */
    private void cleanBullets(Map map, List<Projectile> projectiles) {
        LinkedList<Projectile> toRemove = new LinkedList<>();

        for (Projectile projectile : projectiles) {
            if (map.isOnTheGround(projectile)) {
                projectile.destroy();
            }

            if (projectile.isDestroyed()) {
                toRemove.add(projectile);
            }
        }

        for (Projectile projectile : toRemove) {
            projectiles.remove(projectile);
        }
    }

    /**
     * Create start dialog window
     */
    private static void initialize() {
        connectScreen.setPadding(new Insets(25, 25, 25, 25));
        connectScreen.setHgap(25);
        connectScreen.setVgap(15);

        serverButton = new Button();
        serverButton.setText("server button");
        serverButton.setPrefSize(230, connectScreen.getHeight() / 5);

        clientButton = new Button();
        clientButton.setText("client button");
        clientButton.setPrefSize(230, connectScreen.getHeight() / 5);

        Label massege = new Label("IP address: ");

        ipAddress = new TextField();
        ipAddress.setDisable(true);

        connect = new Button("connect");
        connect.setPrefSize(150, 40);
        connect.setDisable(true);

        connectScreen.add(serverButton, 0, 0);
        connectScreen.add(clientButton, 1, 0);
        connectScreen.add(massege, 0, 1);

        connectScreen.add(connect, 1, 2);
        GridPane.setHalignment(connect, HPos.CENTER);
        GridPane.setValignment(connect, VPos.CENTER);

        connectScreen.add(ipAddress, 0, 2);
    }

    /**
     * Actions on the server buttons
     */
    private void serverButtonAction() {
        serverButton.setOnAction(event -> {
            clientButton.setDisable(true);

            InetAddress thisIp = null;

            try {
                thisIp = InetAddress.getLocalHost();
            } catch (UnknownHostException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Can't get IP", ButtonType.CLOSE);
                alert.setHeaderText(null);
                alert.showAndWait();
                System.exit(1);
            }

            Label ip = new Label("Your IP address: " + thisIp.getHostAddress());
            connectScreen.add(ip, 0, 4);
            serverButton.setDisable(true);

            game = new ServerGame();

            gameForServer();
        });
    }

    /**
     * Realise game for server
     */
    private void gameForServer() {
        Stage serverWindow = new Stage();
        serverWindow.setFullScreen(true);
        serverWindow.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        serverWindow.setTitle("Server Window");

        Group root = new Group();
        Scene scene = new Scene(root);
        serverWindow.setScene(scene);
        List<KeyCode> keys = keyboardSettings(scene);
        List<KeyCode> counterpartyKeys = counterpartyKeyboardSettings(game);

        int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight() + 40;
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.scale((double) screenWidth / BASIC_WIDTH, (double) screenHeight / BASIC_HEIGHT);

        Weapon weapon = new Weapon(graphicsContext, START_X, START_Y);
        Weapon weapon2 = new Weapon(graphicsContext, BASIC_WIDTH - START_X, START_Y);

        gameMechanics(serverWindow, graphicsContext, keys, counterpartyKeys, weapon, weapon2);
        serverWindow.show();
    }

    /**
     * Actions on the connect button
     */
    private void connectButtonAction() {
        connect.setOnAction(event -> {
            String ipAddressText = ipAddress.getText();
            try {
                if (!InetAddress.getLocalHost().getHostAddress().equals(ipAddressText)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong IP", ButtonType.CLOSE);
                    alert.setHeaderText(null);
                    alert.setTitle("error");
                    alert.showAndWait();
                    System.exit(1);
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Couldn't get I/O for the connection to: " + ipAddressText, ButtonType.CLOSE);
                alert.setHeaderText(null);
                alert.setTitle("error");
                alert.showAndWait();
                System.exit(1);
            }
            connect.setDisable(true);
            ipAddress.setDisable(true);

            game = new ClientGame(ipAddressText);

            gameForClient();
        });
    }

    /**
     * Realise game for client
     */
    private void gameForClient() {
        Stage clientWindow = new Stage();
        clientWindow.setFullScreen(true);
        clientWindow.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        clientWindow.setTitle("Client Window");

        Group root = new Group();
        Scene scene = new Scene(root);
        clientWindow.setScene(scene);
        List<KeyCode> keys = keyboardSettings(scene);
        List<KeyCode> counterpartyKeys = counterpartyKeyboardSettings(game);

        int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight() + 40;
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.scale((double) screenWidth / BASIC_WIDTH, (double) screenHeight / BASIC_HEIGHT);

        Weapon weapon = new Weapon(graphicsContext, START_X, START_Y);
        Weapon weapon2 = new Weapon(graphicsContext, BASIC_WIDTH - START_X, START_Y);

        gameMechanics(clientWindow, graphicsContext, keys, counterpartyKeys, weapon2, weapon);
        clientWindow.show();
    }

    @Override
    public void stop() throws Exception {
        game.close();
        executor.shutdownNow();
        super.stop();
    }
}