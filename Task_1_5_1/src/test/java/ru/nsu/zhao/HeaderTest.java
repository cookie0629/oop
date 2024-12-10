package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * HeaderTest 单元测试
 */
class HeaderTest {

    @Test
    void testHeaderToMarkdown() {
        Header header1 = new Header(1, "Main Title");
        Header header6 = new Header(6, "Sub Title");

        assertEquals("# Main Title", header1.toMarkdown());
        assertEquals("###### Sub Title", header6.toMarkdown());
    }

    @Test
    void testHeaderEquality() {
        Header header1A = new Header(1, "Main Title");
        Header header1B = new Header(1, "Main Title");
        Header header2 = new Header(2, "Another Title");

        assertEquals(header1A, header1B);
        assertNotEquals(header1A, header2);
    }
}
