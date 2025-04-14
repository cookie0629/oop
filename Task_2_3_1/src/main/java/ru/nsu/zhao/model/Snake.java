package ru.nsu.zhao.model;

import javafx.geometry.Point2D;
import java.util.LinkedList;

/**
 * 蛇模型类
 * Snake model class
 */
public class Snake {
    private LinkedList<Point2D> body; // 蛇身体 segments
    private Direction direction;     // 当前方向 current direction
    private boolean isAlive;         // 是否存活 is alive
    private int growthPending;       // 待增长段数 segments to grow

    // 方向枚举
    // Direction enum
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * 构造函数
     * Constructor
     * @param startPos 起始位置 starting position
     */
    public Snake(Point2D startPos) {
        this.body = new LinkedList<>();
        this.body.add(startPos);
        this.direction = Direction.RIGHT; // 默认向右 default to right
        this.isAlive = true;
        this.growthPending = 0;
    }

    /**
     * 移动蛇
     * Move the snake
     */
    public void move() {
        if (!isAlive) return;

        // 获取头部位置
        // Get head position
        Point2D head = body.getFirst();

        // 计算新头部位置
        // Calculate new head position
        Point2D newHead = calculateNewHead(head);

        // 添加新头部
        // Add new head
        body.addFirst(newHead);

        // 如果不需要增长，移除尾部
        // Remove tail if no growth pending
        if (growthPending <= 0) {
            body.removeLast();
        } else {
            growthPending--;
        }
    }

    /**
     * 计算新头部位置
     * Calculate new head position
     * @param head 当前头部 current head
     * @return 新头部位置 new head position
     */
    private Point2D calculateNewHead(Point2D head) {
        double x = head.getX();
        double y = head.getY();

        switch (direction) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }

        return new Point2D(x, y);
    }

    /**
     * 改变方向
     * Change direction
     * @param newDirection 新方向 new direction
     */
    public void changeDirection(Direction newDirection) {
        // 防止180度转弯
        // Prevent 180-degree turn
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection != Direction.UP) ||
                (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            this.direction = newDirection;
        }
    }

    /**
     * 检查碰撞
     * Check collision
     * @param gridWidth 网格宽度 grid width
     * @param gridHeight 网格高度 grid height
     * @param obstacles 障碍物 obstacles
     * @return 是否碰撞 whether collided
     */
    public boolean checkCollision(int gridWidth, int gridHeight, LinkedList<Obstacle> obstacles) {
        Point2D head = body.getFirst();

        // 检查墙壁碰撞
        // Check wall collision
        if (head.getX() < 0 || head.getX() >= gridWidth ||
                head.getY() < 0 || head.getY() >= gridHeight) {
            isAlive = false;
            return true;
        }

        // 检查自身碰撞
        // Check self collision
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                isAlive = false;
                return true;
            }
        }

        // 检查障碍物碰撞
        // Check obstacle collision
        for (Obstacle obstacle : obstacles) {
            if (head.equals(obstacle.getPosition())) {
                isAlive = false;
                return true;
            }
        }

        return false;
    }

    /**
     * 检查是否吃到食物
     * Check if food is eaten
     * @param food 食物 food
     * @return 是否吃到 whether food is eaten
     */
    public boolean checkFoodEaten(Food food) {
        if (body.getFirst().equals(food.getPosition())) {
            grow();
            return true;
        }
        return false;
    }

    /**
     * 蛇增长
     * Grow the snake
     */
    public void grow() {
        growthPending += 1;
    }

    // Getters and setters
    public LinkedList<Point2D> getBody() { return body; }
    public Direction getDirection() { return direction; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }
}
