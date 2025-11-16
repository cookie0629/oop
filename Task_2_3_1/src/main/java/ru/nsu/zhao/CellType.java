package ru.nsu.zhao;

/**
 * 枚举类型，表示单元格的类型
 * Enum representing cell types in the game
 */
public enum CellType {
    EMPTY,    // 空单元格 / Empty cell
    SNAKE,    // 蛇身单元格 / Snake body segment
    WALL,     // 墙壁单元格 / Wall (boundary)
    FOOD      // 食物单元格 / Food
}
