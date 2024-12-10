package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 超链接或图片。
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


