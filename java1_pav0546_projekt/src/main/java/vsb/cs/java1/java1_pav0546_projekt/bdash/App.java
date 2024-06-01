package vsb.cs.java1.java1_pav0546_projekt.bdash;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class App extends Application {

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image(getClass().getResource("/logo.jpg").toExternalForm()));
        primaryStage.setTitle("Boulder dash");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Pane pane = loader.load();
        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(this::exitProgram);
        primaryStage.show();
    }

    private void exitProgram(WindowEvent evt) {
        System.exit(0);
    }
}