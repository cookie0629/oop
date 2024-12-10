package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表示 Markdown 表格。
 */
public class Table extends Element {
    public static final String ALIGN_LEFT = ":--";
    public static final String ALIGN_CENTER = ":-:";
    public static final String ALIGN_RIGHT = "--:";

    private final List<String[]> rows;
    private final String[] alignments;

    private Table(List<String[]> rows, String[] alignments) {
        this.rows = rows;
        this.alignments = alignments;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        // Header Row
        markdown.append("| ");
        for (String header : rows.get(0)) {
            markdown.append(header).append(" | ");
        }
        markdown.append("\n| ");
        for (String align : alignments) {
            markdown.append(align).append(" | ");
        }
        markdown.append("\n");
        // Data Rows
        for (int i = 1; i < rows.size(); i++) {
            markdown.append("| ");
            for (String cell : rows.get(i)) {
                markdown.append(cell).append(" | ");
            }
            markdown.append("\n");
        }
        return markdown.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Table)) return false;
        Table table = (Table) obj;
        return Objects.equals(rows, table.rows) && Objects.equals(alignments, table.alignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, alignments);
    }

    /**
     * 表示用于构建 Table 的 Builder。
     */
    public static class Builder {
        private final List<String[]> rows = new ArrayList<>();
        private String[] alignments;
        private int rowLimit = Integer.MAX_VALUE;

        public Builder withAlignments(String... alignments) {
            this.alignments = alignments;
            return this;
        }

        public Builder withRowLimit(int rowLimit) {
            this.rowLimit = rowLimit;
            return this;
        }

        public Builder addRow(String... cells) {
            if (rows.size() >= rowLimit) {
                throw new IllegalStateException("超出表格行数限制");
            }
            rows.add(cells);
            return this;
        }

        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("表格至少需要一行");
            }
            if (alignments == null || alignments.length != rows.get(0).length) {
                throw new IllegalStateException("对齐方式数量应与列数相同");
            }
            return new Table(rows, alignments);
        }
    }
}
