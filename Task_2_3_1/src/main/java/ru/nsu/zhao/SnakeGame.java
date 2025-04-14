package ru.nsu.zhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SnakeGame extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Snake Game");
        Image image = new Image(getClass().getResourceAsStream("/icon.jpg"));
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
