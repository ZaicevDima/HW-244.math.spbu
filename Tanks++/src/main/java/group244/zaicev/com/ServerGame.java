package group244.zaicev.com;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class for work with client socket
 */
public class ServerGame extends Game {

    public static final int PORT = 12345;
    private ServerSocket server;
    private Socket client;

    /**
     * constructor
     */
    ServerGame() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Error, the server is already running");
            alert.showAndWait();
            System.exit(1);
        }
    }

    @Override
    protected void initUnderLock() {
        if (client == null) {
            try {
                client = server.accept();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
