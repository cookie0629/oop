package ru.nsu.zhao;

/**
 * 表示 Markdown 中的文本元素.
 */
public class Text implements Element {
    private final String content;

    public Text(String content) {
        this.content = content;
    }

    /**
     * 加粗文本.
     */
    public static class Bold extends Text {
        public Bold(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "**" + super.content + "**";
        }
    }

    /**
     * 斜体文本.
     */
    public static class Italic extends Text {
        public Italic(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "*" + super.content + "*";
        }
    }

    /**
     * 删除线文本.
     */
    public static class Strikethrough extends Text {
        public Strikethrough(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "~~" + super.content + "~~";
        }
    }

    /**
     * 行内代码文本.
     */
    public static class InlineCode extends Text {
        public InlineCode(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "`" + super.content + "`";
        }
    }

    @Override
    public String toMarkdown() {
        return content;
    }
}
