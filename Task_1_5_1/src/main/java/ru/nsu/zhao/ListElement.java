package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表示 Markdown 列表，支持有序和无序列表。
 */
public class ListElement implements Element {
    private final List<String> items;
    private final boolean ordered;

    public ListElement(boolean ordered) {
        this.ordered = ordered;
        this.items = new ArrayList<>();
    }

    /**
     * 添加一个列表项。
     *
     * @param item 列表项内容
     * @return 当前 ListElement 实例（链式调用）
     */
    public ListElement addItem(String item) {
        items.add(item);
        return this;
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
        return markdown.toString();
    }

}
