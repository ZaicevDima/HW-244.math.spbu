package group244.zaicev.com;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

class ExitWindows {
    /**
     * Method for realise exit window
     * @param state lose or win
     * @param name name player
     */
    static void exitWindow(String state, String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.CLOSE);
        alert.setTitle("exit");
        alert.setHeaderText(null);
        if (name.contains("Server")) {
            alert.setContentText("Server " + state);
        } else {
            alert.setContentText("Client " + state);
        }
        alert.setOnCloseRequest(event -> System.exit(0));
        alert.show();
    }

    /**
     * Exit window after pressed ESCAPE
     */
    static void escapeeWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.CLOSE);
        alert.setTitle("escape exit");
        alert.setHeaderText(null);
        alert.setContentText("Player pressed the escape");
        alert.setOnCloseRequest(event -> System.exit(0));
        alert.show();
    }
}
