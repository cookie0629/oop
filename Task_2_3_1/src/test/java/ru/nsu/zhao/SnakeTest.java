package ru.nsu.zhao;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 蛇模型测试
 * Snake model test
 */
class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(new Point2D(10, 10));
    }

    @Test
    void testInitialState() {
        // 测试初始状态
        // Test initial state
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point2D(10, 10), snake.getBody().getFirst());
        assertTrue(snake.isAlive());
    }

    @Test
    void testMove() {
        // 测试移动
        // Test movement
        snake.move();
        assertEquals(new Point2D(11, 10), snake.getBody().getFirst());

        snake.changeDirection(Snake.Direction.DOWN);
        snake.move();
        assertEquals(new Point2D(11, 11), snake.getBody().getFirst());
    }

    @Test
    void testGrow() {
        // 测试增长
        // Test growth
        snake.grow();
        snake.move();
        assertEquals(2, snake.getBody().size());
    }

    @Test
    void testChangeDirection() {
        // 测试方向改变
        // Test direction change
        snake.changeDirection(Snake.Direction.UP);
        snake.move();
        assertEquals(new Point2D(10, 9), snake.getBody().getFirst());

        // 测试不能180度转弯
        // Test no 180-degree turn
        snake.changeDirection(Snake.Direction.DOWN);
        snake.move();
        assertEquals(new Point2D(10, 8), snake.getBody().getFirst()); // 方向仍然是UP direction still UP
    }

    @Test
    void testCollision() {
        // 测试自身碰撞
        // Test self collision
        snake.grow();
        snake.move();
        snake.changeDirection(Snake.Direction.UP);
        snake.move();
        snake.changeDirection(Snake.Direction.LEFT);
        snake.move();
        snake.changeDirection(Snake.Direction.DOWN);
        snake.move(); // 碰撞自身 collide with self

        assertFalse(snake.isAlive());
    }
}
