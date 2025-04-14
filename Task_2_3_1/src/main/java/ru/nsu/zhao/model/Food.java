package ru.nsu.zhao.model;

import javafx.geometry.Point2D;
import java.util.Random;

/**
 * 食物模型类
 * Food model class
 */
public class Food {
    private Point2D position;
    private int type; // 食物类型 (可用于扩展) food type (for extension)

    /**
     * 构造函数
     * Constructor
     * @param position 位置 position
     */
    public Food(Point2D position) {
        this.position = position;
        this.type = 0; // 普通食物 normal food
    }

    /**
     * 随机生成食物位置
     * Randomly generate food position
     * @param gridWidth 网格宽度 grid width
     * @param gridHeight 网格高度 grid height
     * @param snakeBody 蛇身体 snake body
     * @param obstacles 障碍物 obstacles
     * @return 新食物 new food
     */
    public static Food generateRandomFood(int gridWidth, int gridHeight,
                                          LinkedList<Point2D> snakeBody,
                                          LinkedList<Obstacle> obstacles) {
        Random random = new Random();
        Point2D newPosition;
        boolean validPosition;

        do {
            validPosition = true;
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            newPosition = new Point2D(x, y);

            // 检查是否与蛇身体重叠
            // Check overlap with snake body
            for (Point2D segment : snakeBody) {
                if (segment.equals(newPosition)) {
                    validPosition = false;
                    break;
                }
            }

            // 检查是否与障碍物重叠
            // Check overlap with obstacles
            if (validPosition) {
                for (Obstacle obstacle : obstacles) {
                    if (obstacle.getPosition().equals(newPosition)) {
                        validPosition = false;
                        break;
                    }
                }
            }
        } while (!validPosition);

        return new Food(newPosition);
    }

    // Getters and setters
    public Point2D getPosition() { return position; }
    public int getType() { return type; }
}
