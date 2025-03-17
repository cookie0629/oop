package ru.nsu.zhao;

/**
 * 订单实体类 / Order Entity
 * 封装订单唯一标识 / Encapsulates order unique identifier
 */
public record Order(int id) {
    /**
     * 构造函数 / Constructor
     *
     * @param id 订单唯一编号 / Unique order ID
     */
    public Order {
    }

    /**
     * 获取订单编号 / Get order ID
     *
     * @return 订单编号 / Order number
     */
    @Override
    public int id() {
        return id;
    }
}
