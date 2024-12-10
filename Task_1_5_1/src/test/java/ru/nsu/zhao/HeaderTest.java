package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {
    @Test
    void testHeaderSerialization() {
        for (int level = 1; level <= 6; level++) {
            Header header = new Header(level, "Header Level " + level);
            assertEquals("#".repeat(level) + " Header Level " + level, header.toMarkdown());
        }
    }

    @Test
    void testHeaderInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> new Header(0, "Invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Header(7, "Invalid"));
    }
}
