package ru.nsu.zhao;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础类 Element 表示 Markdown 的通用元素。
 */
abstract class Element {
    /**
     * 序列化为 Markdown 格式字符串。
     *
     * @return Markdown 格式字符串
     */
    public abstract String toMarkdown();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}

/**
 * Text 类表示带有格式的文本元素。
 */
class Text extends Element {
    private final String content;

    // 构造函数
    public Text(String content) {
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Text text = (Text) obj;
        return Objects.equals(content, text.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    /**
     * Bold 类表示加粗文本。
     */
    public static class Bold extends Text {
        public Bold(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "**" + super.toMarkdown() + "**";
        }
    }

    /**
     * Italic 类表示斜体文本。
     */
    public static class Italic extends Text {
        public Italic(String content) {
            super(content);
        }

        @Override
        public String toMarkdown() {
            return "*" + super.toMarkdown() + "*";
        }
    }
}

/**
 * Table 类表示 Markdown 表格。
 */
class Table extends Element {
    private final List<String[]> rows; // 表格的行
    private final String[] alignments; // 每列的对齐方式

    public static final String ALIGN_LEFT = ":--";
    public static final String ALIGN_RIGHT = "--:";
    public static final String ALIGN_CENTER = ":--:";

    private Table(Builder builder) {
        this.rows = new ArrayList<>(builder.rows);
        this.alignments = builder.alignments;
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        // 添加表头
        if (!rows.isEmpty()) {
            sb.append(formatRow(rows.get(0))).append("\n");
            sb.append(formatAlignment()).append("\n");
        }
        // 添加表格内容
        for (int i = 1; i < rows.size(); i++) {
            sb.append(formatRow(rows.get(i))).append("\n");
        }
        return sb.toString().trim();
    }

    private String formatRow(String[] row) {
        return "| " + String.join(" | ", row) + " |";
    }

    private String formatAlignment() {
        return "| " + String.join(" | ", alignments) + " |";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Table table = (Table) obj;
        return rows.equals(table.rows) && Arrays.equals(alignments, table.alignments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows);
        result = 31 * result + Arrays.hashCode(alignments);
        return result;
    }

    /**
     * Builder 模式用于构建 Table。
     */
    public static class Builder {
        private final List<String[]> rows = new ArrayList<>();
        private String[] alignments;
        private int rowLimit = Integer.MAX_VALUE;

        public Builder withAlignments(String... alignments) {
            this.alignments = alignments;
            return this;
        }

        public Builder withRowLimit(int limit) {
            this.rowLimit = limit;
            return this;
        }

        public Builder addRow(Object... values) {
            if (rows.size() >= rowLimit) {
                throw new IllegalStateException("Row limit exceeded.");
            }
            rows.add(Arrays.stream(values)
                    .map(value -> value instanceof Element ? ((Element) value).toMarkdown() : value.toString())
                    .toArray(String[]::new));
            return this;
        }

        public Table build() {
            if (alignments == null || alignments.length == 0) {
                throw new IllegalStateException("Alignments must be specified.");
            }
            return new Table(this);
        }
    }
}

/**
 * 示例代码
 */
class Main {
    public static void main(String[] args) {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random");

        for (int i = 1; i <= 5; i++) {
            final var value = (int) (Math.random() * 10);
            if (value > 5) {
                tableBuilder.addRow(i, new Text.Bold(String.valueOf(value)));
            } else {
                tableBuilder.addRow(i, (int) (Math.random() * 10));
            }
        }

        System.out.println(tableBuilder.build());
    }
}
