package ru.nsu.zhao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * 菜单控制器
 * Menu controller
 */
public class MenuController {
    private Stage primaryStage;

    /**
     * 设置主舞台
     * Set primary stage
     * @param primaryStage 主舞台 primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * 开始游戏
     * Start game
     */
    @FXML
    private void startGame() {
        try {
            // 加载游戏界面
            // Load game view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/zhao/view/GameView.fxml"));
            Parent root = loader.load();

            // 获取游戏控制器并初始化
            // Get game controller and initialize
            GameController gameController = loader.getController();
            gameController.setPrimaryStage(primaryStage);
            gameController.initializeGame();

            // 设置场景
            // Set scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开设置
     * Open settings
     */
    @FXML
    private void openSettings() {
        // 简单实现，显示一个对话框
        // Simple implementation, show a dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("游戏设置");
        alert.setHeaderText("游戏设置");
        alert.setContentText("这里可以添加游戏设置选项");
        alert.showAndWait();
    }

    /**
     * 退出游戏
     * Exit game
     */
    @FXML
    private void exitGame() {
        // 确认对话框
        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退出游戏");
        alert.setHeaderText("确定要退出游戏吗？");
        alert.setContentText("您的游戏进度将会丢失");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            primaryStage.close();
        }
    }
}
