package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 子字符串搜索工具类。
 * 提供从大文件中高效查找子字符串位置的功能。
 */
public class SubstringFinder {
    /**
     * 在给定文件中查找子字符串的所有起始索引。
     * 使用滑动窗口算法，逐字符读取文件内容，适合处理大文件。
     *
     * @param fileName 要读取的文件名
     * @param subStr   要查找的子字符串
     * @return 子字符串的所有起始索引列表
     * @throws IOException 如果读取文件时发生错误
     */
    public static List<Long> find(String fileName, String subStr) throws IOException {
        List<Long> indices = new ArrayList<>(); // 存储结果的索引列表
        int subLen = subStr.length(); // 子字符串长度

        if (subLen == 0) {
            return indices; // 空子字符串，返回空列表
        }

        char[] buffer = new char[subLen]; // 滑动窗口缓冲区
        long globalIndex = 0; // 全局字符索引

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {

            // 初始化窗口：读取第一个子字符串长度的字符
            int readCount = reader.read(buffer);
            if (readCount < subLen) {
                return indices; // 文件内容不足以匹配子字符串
            }

            // 检查初始化窗口是否匹配子字符串
            if (new String(buffer).equals(subStr)) {
                indices.add(globalIndex);
            }

            int nextChar;
            // 滑动窗口逐字符处理文件内容
            while ((nextChar = reader.read()) != -1) {
                globalIndex++;

                // 移动窗口：删除首字符，添加新字符
                System.arraycopy(buffer, 1, buffer, 0, subLen - 1);
                buffer[subLen - 1] = (char) nextChar;

                // 检查窗口是否匹配子字符串
                if (new String(buffer).equals(subStr)) {
                    indices.add(globalIndex - subLen + 1);
                }
            }
        }

        return indices;
    }
}
