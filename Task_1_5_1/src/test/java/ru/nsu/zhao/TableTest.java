package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void testRowLimitExceeded() {
        Table.Builder builder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .withRowLimit(2)
                .addRow("Header1", "Header2")
                .addRow("Value1", "Value2");

        assertThrows(IllegalStateException.class, () -> builder.addRow("Value3", "Value4"));
    }

    @Test
    void testInvalidAlignment() {
        assertThrows(IllegalStateException.class, () -> new Table.Builder().build());
    }

    @Test
    void testNotEquals() {
        Table table1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Header1", "Header2")
                .addRow("Value1", "Value2")
                .build();

        Table table2 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Header1", "Header2")
                .addRow("Value3", "Value4")
                .build();

        assertNotEquals(table1, table2);
    }
}
