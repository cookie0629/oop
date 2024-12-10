package ru.nsu.zhao;

import java.util.List;

/**
 * 表示 Markdown 引用.
 */
public class Blockquote implements Element {
    private final List<Element> elements;

    public Blockquote(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (Element element : elements) {
            markdown.append("> ").append(element.toMarkdown().replace("\n", "\n> ")).append("\n");
        }
        return markdown.toString().trim();
    }
}
