package ru.nsu.zhao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MarkdownGeneratorTest {

    @Test
    void testBoldSerialization() {
        // 测试加粗文本的序列化
        var bold = new Bold("Test");
        assertEquals("**Test**", bold.toMarkdown(), "加粗文本的序列化输出应正确");
    }


    @Test
    void testRowLimit() {
        // 测试表格行数限制
        Table.Builder builder = new Table.Builder()
                .withRowLimit(2) // 限制最多 2 行
                .addRow("Header1", "Header2");

        builder.addRow(1, "Value1");

        // 添加超过行数限制的行应抛出异常
        assertThrows(IllegalStateException.class, () -> builder.addRow(2, "Value2"),
                "超过行数限制时应抛出 IllegalStateException");
    }

    @Test
    void testEmptyTable() {
        // 测试空表格的异常
        Table.Builder builder = new Table.Builder();
        assertThrows(IllegalStateException.class, builder::build,
                "空表格构建时应抛出 IllegalStateException");
    }

    @Test
    void testEquality() {
        // 测试元素的相等性
        var bold1 = new Bold("Test");
        var bold2 = new Bold("Test");
        var bold3 = new Bold("Different");

        assertEquals(bold1, bold2, "内容相同的加粗文本对象应相等");
        assertNotEquals(bold1, bold3, "内容不同的加粗文本对象不应相等");

        Table.Builder builder1 = new Table.Builder()
                .addRow("Header1", "Header2")
                .addRow(1, "Value1");

        Table.Builder builder2 = new Table.Builder()
                .addRow("Header1", "Header2")
                .addRow(1, "Value1");

        Table table1 = builder1.build();
        Table table2 = builder2.build();

        assertEquals(table1, table2, "内容相同的表格对象应相等");
    }
}
