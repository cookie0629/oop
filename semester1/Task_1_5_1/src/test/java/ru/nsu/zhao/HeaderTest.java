package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Header 类的功能.
 */
public class HeaderTest {
    @Test
    void testHeaderLevel1Serialization() {
        Element header = new Header(1, "Header 1");
        String expected = "# Header 1";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testHeaderLevel2Serialization() {
        Element header = new Header(2, "Header 2");
        String expected = "## Header 2";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testHeaderLevel3Serialization() {
        Element header = new Header(3, "Header 3");
        String expected = "### Header 3";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testHeaderLevel4Serialization() {
        Element header = new Header(4, "Header 4");
        String expected = "#### Header 4";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testHeaderLevel5Serialization() {
        Element header = new Header(5, "Header 5");
        String expected = "##### Header 5";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testHeaderLevel6Serialization() {
        Element header = new Header(6, "Header 6");
        String expected = "###### Header 6";
        assertEquals(expected, header.toMarkdown());
    }

    @Test
    void testInvalidHeaderLevel() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Header(0, "Invalid Header");
        });
        assertEquals("标题级别必须在 1 到 6 之间", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Header(7, "Invalid Header");
        });
        assertEquals("标题级别必须在 1 到 6 之间", exception.getMessage());
    }
}
