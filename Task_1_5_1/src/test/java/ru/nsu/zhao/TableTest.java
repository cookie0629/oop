package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TableTest 单元测试
 */
class TableTest {

    @Test
    void testTableToMarkdown() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World").build();

        String expectedMarkdown = "| Index | Value |\n|:-----:|:-----:|\n| 1     | Hello |\n| 2     | World |\n";
        assertEquals(expectedMarkdown, table.toMarkdown());
    }

    @Test
    void testTableToMarkdownWithDifferentAlignments() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Right Aligned")
                .addRow("2", "Left Aligned").build();

        String expectedMarkdown = "| Index | Value              |\n|:-----:|:------------------:|\n| 1     | Right Aligned      |\n| 2     | Left Aligned       |\n";
        assertEquals(expectedMarkdown, table.toMarkdown());
    }

    @Test
    void testTableEquality() {
        Table table1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World")
                .build();

        Table table2 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World")
                .build();

        assertEquals(table1, table2);
    }

    @Test
    void testTableWithDifferentRows() {
        Table table1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World")
                .build();

        Table table2 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("1", "Hello")
                .addRow("3", "Universe")
                .build();

        assertNotEquals(table1, table2);
    }

    @Test
    void testTableWithDifferentAlignments() {
        Table table1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World")
                .build();

        Table table2 = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(3)
                .addRow("Index", "Value")
                .addRow("1", "Hello")
                .addRow("2", "World")
                .build();

        assertNotEquals(table1, table2);
    }
}
