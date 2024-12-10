package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void testTableSerialization() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Name", "Score")
                .addRow("Alice", "95")
                .addRow("Bob", "85")
                .build();
        String expected = """
                | Name  | Score |
                |:---   |   ---:|
                | Alice |    95 |
                | Bob   |    85 |
                """;
        assertEquals(expected.trim(), table.toMarkdown());
    }

    @Test
    void testTableWithoutAlignment() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT)
                .addRow("A", "B")
                .addRow("1", "2")
                .build();
        String expected = """
                | A | B |
                |:--|:--|
                | 1 | 2 |
                """;
        assertEquals(expected.trim(), table.toMarkdown());
    }
}
