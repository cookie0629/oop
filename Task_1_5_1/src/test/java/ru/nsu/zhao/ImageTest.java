package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {
    @Test
    void testImageSerialization() {
        Image image = new Image("Alt text", "https://example.com/image.png");
        assertEquals("![Alt text](https://example.com/image.png)", image.toMarkdown());
    }
}
