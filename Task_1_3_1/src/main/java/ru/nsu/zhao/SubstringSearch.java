package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 用于子字符串搜索的工具类。
 */
public class SubstringSearch {

    /**
     * 在资源文件中搜索指定的子字符串，并返回其所有出现的起始索引。
     *
     * @param resourceName 资源文件的名称
     * @param subName      要搜索的子字符串
     * @return 子字符串在资源文件中所有出现的起始索引列表
     * @throws ResourceReadException 如果资源读取失败或资源未找到
     */
    public static ArrayList<Long> resourceSearch(String resourceName, String subName)
            throws ResourceReadException {
        ArrayList<Long> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder(); // 用于保存滑动窗口内容
        long globalIndex = 0; // 全局字符索引

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        SubstringSearch.class.getClassLoader().getResourceAsStream(resourceName)),
                StandardCharsets.UTF_8))
        ) {
            // 初始化缓冲区，读取与子字符串长度相同的字符
            char[] buffer = new char[subLength];
            int readChars = reader.read(buffer);

            // 如果资源内容长度小于子字符串，直接返回空列表
            if (readChars < subLength) {
                return indexes;
            }

            // 初始化滑动窗口内容
            current.append(buffer, 0, readChars);
            if (current.toString().equals(subName)) {
                indexes.add(globalIndex); // 如果初始窗口匹配，记录索引
            }
            ++globalIndex;

            int nextChar;
            // 滑动窗口逐字符读取资源内容
            while ((nextChar = reader.read()) != -1) {
                current.deleteCharAt(0); // 删除滑动窗口的第一个字符
                current.append((char) nextChar); // 添加新字符

                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex); // 如果匹配，记录索引
                }
                ++globalIndex;
            }
        } catch (IOException e) {
            throw new ResourceReadException("读取资源失败: " + resourceName, e);
        } catch (NullPointerException e) {
            throw new ResourceReadException("资源未找到: " + resourceName, e);
        }

        return indexes;
    }

    /**
     * 搜索由 Reader 提供的文本内容中的指定子字符串，适用于处理生成的大文件。
     *
     * @param reader  Reader 提供的文本内容
     * @param subName 要搜索的子字符串
     * @return 子字符串在文本中所有出现的起始索引列表
     * @throws IOException 如果读取文本时发生 I/O 错误
     */
    public static ArrayList<Long> searchInReader(Reader reader, String subName)
            throws IOException {
        ArrayList<Long> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder(); // 滑动窗口内容
        long globalIndex = 0; // 全局字符索引

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            // 初始化缓冲区，读取与子字符串长度相同的字符
            char[] buffer = new char[subLength];
            int readChars = bufferedReader.read(buffer);

            // 如果内容长度小于子字符串，直接返回空列表
            if (readChars < subLength) {
                return indexes;
            }

            // 初始化滑动窗口内容
            current.append(buffer, 0, readChars);
            if (current.toString().equals(subName)) {
                indexes.add(globalIndex); // 如果初始窗口匹配，记录索引
            }

            int nextChar;
            // 滑动窗口逐字符读取内容
            while ((nextChar = bufferedReader.read()) != -1) {
                ++globalIndex;

                current.deleteCharAt(0); // 删除滑动窗口的第一个字符
                current.append((char) nextChar); // 添加新字符

                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex - subLength + 1); // 记录匹配的起始索引
                }
            }
        }

        return indexes;
    }
}
