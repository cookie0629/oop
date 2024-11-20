package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 主程序类，演示如何调用子字符串搜索方法。
 */
public class Main {

    /**
     * 在给定文本中查找子字符串并返回所有出现的起始索引。
     *
     * @param text    要搜索的文本
     * @param subName 要查找的子字符串
     * @return 所有匹配的起始索引
     */
    public static List<Integer> findSubString(String text, String subName) {
        List<Integer> indexes = new ArrayList<>();
        int subLength = subName.length();
        int index = 0;

        // 遍历文本，查找子字符串出现的位置
        while ((index = text.indexOf(subName, index)) != -1) {
            indexes.add(index);
            index++; // 向前移动一位继续查找下一个匹配项
        }

        return indexes;
    }

    public static void main(String[] args) {
        // 示例文本
        String text = "абракадабра";
        // 要查找的子字符串
        String subName = "бра";

        // 调用 findSubString 方法查找匹配的起始索引
        List<Integer> result = findSubString(text, subName);

        // 输出结果
        System.out.println(result); // 输出: [1, 8]
    }
}
