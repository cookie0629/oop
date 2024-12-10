package ru.nsu.zhao;

import java.util.Objects;

public class Image extends Element {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Image)) return false;
        Image image = (Image) obj;
        return Objects.equals(altText, image.altText) && Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(altText, url);
    }
}
