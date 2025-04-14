package ru.nsu.zhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主应用程序类
 * Main application class
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 加载菜单界面
        // Load menu view
        Parent root = FXMLLoader.load(getClass().getResource("/ru/nsu/zhao/view/MenuView.fxml"));

        // 设置舞台
        // Set up the stage
        primaryStage.setTitle("贪吃蛇游戏 - Snake Game");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
