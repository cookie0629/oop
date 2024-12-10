package ru.nsu.zhao;

import java.util.List;

/**
 * 表示 Markdown 列表.
 */
public class ListElement implements Element {
    private final boolean ordered;
    private final List<String> items;

    public ListElement(boolean ordered, List<String> items) {
        this.ordered = ordered;
        this.items = items;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String prefix = ordered ? (i + 1) + ". " : "- ";
            markdown.append(prefix).append(items.get(i)).append("\n");
        }
        return markdown.toString().trim();
    }
}
