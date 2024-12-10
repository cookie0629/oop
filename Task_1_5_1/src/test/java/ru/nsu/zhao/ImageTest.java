package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ImageTest 单元测试
 */
class ImageTest {

    @Test
    void testImageToMarkdown() {
        Image image = new Image("An image", "https://example.com/image.png");
        String expectedMarkdown = "![An image](https://example.com/image.png)";
        assertEquals(expectedMarkdown, image.toMarkdown());
    }

    @Test
    void testImageEquality() {
        Image image1 = new Image("An image", "https://example.com/image.png");
        Image image2 = new Image("An image", "https://example.com/image.png");
        Image image3 = new Image("Another image", "https://example.com/another-image.png");

        assertEquals(image1, image2);
        assertNotEquals(image1, image3);
    }
}
