package ru.nsu.zhao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {

    public static List<Integer> find(String fileName, String substring) throws IOException {
        List<Integer> indices = new ArrayList<>();
        int substringLength = substring.length();

        // 缓冲区大小，可以调整大小以优化性能
        int bufferSize = 4096;
        char[] buffer = new char[bufferSize];
        StringBuilder remaining = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            int totalCharsRead = 0; // 已读取的字符总数
            int charsRead;

            while ((charsRead = reader.read(buffer)) != -1) {
                String chunk = remaining.append(new String(buffer, 0, charsRead)).toString();

                // 搜索子字符串
                int index = 0;
                while ((index = chunk.indexOf(substring, index)) != -1) {
                    indices.add(totalCharsRead + index);
                    index++; // 移动索引，继续搜索下一个子串
                }

                // 保留末尾可能包含部分子串的内容
                int remainingLength = Math.min(substringLength - 1, chunk.length());
                remaining = new StringBuilder(chunk.substring(chunk.length() - remainingLength));

                totalCharsRead += charsRead; // 更新读取字符的计数
            }
        }

        return indices;
    }

    public static void main(String[] args) {
        try {
            String fileName = "input.txt";
            String substring = "бра";

            // 调用方法并打印结果
            List<Integer> result = find(fileName, substring);
            System.out.println(result);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
