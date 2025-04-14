package ru.nsu.zhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 主应用程序类
 * Main application class
 */
public class SnakeGame extends Application {

    public static void main(String[] args) {
        launch(); // 启动JavaFX应用 / Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Snake Game");
        Image image = new Image(getClass().getResourceAsStream("/icon.jpg"));
        primaryStage.getIcons().add(image); // 设置窗口图标 / Set window icon
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // 禁止调整窗口大小 / Disable window resizing
        primaryStage.show(); // 显示窗口 / Show window
    }
}
