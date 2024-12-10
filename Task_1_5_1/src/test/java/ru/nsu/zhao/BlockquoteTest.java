package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BlockquoteTest {
    @Test
    void testBlockquoteSerialization() {
        Blockquote blockquote = new Blockquote(List.of(
                new Text("This is a quote"),
                new Blockquote(List.of(new Text("Nested quote")))
        ));
        String expected = """
                > This is a quote
                > > Nested quote
                """;
        assertEquals(expected.trim(), blockquote.toMarkdown());
    }
}
