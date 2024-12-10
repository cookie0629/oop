package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    @Test
    void testToMarkdown() {
        Header header1 = new Header(1, "Header 1");
        assertEquals("# Header 1", header1.toMarkdown());

        Header header6 = new Header(6, "Header 6");
        assertEquals("###### Header 6", header6.toMarkdown());
    }

    @Test
    void testEquality() {
        Header header1a = new Header(1, "Header 1");
        Header header1b = new Header(1, "Header 1");
        Header header2 = new Header(2, "Header 2");

        assertEquals(header1a, header1b);
        assertNotEquals(header1a, header2);
    }

    @Test
    void testInvalidHeaderLevel() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Header(0, "Invalid Header Level"));
        assertEquals("标题级别必须在 1 到 6 之间", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> new Header(7, "Invalid Header Level"));
        assertEquals("标题级别必须在 1 到 6 之间", exception.getMessage());
    }
}
