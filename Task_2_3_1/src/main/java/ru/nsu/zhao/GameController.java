package ru.nsu.zhao;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.math.NumberUtils;
import java.awt.*;
import java.util.Objects;

/**
 * 游戏控制器，处理游戏逻辑和用户交互
 * Game controller handling game logic and user interaction
 */
public class GameController {
    @FXML private VBox startScreen;       // 开始界面 / Start screen
    @FXML private TextField scoreInput;   // 分数输入框 / Score input field
    @FXML private Canvas gameCanvas;      // 游戏画布 / Game canvas
    @FXML private Label scoreLabel;       // 分数标签 / Score label
    @FXML private Button restartButton;   // 重新开始按钮 / Restart button

    private final Image appleImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/apple.png"))); // 食物图片 / Food image

    private static final int CELL_SIZE = 20; // 单元格大小 / Cell size in pixels
    private static final int WIDTH = 30;    // 游戏区域宽度(单元格数) / Game field width in cells
    private static final int HEIGHT = 20;    // 游戏区域高度(单元格数) / Game field height in cells
    private static final long MOVE_INTERVAL = 150_000_000L; // 移动间隔(纳秒) / Movement interval in nanoseconds

    private GameField gameField;  // 游戏区域 / Game field
    private Snake snake;         // 蛇 / Snake
    private boolean running;      // 游戏运行状态 / Game running state
    private Thread gameLoop;      // 游戏循环线程 / Game loop thread
    private boolean gameOverDisplayed = false; // 游戏结束显示状态 / Game over display state
    private int scoreToWin;       // 胜利所需分数 / Score needed to win

    /**
     * 开始游戏
     * Start the game
     */
    @FXML
    private void startGame() {
        try {
            scoreToWin = NumberUtils.toInt(scoreInput.getText(), 0);
            if (scoreToWin <= 0 || scoreToWin >= 600) {
                throw new NumberFormatException();
            }

            startScreen.setVisible(false);

            gameCanvas.setVisible(true);
            scoreLabel.setVisible(true);
            restartButton.setVisible(true);

            restartGame();
        } catch (NumberFormatException e) {
            scoreInput.setText("输入无效"); // 输入无效 / Invalid input
        }
    }

    /**
     * 初始化方法
     * Initialize method
     */
    @FXML
    public void initialize() {
        showStartScreen();
        restartButton.setOnAction(_ -> showStartScreen());
        restartButton.setFocusTraversable(false);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * 重新开始游戏
     * Restart the game
     */
    private void restartGame() {
        gameField = new GameField(WIDTH, HEIGHT);
        snake = new Snake(WIDTH / 2, HEIGHT / 2, gameField);
        running = true;

        if (gameLoop != null) {
            gameLoopStop();
        }

        gameLoop = new Thread(() -> {
            long lastUpdate = 0;

            while (running) {
                long now = System.nanoTime();
                if (now - lastUpdate > MOVE_INTERVAL) {
                    int computedDelta = (int) ((now - lastUpdate) / MOVE_INTERVAL);

                    if (lastUpdate == 0) {
                        computedDelta = 1;
                        Platform.runLater(this::fullDraw);
                    }

                    lastUpdate = now;

                    final int delta = computedDelta;
                    Platform.runLater(() -> updateGame(delta));
                }
            }
        });
        gameLoop.setDaemon(true);
        gameLoop.start();
    }

    /**
     * 更新游戏状态
     * Update game state
     * @param delta 时间增量 / Time delta
     */
    private void updateGame(int delta) {
        if (!running) return;

        for (int i = 0; i < delta; i++) {
            CellType collision = snake.checkCollision(WIDTH, HEIGHT);
            if (collision == CellType.WALL || collision == CellType.SNAKE) {
                running = false;
                gameLoopStop();
                drawGameOver();
                return;
            }

            Point clearTail = null;
            Point newFood = null;

            if (collision == CellType.FOOD) {
                snake.grow();
                if (snake.getBody().size() >= scoreToWin) {
                    running = false;
                    gameLoopStop();
                    drawVictory();
                    return;
                }
                newFood = gameField.generateFood(snake.getBody(), snake.getNextHeadPosition());
            } else {
                clearTail = snake.move();
            }

            draw(clearTail, newFood);
        }
    }

    /**
     * 完整绘制游戏画面
     * Full draw of the game
     */
    private void fullDraw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (var food : gameField.getFoodPositions()) {
            gc.drawImage(appleImage, food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.GREEN);
        for (var segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + snake.getBody().size());
    }

    /**
     * 增量绘制游戏画面
     * Incremental draw of the game
     * @param clearTail 需要清除的尾部 / Tail to clear
     * @param newFood 新生成的食物 / New food generated
     */
    private void draw(Point clearTail, Point newFood) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        if(clearTail != null) {
            gc.setFill(Color.ALICEBLUE);
            gc.fillRect(clearTail.x * CELL_SIZE, clearTail.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        if (newFood != null) {
            gc.drawImage(appleImage, newFood.x * CELL_SIZE, newFood.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(snake.getBody().getFirst().x * CELL_SIZE, snake.getBody().getFirst().y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        scoreLabel.setText("Score: " + snake.getBody().size());
    }

    /**
     * 绘制游戏结束画面
     * Draw game over screen
     */
    private void drawGameOver() {
        gameOverDisplayed = true;
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setFont(new Font(30));
        gc.fillText("Game Over", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }

    /**
     * 处理键盘输入
     * Handle keyboard input
     * @param event 键盘事件 / Key event
     */
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> snake.changeDirection(0, -1);
            case DOWN, S -> snake.changeDirection(0, 1);
            case LEFT, A -> snake.changeDirection(-1, 0);
            case RIGHT, D -> snake.changeDirection(1, 0);
        }
    }


    /**
     * 显示开始界面
     * Show start screen
     */
    private void showStartScreen() {
        Platform.runLater(() -> {
            startScreen.setVisible(true);
            scoreInput.clear();
            gameCanvas.setVisible(false);
            scoreLabel.setVisible(false);
            restartButton.setVisible(false);
        });
    }

    /**
     * 停止游戏循环
     * Stop game loop
     */
    private void gameLoopStop() {
        gameLoop.interrupt();
        gameLoop = null;
    }

    /**
     * 绘制胜利画面
     * Draw victory screen
     */
    private void drawVictory() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        gc.setFont(new Font(30));
        gc.fillText("Victory", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }

    public boolean isGameOverDisplayed() {
        return gameOverDisplayed;
    }
}
