package ru.nsu.zhao;

import java.awt.Point;
import java.util.LinkedList;

/**
 * 蛇类，管理蛇的移动和生长
 * Snake class managing movement and growth
 */
public class Snake {
    private LinkedList<Point> body; // 蛇身 / Snake body
    private int dx, dy;            // 移动方向 / Movement direction
    private final GameField gameField; // 游戏区域引用 / Reference to game field

    /**
     * 构造函数
     * Constructor
     * @param startX 起始X坐标 / Starting X coordinate
     * @param startY 起始Y坐标 / Starting Y coordinate
     * @param gameField 游戏区域 / Game field
     */
    public Snake(int startX, int startY, GameField gameField) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
        dx = 1; // 初始向右移动 / Initially move right
        dy = 0;
        this.gameField = gameField;
    }

    /**
     * 移动蛇
     * Move the snake
     * @return 被移除的尾部位置 / Removed tail position
     */
    public Point move() {
        Point newHead = getNextHeadPosition();
        body.addFirst(newHead);
        return body.removeLast();
    }

    /**
     * 蛇生长
     * Grow the snake
     */
    public void grow() {
        body.addFirst(getNextHeadPosition());
    }

    /**
     * 改变移动方向
     * Change movement direction
     * @param newDx 新的X方向 / New X direction
     * @param newDy 新的Y方向 / New Y direction
     */
    public void changeDirection(int newDx, int newDy) {
        // 防止180度转弯 / Prevent 180-degree turns
        if (dx != -newDx || dy != -newDy) {
            dx = newDx;
            dy = newDy;
        }
    }

    /**
     * 获取下一个头部位置
     * Get next head position
     * @return 下一个头部位置 / Next head position
     */
    public Point getNextHeadPosition() {
        Point head = body.getFirst();
        return new Point(head.x + dx, head.y + dy);
    }

    /**
     * 检查碰撞
     * Check for collisions
     * @param width 区域宽度 / Field width
     * @param height 区域高度 / Field height
     * @return 碰撞类型 / Collision type
     */
    public CellType checkCollision(int width, int height) {
        Point head = body.getFirst();
        // 检查墙壁碰撞 / Check wall collision
        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            return CellType.WALL;
        }

        // 检查自身碰撞 / Check self collision
        if (body.stream().skip(1).anyMatch(segment -> segment.equals(head))){
            return CellType.SNAKE;
        }
        // 检查食物碰撞 / Check food collision
        if (gameField.isFood(head)){
            return CellType.FOOD;
        }
        return CellType.EMPTY;
    }

    /**
     * 获取蛇身
     * Get snake body
     * @return 蛇身列表 / Snake body list
     */
    public LinkedList<Point> getBody() {
        return body;
    }
}
