package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TextTest 单元测试
 */
class TextTest {

    @Test
    void testBoldToMarkdown() {
        Text.Bold boldText = new Text.Bold("Bold Text");
        String expectedMarkdown = "**Bold Text**";
        assertEquals(expectedMarkdown, boldText.toMarkdown());
    }

    @Test
    void testItalicToMarkdown() {
        Text.Italic italicText = new Text.Italic("Italic Text");
        String expectedMarkdown = "*Italic Text*";
        assertEquals(expectedMarkdown, italicText.toMarkdown());
    }

    @Test
    void testStrikethroughToMarkdown() {
        Text.Strikethrough strikethroughText = new Text.Strikethrough("Strikethrough Text");
        String expectedMarkdown = "~~Strikethrough Text~~";
        assertEquals(expectedMarkdown, strikethroughText.toMarkdown());
    }

    @Test
    void testInlineCodeToMarkdown() {
        Text.InlineCode inlineCodeText = new Text.InlineCode("Inline Code");
        String expectedMarkdown = "`Inline Code`";
        assertEquals(expectedMarkdown, inlineCodeText.toMarkdown());
    }

    @Test
    void testTextEquality() {
        Text.Bold bold1 = new Text.Bold("Bold Text");
        Text.Bold bold2 = new Text.Bold("Bold Text");
        Text.Italic italic = new Text.Italic("Italic Text");
        Text.Strikethrough strikethrough1 = new Text.Strikethrough("Strikethrough Text");
        Text.Strikethrough strikethrough2 = new Text.Strikethrough("Strikethrough Text");

        assertEquals(bold1, bold2);
        assertNotEquals(bold1, italic);
        assertNotEquals(strikethrough1, bold2);
        assertEquals(strikethrough1, strikethrough2);
    }
}
