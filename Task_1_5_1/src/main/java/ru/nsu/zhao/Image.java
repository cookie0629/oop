package ru.nsu.zhao;

import java.util.Objects;

public class Image implements Element {
    private final String altText;
    private final String url;

    public Image(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }

    @Override
    public String toMarkdown() {
        return "![" + altText + "](" + url + ")";
    }

}
