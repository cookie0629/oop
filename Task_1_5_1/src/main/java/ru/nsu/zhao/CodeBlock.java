package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示 Markdown 代码块。
 */
public class CodeBlock extends Element {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CodeBlock)) return false;
        CodeBlock that = (CodeBlock) obj;
        return Objects.equals(language, that.language) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, code);
    }
}
