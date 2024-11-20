package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SubstringFinderTest {

    /**
     * 测试正常的子字符串查找。
     * 测试在文件中查找子字符串的功能。
     */
    @Test
    public void testFindSubstring() throws IOException {
        // 创建一个临时文件来测试
        String content = "абракадабра";
        String fileName = "test_input.txt";
        Files.write(Paths.get(fileName), content.getBytes());

        // 调用 SubstringFinder 的 find 方法
        List<Long> result = SubstringFinder.find(fileName, "бра");

        // 验证返回的结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0));  // 第一个匹配位置是索引 1
        assertEquals(8L, result.get(1));  // 第二个匹配位置是索引 8

        // 删除临时文件
        Files.delete(Paths.get(fileName));
    }

    /**
     * 测试文件中没有匹配的子字符串。
     */
    @Test
    public void testNoMatchFound() throws IOException {
        // 创建一个临时文件来测试
        String content = "abcdefghijk";
        String fileName = "test_input.txt";
        Files.write(Paths.get(fileName), content.getBytes());

        // 调用 SubstringFinder 的 find 方法
        List<Long> result = SubstringFinder.find(fileName, "xyz");

        // 验证返回的结果
        assertNotNull(result);
        assertTrue(result.isEmpty());  // 没有匹配项，返回空列表

        // 删除临时文件
        Files.delete(Paths.get(fileName));
    }

    /**
     * 测试子字符串为空的情况。
     */
    @Test
    public void testEmptySubstring() throws IOException {
        // 创建一个临时文件来测试
        String content = "абракадабра";
        String fileName = "test_input.txt";
        Files.write(Paths.get(fileName), content.getBytes());

        // 调用 SubstringFinder 的 find 方法，查找空子字符串
        List<Long> result = SubstringFinder.find(fileName, "");

        // 验证返回的结果
        assertNotNull(result);
        assertTrue(result.isEmpty());  // 空子字符串返回空列表

        // 删除临时文件
        Files.delete(Paths.get(fileName));
    }

    /**
     * 测试文件内容小于子字符串的情况。
     */
    @Test
    public void testFileContentShorterThanSubstring() throws IOException {
        // 创建一个临时文件来测试
        String content = "abc";
        String fileName = "test_input.txt";
        Files.write(Paths.get(fileName), content.getBytes());

        // 调用 SubstringFinder 的 find 方法，查找一个更长的子字符串
        List<Long> result = SubstringFinder.find(fileName, "abcdef");

        // 验证返回的结果
        assertNotNull(result);
        assertTrue(result.isEmpty());  // 子字符串长度大于文件内容，返回空列表

        // 删除临时文件
        Files.delete(Paths.get(fileName));
    }

    /**
     * 测试文件不存在的情况。
     */
    @Test
    public void testFileNotFound() {
        // 调用 SubstringFinder 的 find 方法，查找不存在的文件
        assertThrows(IOException.class, () -> {
            SubstringFinder.find("non_existent_file.txt", "бра");
        });
    }
}
