package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    @Test
    void testToMarkdown() {
        Header header = new Header("Header Content", 3);
        String expected = "### Header Content";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> new Header("Invalid", 0));
        assertThrows(IllegalArgumentException.class, () -> new Header("Invalid", 7));
    }

    @Test
    void testEqualsAndHashCode() {
        Header h1 = new Header("Content", 2);
        Header h2 = new Header("Content", 2);
        Header h3 = new Header("Different", 2);
        Header h4 = new Header("Content", 3);

        assertEquals(h1, h2);
        assertNotEquals(h1, h3);
        assertNotEquals(h1, h4);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1.hashCode(), h3.hashCode());
        assertNotEquals(h1.hashCode(), h4.hashCode());
    }
}
