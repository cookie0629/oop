package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListElementTest {

    @Test
    void testUnorderedListToMarkdown() {
        ListElement list = new ListElement(List.of("Item 1", "Item 2", "Item 3"), false);
        String expected = "- Item 1\n- Item 2\n- Item 3";
        assertEquals(expected, list.toMarkdown());
    }

    @Test
    void testOrderedListToMarkdown() {
        ListElement list = new ListElement(List.of("First", "Second", "Third"), true);
        String expected = "1. First\n2. Second\n3. Third";
        assertEquals(expected, list.toMarkdown());
    }

    @Test
    void testEmptyListThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ListElement(List.of(), false));
    }

    @Test
    void testEqualsAndHashCode() {
        ListElement list1 = new ListElement(List.of("Item 1", "Item 2"), false);
        ListElement list2 = new ListElement(List.of("Item 1", "Item 2"), false);
        ListElement list3 = new ListElement(List.of("Item 1", "Item 2"), true);
        ListElement list4 = new ListElement(List.of("Item 1", "Different"), false);

        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
        assertNotEquals(list1, list4);
        assertEquals(list1.hashCode(), list2.hashCode());
        assertNotEquals(list1.hashCode(), list3.hashCode());
        assertNotEquals(list1.hashCode(), list4.hashCode());
    }
}
