package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTests {

    @Test
    void testTableSerialization() {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random")
                .addRow("1", "**8**")
                .addRow("2", "2")
                .addRow("3", "3")
                .addRow("4", "**6**")
                .addRow("5", "3");

        String expectedMarkdown = "| Index | Random |\n| -----:| ------ |\n|     1 | **8**  |\n|     2 | 2      |\n|     3 | 3      |\n|     4 | **6**  |\n|     5 | 3      |\n";
        assertEquals(expectedMarkdown, tableBuilder.build());
    }

    @Test
    void testTableEquality() {
        Table.Builder tableBuilder1 = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("1", "A")
                .addRow("2", "B");

        Table.Builder tableBuilder2 = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("1", "A")
                .addRow("2", "B");

        Table table1 = tableBuilder1.build();
        Table table2 = tableBuilder2.build();
        assertEquals(table1, table2);
        assertEquals(table1.hashCode(), table2.hashCode());

        Table.Builder differentTableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("1", "A")
                .addRow("2", "C");

        Table differentTable = differentTableBuilder.build();
        assertNotEquals(table1, differentTable);
    }
}

