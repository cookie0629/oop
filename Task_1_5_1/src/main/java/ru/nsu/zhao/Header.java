package ru.nsu.zhao;

/**
 * 表示 Markdown 标题.
 */
public class Header implements Element {
    private final int level;
    private final String text;

    public Header(int level, String text) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("标题级别必须在 1 到 6 之间");
        }
        this.level = level;
        this.text = text;
    }

    @Override
    public String toMarkdown() {
        return "#".repeat(level) + " " + text;
    }
}
