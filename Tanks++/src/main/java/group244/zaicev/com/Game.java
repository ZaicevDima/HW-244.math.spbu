package group244.zaicev.com;

import com.google.common.collect.ImmutableList;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for two sockets client and server
 */
public abstract class Game implements AutoCloseable {
    volatile PrintWriter out;
    volatile BufferedReader in;
    private final ExecutorService sender = Executors.newSingleThreadExecutor();

    /**
     * sends command
     * @param commands your List from commands
     */
    public void send(List<KeyCode> commands) {
        if (commands.isEmpty()) {
            return;
        }
        ImmutableList<KeyCode> commandsToSend = ImmutableList.copyOf(commands);
        sender.submit(() -> {
            init();
            for (KeyCode command : commandsToSend) {
                out.println(command);
            }
            out.flush();
        });
    }

    /**
     * returns the received command
     */
    public KeyCode receive() {
        initUnderLock();
        return in.lines()
                .limit(1)
                .findAny()
                .map(KeyCode::valueOf)
                .orElse(null);
    }

    /**
     * Method for correct initUnderLock()
     */
    private void init() {
        if (in == null) {
            synchronized (this) {
                initUnderLock();
            }
        }
    }

    /** initialization **/
    protected abstract void initUnderLock();

    @Override
    public void close() {
        try {
            out.close();
            in.close();
            sender.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}