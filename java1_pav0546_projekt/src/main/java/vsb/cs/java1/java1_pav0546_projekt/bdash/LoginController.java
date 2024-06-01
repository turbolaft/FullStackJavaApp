package vsb.cs.java1.java1_pav0546_projekt.bdash;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import vsb.cs.java1.java1_pav0546_projekt.game_api.NetworkRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button submit;
    @FXML
    private Button signup;

    public LoginController() {
    }

    @FXML
    public void initialize() {
        submit.setOnAction(event -> {
            String user = username.getText();
            String pass = password.getText();

            Pair response = NetworkRequest.makeRequest("http://localhost:9090/auth/signin", "POST",
                    "application/json", "application/json", null,
                    "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}");

            if (response == null) {
                System.out.println("Error while making request");
            } else if (response.getKey().equals(HttpURLConnection.HTTP_OK)) {
                System.out.println(response.getValue());

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                    GameController controller = loader.getController();
                    controller.start(response.getValue().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (response.getKey().equals(HttpURLConnection.HTTP_BAD_REQUEST)) {
                System.out.println("Invalid username or password");
            }
        });

        signup.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
