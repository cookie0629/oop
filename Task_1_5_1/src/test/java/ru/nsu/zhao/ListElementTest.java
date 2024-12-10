package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ListElementTest 单元测试
 */
class ListElementTest {

    @Test
    void testUnorderedListToMarkdown() {
        ListElement unorderedList = new ListElement(false)
                .addItem("Item 1")
                .addItem("Item 2");

        String expectedMarkdown = "- Item 1\n- Item 2\n";
        assertEquals(expectedMarkdown, unorderedList.toMarkdown());
    }

    @Test
    void testOrderedListToMarkdown() {
        ListElement orderedList = new ListElement(true)
                .addItem("First item")
                .addItem("Second item");

        String expectedMarkdown = "1. First item\n2. Second item\n";
        assertEquals(expectedMarkdown, orderedList.toMarkdown());
    }
}
