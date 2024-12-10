package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 标题。
 */
public class Header implements Element {
    private final int level;
    private final String content;

    /**
     * 构造函数。
     *
     * @param level   标题级别（1-6）
     * @param content 标题内容
     */
    public Header(int level, String content) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("标题级别必须在 1 到 6 之间");
        }
        this.level = level;
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return "#".repeat(level) + " " + content;
    }

}
