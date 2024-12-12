package ru.nsu.zhao;

/**
 * 表示 Markdown 图片.
 */
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
