package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CodeBlockTest {
    @Test
    void testCodeBlockSerialization() {
        CodeBlock codeBlock = new CodeBlock("java", "System.out.println(\"Hello, world!\");");
        String expected = """
                ```java
                System.out.println("Hello, world!");
                ```
                """;
        assertEquals(expected.trim(), codeBlock.toMarkdown());
    }
}
