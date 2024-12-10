package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Blockquote 类。
 */
class BlockquoteTest {

    @Test
    void testToMarkdownSingleLine() {
        Blockquote blockquote = new Blockquote("This is a blockquote.");
        String expectedMarkdown = "> This is a blockquote.";
        assertEquals(expectedMarkdown, blockquote.toMarkdown(), "单行引用的 Markdown 序列化失败");
    }

    @Test
    void testToMarkdownMultiLine() {
        Blockquote blockquote = new Blockquote("This is a blockquote.\nIt has multiple lines.");
        String expectedMarkdown = "> This is a blockquote.\n> It has multiple lines.";
        assertEquals(expectedMarkdown, blockquote.toMarkdown(), "多行引用的 Markdown 序列化失败");
    }

    @Test
    void testEqualsSameContent() {
        Blockquote blockquote1 = new Blockquote("Same content");
        Blockquote blockquote2 = new Blockquote("Same content");
        assertEquals(blockquote1, blockquote2, "具有相同内容的 Blockquote 对象不相等");
    }

    @Test
    void testNotEqualsDifferentContent() {
        Blockquote blockquote1 = new Blockquote("Content A");
        Blockquote blockquote2 = new Blockquote("Content B");
        assertNotEquals(blockquote1, blockquote2, "具有不同内容的 Blockquote 对象相等");
    }

    @Test
    void testHashCodeSameContent() {
        Blockquote blockquote1 = new Blockquote("Same content");
        Blockquote blockquote2 = new Blockquote("Same content");
        assertEquals(blockquote1.hashCode(), blockquote2.hashCode(), "相同内容的 Blockquote 对象的哈希值不同");
    }

    @Test
    void testHashCodeDifferentContent() {
        Blockquote blockquote1 = new Blockquote("Content A");
        Blockquote blockquote2 = new Blockquote("Content B");
        assertNotEquals(blockquote1.hashCode(), blockquote2.hashCode(), "不同内容的 Blockquote 对象的哈希值相同");
    }
}
