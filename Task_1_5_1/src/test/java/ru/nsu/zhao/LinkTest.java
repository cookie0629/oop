package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkTest {
    @Test
    void testLinkSerialization() {
        Link link = new Link("Google", "https://www.google.com");
        assertEquals("[Google](https://www.google.com)", link.toMarkdown());
    }
}
