package pt.ipbeja.po2.connectfour.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author André Dâmaso
 * @number 15307
 */

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board();
        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
