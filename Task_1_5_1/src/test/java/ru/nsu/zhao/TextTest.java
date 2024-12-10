package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextTest {
    @Test
    void testPlainTextSerialization() {
        Text text = new Text("Plain text");
        assertEquals("Plain text", text.toMarkdown());
    }

    @Test
    void testBoldTextSerialization() {
        Text.Bold boldText = new Text.Bold("Bold text");
        assertEquals("**Bold text**", boldText.toMarkdown());
    }

    @Test
    void testItalicTextSerialization() {
        Text.Italic italicText = new Text.Italic("Italic text");
        assertEquals("*Italic text*", italicText.toMarkdown());
    }

    @Test
    void testStrikethroughTextSerialization() {
        Text.Strikethrough strikethroughText = new Text.Strikethrough("Strikethrough text");
        assertEquals("~~Strikethrough text~~", strikethroughText.toMarkdown());
    }

    @Test
    void testInlineCodeSerialization() {
        Text.InlineCode inlineCode = new Text.InlineCode("Code");
        assertEquals("`Code`", inlineCode.toMarkdown());
    }
}
