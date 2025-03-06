package ru.nsu.zhao;

import java.util.List;

/**
 * 配置数据类 / Configuration Data Class
 * 用于加载JSON配置文件 / Used for loading JSON configuration
 */
public class Config {
    public int storageCapacity;  // 仓库容量 / Storage capacity
    public List<Integer> bakers;  // 面包师配置列表 / List of baker configurations
    public List<Integer> couriers;  // 快递员配置列表 / List of courier configurations
}
