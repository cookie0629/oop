package ru.nsu.zhao;

import java.awt.Point;
import java.util.*;

/**
 * 游戏区域类，管理食物生成和位置
 * Game field class managing food generation and positions
 */
public class GameField {
    private final int width, height;  // 区域宽高 / Field dimensions
    private final Set<Point> food;   // 食物位置集合 / Food positions
    private final Random random;     // 随机数生成器 / Random number generator

    /**
     * 构造函数
     * Constructor
     * @param width 区域宽度 / Field width
     * @param height 区域高度 / Field height
     */
    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.food = new HashSet<>();
        this.random = new Random();
        generateFood(new LinkedList<>(), null); // 初始生成食物 / Initial food generation
    }

    /**
     * 生成食物
     * Generate food
     * @param occupied 被占用的位置 / Occupied positions
     * @param eatenFood 被吃掉的食物 / Eaten food
     * @return 新生成的食物 / Newly generated food
     */
    public Point generateFood(LinkedList<Point> occupied, Point eatenFood) {
        food.remove(eatenFood);

        List<Point> availableCells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point point = new Point(x, y);

                if (!occupied.contains(point) && point != eatenFood && !food.contains(point)) {
                    availableCells.add(point);
                }
            }
        }

        Point newFood = null;

        // 保持场上有3个食物 / Maintain 3 food items on the field
        while (food.size() < 3 && !availableCells.isEmpty()) {
            int randomIndex = random.nextInt(availableCells.size());
            newFood = availableCells.get(randomIndex);

            food.add(newFood);

            availableCells.remove(randomIndex);
        }

        return newFood;
    }

    /**
     * 检查指定位置是否有食物
     * Check if there is food at the specified position
     * @param p 位置 / Position
     * @return 是否有食物 / Whether there is food
     */
    public boolean isFood(Point p) {
        return food.remove(p); // 移除并返回是否成功 / Remove and return whether successful
    }

    /**
     * 获取所有食物位置
     * Get all food positions
     * @return 食物位置集合 / Set of food positions
     */
    public Set<Point> getFoodPositions() {
        return food;
    }
}
