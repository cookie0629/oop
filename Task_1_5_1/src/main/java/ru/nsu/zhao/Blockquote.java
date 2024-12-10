package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 引用块。
 */
public class Blockquote extends Element {
    private final String content;

    /**
     * 构造函数。
     *
     * @param content 引用内容
     */
    public Blockquote(String content) {
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return "> " + content.replaceAll("\n", "\n> ");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Blockquote that)) return false;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
