package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockquoteTest {

    @Test
    void testToMarkdown() {
        Blockquote blockquote = new Blockquote("This is a blockquote.\nSecond line.");
        String expected = "> This is a blockquote.\n> Second line.";
        assertEquals(expected, blockquote.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Blockquote b1 = new Blockquote("Content");
        Blockquote b2 = new Blockquote("Content");
        Blockquote b3 = new Blockquote("Different");

        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
    }
}
