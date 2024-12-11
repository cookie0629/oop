package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 CodeBlock 类的功能.
 */
public class CodeBlockTest {
    @Test
    void testCodeBlockSerialization() {
        Element codeBlock = new CodeBlock("java", "System.out.println('Hello, world!');");
        String expected = "```java\nSystem.out.println('Hello, world!');\n```";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testCodeBlockWithoutLanguage() {
        Element codeBlock = new CodeBlock("", "System.out.println('Hello, world!');");
        String expected = "```\nSystem.out.println('Hello, world!');\n```";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testEmptyCodeBlock() {
        Element codeBlock = new CodeBlock("java", "");
        String expected = "```java\n\n```";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testCodeBlockWithSpecialCharacters() {
        String code = "System.out.println('Special characters: $@&*%');";
        Element codeBlock = new CodeBlock("java", code);
        String expected = "```java\nSystem.out.println('Special characters: $@&*%');\n```";
        assertEquals(expected, codeBlock.toMarkdown());
    }
}
