package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubstringSearchTest {
    @Test
    void testResourceSearchResourceNotFound() {
        // 测试当资源不存在时的行为
        String resourceName = "nonexistent.txt";
        String subName = "abra";

        // 断言抛出 ResourceReadException
        assertThrows(ResourceReadException.class, () -> {
            SubstringSearch.resourceSearch(resourceName, subName);
        });
    }


    @Test
    void testSearchInReaderEmptyResult() throws Exception {
        // 使用字符串作为模拟输入内容，测试子字符串不存在的情况
        String text = "ababababa";
        String subName = "xyz";

        // Reader 对象
        StringReader reader = new StringReader(text);

        // 期望的结果是空列表
        ArrayList<Long> expected = new ArrayList<>();

        // 调用方法并断言结果
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);
        assertEquals(expected, result);
    }

    @Test
    void testSearchInReaderShortText() throws Exception {
        // 测试子字符串长度大于输入文本长度的情况
        String text = "ab";
        String subName = "abc";

        // Reader 对象
        StringReader reader = new StringReader(text);

        // 期望的结果是空列表
        ArrayList<Long> expected = new ArrayList<>();

        // 调用方法并断言结果
        ArrayList<Long> result = SubstringSearch.searchInReader(reader, subName);
        assertEquals(expected, result);
    }
}
