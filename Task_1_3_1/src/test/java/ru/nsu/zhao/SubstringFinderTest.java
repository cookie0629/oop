package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringFinderTest {
    /**
     * 测试子字符串不存在的情况。
     */
    @Test
    void testFindSubstringNotFound() throws IOException {
        String fileName = "test2.txt";
        String content = "абракадабра";
        String subStr = "змея";

        createTestFile(fileName, content);

        List<Long> result = SubstringFinder.find(fileName, subStr);
        assertEquals(List.of(), result, "子字符串不在文件中，应返回空列表");

        deleteTestFile(fileName);
    }

    /**
     * 测试文件为空的情况。
     */
    @Test
    void testEmptyFile() throws IOException {
        String fileName = "test3.txt";
        String content = "";
        String subStr = "бра";

        createTestFile(fileName, content);

        List<Long> result = SubstringFinder.find(fileName, subStr);
        assertEquals(List.of(), result, "空文件中不可能找到子字符串，应返回空列表");

        deleteTestFile(fileName);
    }

    /**
     * 测试子字符串为空的情况。
     */
    @Test
    void testEmptySubstring() throws IOException {
        String fileName = "test4.txt";
        String content = "абракадабра";
        String subStr = "";

        createTestFile(fileName, content);

        List<Long> result = SubstringFinder.find(fileName, subStr);
        assertEquals(List.of(), result, "空子字符串应返回空列表");

        deleteTestFile(fileName);
    }

    /**
     * 测试文件较大，子字符串存在的情况。
     */
    @Test
    void testLargeFile() throws IOException {
        String fileName = "test5.txt";
        String subStr = "бра";
        int repetitions = 1_000_000;
        String content = "абракадабра\n".repeat(repetitions);

        createTestFile(fileName, content);

        List<Long> result = SubstringFinder.find(fileName, subStr);
        // 验证第 1 和每行的子字符串位置
        assertEquals(2 * repetitions, result.size(), "子字符串出现的总次数应匹配内容");

        deleteTestFile(fileName);
    }

    /**
     * 工具方法：创建测试文件。
     */
    private void createTestFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    /**
     * 工具方法：删除测试文件。
     */
    private void deleteTestFile(String fileName) throws IOException {
        Files.delete(Path.of(fileName));
    }
}
