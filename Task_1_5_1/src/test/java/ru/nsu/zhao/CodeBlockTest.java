package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CodeBlockTest 单元测试
 */
class CodeBlockTest {

    @Test
    void testCodeBlockToMarkdown() {
        CodeBlock codeBlock = new CodeBlock("java", "public static void main(String[] args) { System.out.println(\"Hello, world!\"); }");
        String expectedMarkdown = "```java\npublic static void main(String[] args) { System.out.println(\"Hello, world!\"); }\n```";
        assertEquals(expectedMarkdown, codeBlock.toMarkdown());
    }

    @Test
    void testCodeBlockEquality() {
        CodeBlock codeBlock1 = new CodeBlock("java", "public static void main(String[] args) { System.out.println(\"Hello, world!\"); }");
        CodeBlock codeBlock2 = new CodeBlock("java", "public static void main(String[] args) { System.out.println(\"Hello, world!\"); }");
        CodeBlock codeBlock3 = new CodeBlock("python", "print('Hello, world!')");

        assertEquals(codeBlock1, codeBlock2);
        assertNotEquals(codeBlock1, codeBlock3);
    }
}
