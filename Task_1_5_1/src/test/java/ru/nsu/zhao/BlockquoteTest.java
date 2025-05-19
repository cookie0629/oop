package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Blockquote 类的功能.
 */
public class BlockquoteTest {
    @Test
    void testBlockquoteSerialization() {
        Element text1 = new Text.Bold("This is bold text");
        Element text2 = new Text.Italic("This is italic text");
        Blockquote blockquote = new Blockquote(Arrays.asList(text1, text2));

        String expected = "> **This is bold text**\n> *This is italic text*";
        assertEquals(expected, blockquote.toMarkdown());
    }

    @Test
    void testEmptyBlockquote() {
        Blockquote blockquote = new Blockquote(Arrays.asList());
        String expected = "";
        assertEquals(expected, blockquote.toMarkdown());
    }

    @Test
    void testNestedBlockquote() {
        Element text = new Text.Bold("This is bold text");
        Blockquote innerBlockquote = new Blockquote(Arrays.asList(text));
        Blockquote outerBlockquote = new Blockquote(Arrays.asList(innerBlockquote));

        String expected = "> > **This is bold text**";
        assertEquals(expected, outerBlockquote.toMarkdown());
    }
}
