package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 子字符串搜索类，用于在大文件中查找子字符串出现的起始索引。
 */
public class SubstringSearch {

    /**
     * 在资源文件中搜索子字符串，并返回所有匹配的起始索引。
     *
     * @param resourceName 资源文件的名称
     * @param subName      需要搜索的子字符串
     * @return 子字符串每次出现的起始索引列表
     * @throws IOException 如果在读取文件时发生错误
     */
    public static ArrayList<Long> resourceSearch(String resourceName, String subName) throws IOException {
        ArrayList<Long> indexes = new ArrayList<>(); // 用于存储子字符串出现的起始索引
        int subLength = subName.length(); // 子字符串的长度
        StringBuilder current = new StringBuilder(); // 滑动窗口，用于存储当前正在比较的文本片段
        long globalIndex = 0; // 当前全局索引

        // 使用 BufferedReader 逐字符读取文件
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        SubstringSearch.class.getClassLoader().getResourceAsStream(resourceName)),
                StandardCharsets.UTF_8))) {

            // 初始化滑动窗口，读取与子字符串等长的第一个片段
            char[] buffer = new char[subLength];
            int readChars = reader.read(buffer);

            // 如果文件长度小于子字符串长度，直接返回空结果
            if (readChars < subLength) {
                return indexes;
            }

            // 将读取的片段加入滑动窗口
            current.append(buffer, 0, readChars);
            // 如果第一个片段匹配子字符串，则记录索引
            if (current.toString().equals(subName)) {
                indexes.add(globalIndex);
            }
            ++globalIndex;

            // 逐字符读取剩余部分并更新滑动窗口
            int nextChar;
            while ((nextChar = reader.read()) != -1) {
                current.deleteCharAt(0); // 移除窗口的第一个字符
                current.append((char) nextChar); // 添加新的字符到窗口末尾

                // 如果当前窗口的内容与子字符串匹配，记录索引
                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex);
                }
                ++globalIndex;
            }
        } catch (NullPointerException e) {
            throw new IOException("资源文件未找到: " + resourceName, e);
        }

        return indexes;
    }

    /**
     * 使用 Reader 对象搜索子字符串，用于处理任意输入流。
     *
     * @param reader  提供文本内容的 Reader
     * @param subName 需要搜索的子字符串
     * @return 子字符串每次出现的起始索引列表
     * @throws IOException 如果在读取内容时发生错误
     */
    public static ArrayList<Long> searchInReader(BufferedReader reader, String subName) throws IOException {
        ArrayList<Long> indexes = new ArrayList<>(); // 用于存储子字符串出现的起始索引
        int subLength = subName.length(); // 子字符串的长度
        StringBuilder current = new StringBuilder(); // 滑动窗口，用于存储当前正在比较的文本片段
        long globalIndex = 0; // 当前全局索引

        // 初始化滑动窗口，读取与子字符串等长的第一个片段
        char[] buffer = new char[subLength];
        int readChars = reader.read(buffer);

        // 如果内容长度小于子字符串长度，直接返回空结果
        if (readChars < subLength) {
            return indexes;
        }

        // 将读取的片段加入滑动窗口
        current.append(buffer, 0, readChars);
        // 如果第一个片段匹配子字符串，则记录索引
        if (current.toString().equals(subName)) {
            indexes.add(globalIndex);
        }
        ++globalIndex;

        // 逐字符读取剩余部分并更新滑动窗口
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            current.deleteCharAt(0); // 移除窗口的第一个字符
            current.append((char) nextChar); // 添加新的字符到窗口末尾

            // 如果当前窗口的内容与子字符串匹配，记录索引
            if (current.toString().equals(subName)) {
                indexes.add(globalIndex - subLength + 1); // 调整起始索引
            }
            ++globalIndex;
        }

        return indexes;
    }
}
