package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试类，用于验证 SubstringSearch 类的正确性
 */
class SubstringFinderTest {
    /**
     * 测试 resourceSearch 方法，当文件中不包含子字符串时
     */
    @Test
    void testResourceSearchWithNoMatch() throws IOException {
        // 假设资源内容是 "абракадабра"
        String resourceName = "input.txt";
        String subName = "xyz";

        // 创建模拟文件内容的输入流
        String inputContent = "абракадабра";
        BufferedReader reader = new BufferedReader(new StringReader(inputContent));

        // 使用 searchInReader 方法进行搜索
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);

        // 断言没有找到任何匹配项
        assertTrue(result.isEmpty());
    }

    /**
     * 测试 resourceSearch 方法，当文件内容小于子字符串长度时
     */
    @Test
    void testResourceSearchWithSmallFile() throws IOException {
        // 假设资源内容是 "абра"
        String resourceName = "input.txt";
        String subName = "абракадабра";  // 长度大于文件内容

        // 创建模拟文件内容的输入流
        String inputContent = "абра";
        BufferedReader reader = new BufferedReader(new StringReader(inputContent));

        // 使用 searchInReader 方法进行搜索
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);

        // 断言没有找到任何匹配项
        assertTrue(result.isEmpty());
    }

    /**
     * 测试 resourceSearch 方法，在空文件中搜索
     */
    @Test
    void testResourceSearchWithEmptyFile() throws IOException {
        // 假设资源内容是空的
        String resourceName = "input.txt";
        String subName = "бра";

        // 创建模拟空文件内容的输入流
        String inputContent = "";
        BufferedReader reader = new BufferedReader(new StringReader(inputContent));

        // 使用 searchInReader 方法进行搜索
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);

        // 断言没有找到任何匹配项
        assertTrue(result.isEmpty());
    }

    /**
     * 测试 resourceSearch 方法，子字符串与文件内容完全相同的情况
     */
    @Test
    void testResourceSearchWithExactMatch() throws IOException {
        // 假设资源内容是 "абракадабра"
        String resourceName = "input.txt";
        String subName = "абракадабра";  // 完全匹配

        // 创建模拟文件内容的输入流
        String inputContent = "абракадабра";
        BufferedReader reader = new BufferedReader(new StringReader(inputContent));

        // 使用 searchInReader 方法进行搜索
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);

        // 断言只会有一个匹配，位置为0
        assertEquals(1, result.size());
        assertTrue(result.contains(0L));
    }

}
