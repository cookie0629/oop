package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 的文本及其格式。
 */
public class Text implements Element {
    private final String content;

    private Text(String content) {
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return content;
    }

    // 工厂方法定义不同的文本样式
    public static class Bold extends Text {
        public Bold(String content) {
            super("**" + content + "**");
        }
    }

    public static class Italic extends Text {
        public Italic(String content) {
            super("*" + content + "*");
        }
    }

    public static class Strikethrough extends Text {
        public Strikethrough(String content) {
            super("~~" + content + "~~");
        }
    }

    public static class InlineCode extends Text {
        public InlineCode(String content) {
            super("`" + content + "`");
        }
    }
}
