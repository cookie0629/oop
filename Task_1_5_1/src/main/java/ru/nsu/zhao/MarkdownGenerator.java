package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

// 抽象的 Markdown 元素基类
abstract class Element {
    // 序列化为 Markdown 格式字符串的方法
    public abstract String toMarkdown();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.toMarkdown().equals(((Element) obj).toMarkdown());
    }
}

// 表示加粗文本的类
class Bold extends Element {
    private final String content;

    public Bold(String content) {
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return "**" + content + "**";
    }
}

// 表示普通文本的类
class Text extends Element {
    private final String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public String toMarkdown() {
        return content;
    }
}

// 表格类
class Table extends Element {
    // 对齐方式的常量
    public static final String ALIGN_LEFT = ":---";
    public static final String ALIGN_RIGHT = "---:";
    public static final String ALIGN_CENTER = ":---:";

    private final List<String[]> rows;
    private final List<String> alignments;

    private Table(List<String[]> rows, List<String> alignments) {
        this.rows = rows;
        this.alignments = alignments;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();

        // 添加表头
        if (!rows.isEmpty()) {
            markdown.append("| ");
            for (String header : rows.get(0)) {
                markdown.append(header).append(" | ");
            }
            markdown.append("\n| ");

            // 添加对齐方式
            for (String alignment : alignments) {
                markdown.append(alignment).append(" | ");
            }
            markdown.append("\n");
        }

        // 添加数据行
        for (int i = 1; i < rows.size(); i++) {
            markdown.append("| ");
            for (String cell : rows.get(i)) {
                markdown.append(cell).append(" | ");
            }
            markdown.append("\n");
        }

        return markdown.toString();
    }

    // Table 的 Builder 类
    public static class Builder {
        private final List<String[]> rows = new ArrayList<>();
        private final List<String> alignments = new ArrayList<>();
        private int rowLimit = Integer.MAX_VALUE; // 默认行数限制

        // 设置对齐方式
        public Builder withAlignments(String... alignments) {
            this.alignments.clear();
            for (String alignment : alignments) {
                this.alignments.add(alignment);
            }
            return this;
        }

        // 设置最大行数
        public Builder withRowLimit(int limit) {
            this.rowLimit = limit;
            return this;
        }

        // 添加一行数据
        public Builder addRow(Object... cells) {
            if (rows.size() >= rowLimit) {
                throw new IllegalStateException("Row limit exceeded.");
            }
            String[] row = new String[cells.length];
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] instanceof Element) {
                    row[i] = ((Element) cells[i]).toMarkdown();
                } else {
                    row[i] = cells[i].toString();
                }
            }
            rows.add(row);
            return this;
        }

        // 构建最终的 Table 对象
        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("Table must have at least one row (header).");
            }
            while (alignments.size() < rows.get(0).length) {
                alignments.add(ALIGN_LEFT); // 默认左对齐
            }
            return new Table(rows, alignments);
        }
    }
}

// 测试代码
public class MarkdownGenerator {
    public static void main(String[] args) {
        // 创建一个表格构建器并配置表格
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random"); // 添加表头

        // 随机生成数据并填充表格
        for (int i = 1; i <= 5; i++) {
            final var value = (int) (Math.random() * 10);
            if (value > 5) {
                tableBuilder.addRow(i, new Bold(String.valueOf(value))); // 加粗大于 5 的数字
            } else {
                tableBuilder.addRow(i, (int) (Math.random() * 10));
            }
        }

        // 打印生成的 Markdown 表格
        System.out.println(tableBuilder.build().toMarkdown());
    }
}
