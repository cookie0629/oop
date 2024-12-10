package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示 Markdown 表格.
 */
public class Table implements Element {
    public static final String ALIGN_LEFT = ":---";
    public static final String ALIGN_CENTER = ":---:";
    public static final String ALIGN_RIGHT = "---:";

    private final List<String[]> rows;
    private final String[] alignments;

    private Table(Builder builder) {
        this.rows = builder.rows;
        this.alignments = builder.alignments;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        // Header
        markdown.append("|");
        for (String header : rows.get(0)) {
            markdown.append(" ").append(header).append(" |");
        }
        markdown.append("\n|");
        for (String alignment : alignments) {
            markdown.append(alignment).append("|");
        }
        // Rows
        for (int i = 1; i < rows.size(); i++) {
            markdown.append("\n|");
            for (String cell : rows.get(i)) {
                markdown.append(" ").append(cell).append(" |");
            }
        }
        return markdown.toString();
    }

    public static class Builder {
        private final List<String[]> rows = new ArrayList<>();
        private String[] alignments;

        public Builder withAlignments(String... alignments) {
            this.alignments = alignments;
            return this;
        }

        public Builder addRow(String... cells) {
            rows.add(cells);
            return this;
        }

        public Table build() {
            if (rows.isEmpty() || alignments == null) {
                throw new IllegalStateException("表格必须至少有一行和对齐方式");
            }
            return new Table(this);
        }
    }
}
