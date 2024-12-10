package ru.nsu.zhao;

/**
 * 抽象基类，表示所有 Markdown 元素。
 */
public abstract class Element {
    /**
     * 将元素序列化为 Markdown 格式的字符串。
     *
     * @return Markdown 格式的字符串
     */
    public abstract String toMarkdown();

    /**
     * 检查两个元素是否相等。
     *
     * @param obj 要比较的对象
     * @return 如果相等返回 true，否则返回 false
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * 返回元素的哈希值。
     *
     * @return 元素的哈希值
     */
    @Override
    public abstract int hashCode();
}
