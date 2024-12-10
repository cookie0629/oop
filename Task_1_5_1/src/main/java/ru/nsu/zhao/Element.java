package ru.nsu.zhao;

/**
 * 接口，表示所有 Markdown 元素.
 */
public interface Element {
    /**
     * 将元素序列化为 Markdown 格式的字符串.
     *
     * @return Markdown 格式的字符串
     */
    String toMarkdown();
}
