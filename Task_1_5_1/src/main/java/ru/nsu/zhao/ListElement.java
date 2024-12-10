package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表示 Markdown 列表元素（有序或无序）。
 */
public class ListElement extends Element {
    private final List<String> items;
    private final boolean ordered;

    /**
     * 构造函数。
     *
     * @param items   列表项
     * @param ordered 是否为有序列表
     */
    public ListElement(List<String> items, boolean ordered) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("列表项不能为空");
        }
        this.items = new ArrayList<>(items);
        this.ordered = ordered;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (ordered) {
                markdown.append(i + 1).append(". ").append(items.get(i)).append("\n");
            } else {
                markdown.append("- ").append(items.get(i)).append("\n");
            }
        }
        return markdown.toString().trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ListElement)) return false;
        ListElement that = (ListElement) obj;
        return ordered == that.ordered && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, ordered);
    }
}
