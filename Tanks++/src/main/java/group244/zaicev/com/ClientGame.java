package group244.zaicev.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Class for work with client socket
 */
public class ClientGame extends Game {

    private Socket client;
    private String ipAddress;

    /**
     * Constructor
     */
    ClientGame(String ipAddress) {
        this.ipAddress = Objects.requireNonNull(ipAddress);
    }


    @Override
    protected void initUnderLock() {
        InetAddress inetAddress = null;
        if (client == null) {
            try {
                client = new Socket(inetAddress = InetAddress.getByName(ipAddress), ServerGame.PORT);
                out = new PrintWriter(client.getOutputStream());
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (UnknownHostException e) {
                assert ipAddress != null;
                assert inetAddress != null;
                System.err.println("Don't know about host: " + inetAddress.getHostAddress());
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to: " + inetAddress.getHostAddress());
                System.exit(1);
            }
        }
    }
}
