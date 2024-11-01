package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * HashTable 类的单元测试类。
 * 测试主要覆盖插入、删除、更新、查找等基本操作，以及异常处理情况。
 */
class HashTableTest {

    private HashTable<String, Number> hashTable;

    /**
     * 在每个测试之前初始化一个新的哈希表实例。
     */
    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    /**
     * 测试插入键值对到哈希表。
     * 检查插入后的键值对是否能正确查找到。
     */
    @Test
    void testPut() {
        hashTable.insert("one", 1);
        assertEquals(1, hashTable.search("one"));

        hashTable.insert("two", 2);
        assertEquals(2, hashTable.search("two"));
    }

    /**
     * 测试删除哈希表中的键值对。
     * 删除后检查哈希表中不再包含该键。
     */
    @Test
    void testRemove() {
        hashTable.insert("one", 1);
        hashTable.delete("one");
        assertNull(hashTable.search("one"));
    }

    /**
     * 测试更新已存在键的值。
     * 检查更新后的值是否正确。
     */
    @Test
    void testUpdate() {
        hashTable.insert("one", 1);
        hashTable.update("one", 1.0);
        assertEquals(1.0, hashTable.search("one"));
    }

    /**
     * 测试查找已存在和不存在的键。
     * 存在的键应返回对应的值，不存在的键应返回 null。
     */
    @Test
    void testGet() {
        hashTable.insert("one", 1);
        assertEquals(1, hashTable.search("one"));
        assertNull(hashTable.search("nonexistent"));
    }

    /**
     * 测试判断哈希表中是否包含指定键。
     */
    @Test
    void testContainsKey() {
        hashTable.insert("one", 1);
        assertTrue(hashTable.hasKey("one"));
        assertFalse(hashTable.hasKey("two"));
    }

    /**
     * 测试迭代哈希表中的元素。
     * 使用迭代器遍历所有元素并检查数量。
     */
    @Test
    void testIterator() {
        hashTable.insert("one", 1);
        hashTable.insert("two", 2);

        int count = 0;
        for (HashTable.HashEntry<String, Number> entry : hashTable) {
            count++;
        }
        assertEquals(2, count);
    }

    /**
     * 测试两个哈希表的相等性判断。
     * 插入相同的键值对到两个哈希表，并检查是否相等。
     */
    @Test
    void testEquals() {
        HashTable<String, Number> otherTable = new HashTable<>();

        hashTable.insert("one", 1);
        hashTable.insert("two", 2);

        otherTable.insert("one", 1);
        otherTable.insert("two", 2);

        assertTrue(hashTable.isEqual(otherTable));

        otherTable.insert("three", 3);
        assertFalse(hashTable.isEqual(otherTable));
    }

    /**
     * 测试哈希表的字符串表示。
     * 确保生成的字符串表示包含所有键值对。
     */
    @Test
    void testToString() {
        hashTable.insert("one", 1);
        hashTable.insert("two", 2);
        String result = hashTable.toString();

        assertTrue(result.contains("one=1"));
        assertTrue(result.contains("two=2"));
    }
}
