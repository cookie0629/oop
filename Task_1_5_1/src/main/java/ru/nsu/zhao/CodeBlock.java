package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 代码块。
 */
public class CodeBlock implements Element {
    private final String language;
    private final String code;

    public CodeBlock(String language, String code) {
        this.language = language;
        this.code = code;
    }

    @Override
    public String toMarkdown() {
        return "```" + language + "\n" + code + "\n```";
    }

}
