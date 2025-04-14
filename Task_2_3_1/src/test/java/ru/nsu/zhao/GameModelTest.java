package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 游戏模型测试
 * Game model test
 */
class GameModelTest {
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new GameModel(20, 20, 3, 10);
    }

    @Test
    void testInitialState() {
        // 测试初始状态
        // Test initial state
        assertNotNull(gameModel.getSnake());
        assertEquals(3, gameModel.getFoods().size());
        assertFalse(gameModel.isGameOver());
        assertFalse(gameModel.isGameWon());
    }

    @Test
    void testFoodEaten() {
        // 测试吃到食物
        // Test food eaten
        Point2D headPos = gameModel.getSnake().getBody().getFirst();
        Point2D foodPos = gameModel.getFoods().get(0).getPosition();

        // 移动蛇到食物位置
        // Move snake to food position
        while (!headPos.equals(foodPos)) {
            if (headPos.getX() < foodPos.getX()) {
                gameModel.changeSnakeDirection(Snake.Direction.RIGHT);
            } else if (headPos.getX() > foodPos.getX()) {
                gameModel.changeSnakeDirection(Snake.Direction.LEFT);
            } else if (headPos.getY() < foodPos.getY()) {
                gameModel.changeSnakeDirection(Snake.Direction.DOWN);
            } else {
                gameModel.changeSnakeDirection(Snake.Direction.UP);
            }
            gameModel.update();
            headPos = gameModel.getSnake().getBody().getFirst();
        }

        // 检查是否吃到食物
        // Check if food is eaten
        assertEquals(3, gameModel.getFoods().size()); // 新食物应该生成 new food should be generated
        assertEquals(2, gameModel.getSnake().getBody().size()); // 蛇应该增长 snake should grow
    }

    @Test
    void testWallCollision() {
        // 测试墙壁碰撞
        // Test wall collision
        gameModel.changeSnakeDirection(Snake.Direction.UP);
        for (int i = 0; i < 20; i++) {
            gameModel.update();
        }

        assertTrue(gameModel.isGameOver());
    }
}
