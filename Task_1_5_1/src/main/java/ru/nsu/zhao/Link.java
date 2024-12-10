package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 超链接或图片。
 */
public class Link extends Element {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Link)) return false;
        Link link = (Link) obj;
        return Objects.equals(text, link.text) && Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, url);
    }
}


