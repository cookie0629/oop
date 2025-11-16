package ru.nsu.zhao;

/**
 * 表示 Markdown 链接.
 */
public class Link implements Element {
    private final String text;
    private final String url;

    public Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public String toMarkdown() {
        return "[" + text + "](" + url + ")";
    }
}
