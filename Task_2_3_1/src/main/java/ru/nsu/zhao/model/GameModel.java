package ru.nsu.zhao.model;

import java.util.LinkedList;

/**
 * 游戏模型类
 * Game model class
 */
public class GameModel {
    private Snake snake;                // 玩家蛇 player snake
    private LinkedList<Food> foods;    // 食物列表 food list
    private LinkedList<Obstacle> obstacles; // 障碍物 obstacles
    private int gridWidth;             // 网格宽度 grid width
    private int gridHeight;            // 网格高度 grid height
    private int foodCount;             // 食物数量 food count
    private int targetLength;          // 目标长度 target length
    private boolean gameOver;          // 游戏是否结束 game over flag
    private boolean gameWon;           // 游戏是否胜利 game won flag
    private int score;                 // 当前分数 current score

    /**
     * 构造函数
     * Constructor
     * @param gridWidth 网格宽度 grid width
     * @param gridHeight 网格高度 grid height
     * @param foodCount 食物数量 food count
     * @param targetLength 目标长度 target length
     */
    public GameModel(int gridWidth, int gridHeight, int foodCount, int targetLength) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.foodCount = foodCount;
        this.targetLength = targetLength;
        this.score = 0;

        // 初始化蛇
        // Initialize snake
        Point2D startPos = new Point2D(gridWidth / 2, gridHeight / 2);
        this.snake = new Snake(startPos);

        // 初始化食物
        // Initialize food
        this.foods = new LinkedList<>();
        for (int i = 0; i < foodCount; i++) {
            addNewFood();
        }

        // 初始化障碍物
        // Initialize obstacles
        this.obstacles = new LinkedList<>();
        generateObstacles();
    }

    /**
     * 更新游戏状态
     * Update game state
     */
    public void update() {
        if (gameOver || gameWon) return;

        // 移动蛇
        // Move snake
        snake.move();

        // 检查碰撞
        // Check collision
        if (snake.checkCollision(gridWidth, gridHeight, obstacles)) {
            gameOver = true;
            return;
        }

        // 检查是否吃到食物
        // Check if food is eaten
        for (int i = 0; i < foods.size(); i++) {
            if (snake.checkFoodEaten(foods.get(i)))) {
                foods.remove(i);
                addNewFood();
                score += 10;
                break;
            }
        }

        // 检查胜利条件
        // Check win condition
        if (snake.getBody().size() >= targetLength) {
            gameWon = true;
        }
    }

    /**
     * 添加新食物
     * Add new food
     */
    private void addNewFood() {
        Food newFood = Food.generateRandomFood(gridWidth, gridHeight, snake.getBody(), obstacles);
        foods.add(newFood);
    }

    /**
     * 生成障碍物
     * Generate obstacles
     */
    private void generateObstacles() {
        // 简单实现：在四周生成围墙
        // Simple implementation: walls around the border
        for (int x = 0; x < gridWidth; x++) {
            obstacles.add(new Obstacle(new Point2D(x, 0)));
            obstacles.add(new Obstacle(new Point2D(x, gridHeight - 1)));
        }
        for (int y = 1; y < gridHeight - 1; y++) {
            obstacles.add(new Obstacle(new Point2D(0, y)));
            obstacles.add(new Obstacle(new Point2D(gridWidth - 1, y)));
        }
    }

    /**
     * 改变蛇的方向
     * Change snake direction
     * @param direction 新方向 new direction
     */
    public void changeSnakeDirection(Snake.Direction direction) {
        snake.changeDirection(direction);
    }

    // Getters
    public Snake getSnake() { return snake; }
    public LinkedList<Food> getFoods() { return foods; }
    public LinkedList<Obstacle> getObstacles() { return obstacles; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }
    public int getScore() { return score; }
    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
}
