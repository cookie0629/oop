package ru.nsu.zhao.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.zhao.model.*;

import java.io.IOException;

/**
 * 游戏控制器
 * Game controller
 */
public class GameController {
    private static final int GRID_SIZE = 20;    // 网格大小 grid size
    private static final int GRID_WIDTH = 30;   // 网格宽度 grid width
    private static final int GRID_HEIGHT = 20;  // 网格高度 grid height
    private static final int FOOD_COUNT = 3;    // 食物数量 food count
    private static final int TARGET_LENGTH = 20; // 目标长度 target length

    private Stage primaryStage;
    private GameModel gameModel;
    private AnimationTimer gameLoop;
    private boolean isPaused = false;

    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;
    @FXML private Label lengthLabel;

    /**
     * 设置主舞台
     * Set primary stage
     * @param primaryStage 主舞台 primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // 添加键盘监听
        // Add keyboard listener
        this.primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    /**
     * 初始化游戏
     * Initialize game
     */
    public void initializeGame() {
        // 初始化游戏模型
        // Initialize game model
        gameModel = new GameModel(GRID_WIDTH, GRID_HEIGHT, FOOD_COUNT, TARGET_LENGTH);

        // 初始化游戏循环
        // Initialize game loop
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // 100ms 更新一次 update every 100ms
                    if (!isPaused) {
                        updateGame();
                        renderGame();
                    }
                    lastUpdate = now;
                }
            }
        };

        // 开始游戏循环
        // Start game loop
        gameLoop.start();
    }

    /**
     * 更新游戏状态
     * Update game state
     */
    private void updateGame() {
        gameModel.update();

        // 检查游戏结束或胜利
        // Check game over or win
        if (gameModel.isGameOver()) {
            gameLoop.stop();
            showGameOver();
        } else if (gameModel.isGameWon()) {
            gameLoop.stop();
            showGameWon();
        }

        // 更新UI
        // Update UI
        scoreLabel.setText(String.valueOf(gameModel.getScore()));
        lengthLabel.setText("长度: " + gameModel.getSnake().getBody().size());
    }

    /**
     * 渲染游戏
     * Render game
     */
    private void renderGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // 绘制网格背景
        // Draw grid background
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // 绘制障碍物
        // Draw obstacles
        gc.setFill(Color.DARKGRAY);
        for (Obstacle obstacle : gameModel.getObstacles()) {
            Point2D pos = obstacle.getPosition();
            gc.fillRect(pos.getX() * GRID_SIZE, pos.getY() * GRID_SIZE, GRID_SIZE, GRID_SIZE);
        }

        // 绘制食物
        // Draw food
        gc.setFill(Color.RED);
        for (Food food : gameModel.getFoods()) {
            Point2D pos = food.getPosition();
            gc.fillOval(pos.getX() * GRID_SIZE, pos.getY() * GRID_SIZE, GRID_SIZE, GRID_SIZE);
        }

        // 绘制蛇
        // Draw snake
        LinkedList<Point2D> snakeBody = gameModel.getSnake().getBody();
        for (int i = 0; i < snakeBody.size(); i++) {
            Point2D segment = snakeBody.get(i);

            // 头部和身体不同颜色
            // Different color for head and body
            if (i == 0) {
                gc.setFill(Color.DARKGREEN); // 头部 head
            } else {
                gc.setFill(Color.GREEN);     // 身体 body
            }

            gc.fillRect(segment.getX() * GRID_SIZE, segment.getY() * GRID_SIZE, GRID_SIZE, GRID_SIZE);

            // 绘制边框
            // Draw border
            gc.setStroke(Color.BLACK);
            gc.strokeRect(segment.getX() * GRID_SIZE, segment.getY() * GRID_SIZE, GRID_SIZE, GRID_SIZE);
        }
    }

    /**
     * 处理键盘输入
     * Handle keyboard input
     * @param event 键盘事件 key event
     */
    private void handleKeyPress(KeyEvent event) {
        if (isPaused) return;

        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                gameModel.changeSnakeDirection(Snake.Direction.UP);
                break;
            case DOWN:
                gameModel.changeSnakeDirection(Snake.Direction.DOWN);
                break;
            case LEFT:
                gameModel.changeSnakeDirection(Snake.Direction.LEFT);
                break;
            case RIGHT:
                gameModel.changeSnakeDirection(Snake.Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    /**
     * 暂停游戏
     * Pause game
     */
    @FXML
    private void pauseGame() {
        isPaused = !isPaused;
        Button pauseButton = (Button) gameCanvas.getScene().lookup("#pauseButton");
        if (pauseButton != null) {
            pauseButton.setText(isPaused ? "继续" : "暂停");
        }
    }

    /**
     * 返回菜单
     * Back to menu
     */
    @FXML
    private void backToMenu() {
        gameLoop.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/zhao/view/MenuView.fxml"));
            Parent root = loader.load();

            MenuController menuController = loader.getController();
            menuController.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示游戏结束
     * Show game over
     */
    private void showGameOver() {
        StackPane root = (StackPane) gameCanvas.getScene().getRoot();

        // 创建游戏结束弹窗
        // Create game over popup
        Label gameOverLabel = new Label("游戏结束! 得分: " + gameModel.getScore());
        gameOverLabel.getStyleClass().add("popup-label");

        StackPane popup = new StackPane(gameOverLabel);
        popup.getStyleClass().add("popup");

        root.getChildren().add(popup);
    }

    /**
     * 显示游戏胜利
     * Show game win
     */
    private void showGameWon() {
        StackPane root = (StackPane) gameCanvas.getScene().getRoot();

        // 创建游戏胜利弹窗
        // Create game win popup
        Label gameWinLabel = new Label("恭喜胜利! 得分: " + gameModel.getScore());
        gameWinLabel.getStyleClass().add("popup-label");

        StackPane popup = new StackPane(gameWinLabel);
        popup.getStyleClass().add("popup");

        root.getChildren().add(popup);
    }
}
