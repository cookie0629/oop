package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import ru.nsu.zhao.markdown.Text;

import static org.junit.jupiter.api.Assertions.*;

class TextTests {

    @Test
    void testBoldSerialization() {
        Text.Bold boldText = new Text.Bold("bold");
        assertEquals("**bold**", boldText.toMarkdown());
    }

    @Test
    void testItalicSerialization() {
        Text.Italic italicText = new Text.Italic("italic");
        assertEquals("*italic*", italicText.toMarkdown());
    }

    @Test
    void testStrikethroughSerialization() {
        Text.Strikethrough strikethroughText = new Text.Strikethrough("strikethrough");
        assertEquals("~~strikethrough~~", strikethroughText.toMarkdown());
    }

    @Test
    void testInlineCodeSerialization() {
        Text.InlineCode inlineCode = new Text.InlineCode("code");
        assertEquals("`code`", inlineCode.toMarkdown());
    }

    @Test
    void testTextEquality() {
        Text.Bold text1 = new Text.Bold("bold");
        Text.Bold text2 = new Text.Bold("bold");
        assertEquals(text1, text2);
        assertEquals(text1.hashCode(), text2.hashCode());

        Text.Bold differentText = new Text.Bold("different");
        assertNotEquals(text1, differentText);
    }
}
