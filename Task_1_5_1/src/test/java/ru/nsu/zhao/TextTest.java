package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Text 类及其子类的功能.
 */
public class TextTest {
    @Test
    void testBoldSerialization() {
        Element boldText = new Text.Bold("This is bold text");
        String expected = "**This is bold text**";
        assertEquals(expected, boldText.toMarkdown());
    }

    @Test
    void testItalicSerialization() {
        Element italicText = new Text.Italic("This is italic text");
        String expected = "*This is italic text*";
        assertEquals(expected, italicText.toMarkdown());
    }

    @Test
    void testStrikethroughSerialization() {
        Element strikethroughText = new Text.Strikethrough("This is strikethrough text");
        String expected = "~~This is strikethrough text~~";
        assertEquals(expected, strikethroughText.toMarkdown());
    }

    @Test
    void testInlineCodeSerialization() {
        Element inlineCode = new Text.InlineCode("System.out.println('Hello');");
        String expected = "`System.out.println('Hello');`";
        assertEquals(expected, inlineCode.toMarkdown());
    }

    @Test
    void testPlainTextSerialization() {
        Element plainText = new Text("This is a plain text.");
        String expected = "This is a plain text.";
        assertEquals(expected, plainText.toMarkdown());
    }
}
