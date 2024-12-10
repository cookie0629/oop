package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinkTest 单元测试
 */
class LinkTest {

    @Test
    void testLinkToMarkdown() {
        Link link = new Link("Google", "https://www.google.com");
        String expectedMarkdown = "[Google](https://www.google.com)";
        assertEquals(expectedMarkdown, link.toMarkdown());
    }

    @Test
    void testLinkEquality() {
        Link link1 = new Link("Google", "https://www.google.com");
        Link link2 = new Link("Google", "https://www.google.com");
        Link link3 = new Link("Google Search", "https://www.google.com/search");

        assertEquals(link1, link2);
        assertNotEquals(link1, link3);
    }
}
