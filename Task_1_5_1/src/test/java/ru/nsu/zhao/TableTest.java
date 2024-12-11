package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Table 类的功能.
 */
public class TableTest {
    @Test
    void testTableSerializationWithAlignments() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Header 1", "Header 2")
                .addRow("Data 1", "**Bold Data 2**")
                .build();
        String expected = "| Header 1 | Header 2 |\n| ---: | :--- |\n| Data 1 | **Bold Data 2** |";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithMultipleRows() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .addRow("Index", "Value")
                .addRow("1", "A")
                .addRow("2", "B")
                .addRow("3", "C")
                .build();
        String expected = "| Index | Value |\n| :--- | :---: |\n| 1 | A |\n| 2 | B |\n| 3 | C |";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithDifferentAlignments() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .addRow("Column 1", "Column 2")
                .addRow("Item 1", "200")
                .addRow("Item 2", "300")
                .build();
        String expected = "| Column 1 | Column 2 |\n| :---: | ---: |\n| Item 1 | 200 |\n| Item 2 | 300 |";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithoutAlignment() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            new Table.Builder().addRow("No Alignment").build();
        });
        assertEquals("表格必须至少有一行和对齐方式", exception.getMessage());
    }

    @Test
    void testTableWithoutRows() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            new Table.Builder().withAlignments(Table.ALIGN_LEFT).build();
        });
        assertEquals("表格必须至少有一行和对齐方式", exception.getMessage());
    }
}
