package ru.nsu.zhao;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 哈希表的参数化实现
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class HashTable<K, V> implements Iterable<HashTable.HashEntry<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_THRESHOLD = 0.75f;

    private LinkedList<HashEntry<K, V>>[] buckets;
    private int currentSize;
    private int modCount;

    /**
     * 静态内部类，用于存储键值对
     */
    public static class HashEntry<K, V> {
        private final K key;
        private V value;

        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V newValue) {
            value = newValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * 构造一个空的哈希表
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = new LinkedList[DEFAULT_CAPACITY];
        currentSize = 0;
        modCount = 0;
    }

    /**
     * 插入键值对，键值对已存在时更新值
     *
     * @param key   键
     * @param value 值
     */
    public void insert(K key, V value) {
        int index = computeHash(key);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        boolean found = false;
        for (HashEntry<K, V> entry : buckets[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                found = true;
                break;
            }
        }
        if (!found) {
            buckets[index].add(new HashEntry<>(key, value));
            currentSize++;
            if (currentSize > buckets.length * LOAD_THRESHOLD) {
                expand();
            }
        }
        modCount++;
    }

    /**
     * 根据键删除条目，找到条目即删除
     *
     * @param key 要删除的键
     * @throws NoSuchElementException 如果键不存在
     */
    public void delete(K key) {
        int index = computeHash(key);
        if (buckets[index] != null) {
            Iterator<HashEntry<K, V>> iterator = buckets[index].iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getKey().equals(key)) {
                    iterator.remove();
                    currentSize--;
                    modCount++;
                    return;
                }
            }
        }
        throw new NoSuchElementException("Key not found for removal: " + key);
    }

    /**
     * 根据键查找值，键不存在返回null
     *
     * @param key 查找的键
     * @return 键关联的值或null（如果键不存在）
     */
    public V search(K key) {
        int index = computeHash(key);
        if (buckets[index] != null) {
            for (HashEntry<K, V> entry : buckets[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 更新键的值（如果键存在）
     *
     * @param key   键
     * @param value 新值
     * @throws IllegalArgumentException 如果键不存在
     */
    public void update(K key, V value) {
        V oldValue = search(key);
        if (oldValue == null) {
            throw new IllegalArgumentException("Key does not exist: " + key);
        }
        insert(key, value);
    }

    /**
     * 是否包含指定键
     *
     * @param key 键
     * @return 如果包含返回true，否则返回false
     */
    public boolean hasKey(K key) {
        return search(key) != null;
    }

    /**
     * 返回当前表的迭代器
     *
     * @return 迭代器
     */
    @Override
    public Iterator<HashEntry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * 比较当前哈希表与另一个哈希表是否相等
     *
     * @param other 另一哈希表
     * @return 如果相等则返回true，否则返回false
     */
    public boolean isEqual(HashTable<K, V> other) {
        if (this.currentSize != other.currentSize) return false;
        for (HashEntry<K, V> entry : this) {
            V otherValue = other.search(entry.getKey());
            if (!entry.getValue().equals(otherValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将哈希表内容转换为字符串
     *
     * @return 哈希表的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (LinkedList<HashEntry<K, V>> list : buckets) {
            if (list != null) {
                for (HashEntry<K, V> entry : list) {
                    builder.append(entry.toString()).append(", ");
                }
            }
        }
        if (builder.length() > 1) builder.setLength(builder.length() - 2);
        builder.append("}");
        return builder.toString();
    }

    /**
     * 计算键的哈希值
     *
     * @param key 键
     * @return 哈希值（桶的索引）
     */
    private int computeHash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    /**
     * 扩容哈希表
     */
    @SuppressWarnings("unchecked")
    private void expand() {
        LinkedList<HashEntry<K, V>>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        currentSize = 0;
        for (LinkedList<HashEntry<K, V>> list : oldBuckets) {
            if (list != null) {
                for (HashEntry<K, V> entry : list) {
                    insert(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * 哈希表迭代器类
     */
    private class HashTableIterator implements Iterator<HashEntry<K, V>> {
        private int bucketIndex;
        private Iterator<HashEntry<K, V>> listIterator;
        private final int expectedModCount;

        public HashTableIterator() {
            bucketIndex = -1;
            expectedModCount = modCount;
            moveToNextBucket();
        }

        private void moveToNextBucket() {
            listIterator = null;
            while (++bucketIndex < buckets.length) {
                if (buckets[bucketIndex] != null && !buckets[bucketIndex].isEmpty()) {
                    listIterator = buckets[bucketIndex].iterator();
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("哈希表发生了并发修改！");
            }
            return listIterator != null && (listIterator.hasNext() || nextBucketAvailable());
        }

        private boolean nextBucketAvailable() {
            moveToNextBucket();
            return listIterator != null;
        }

        @Override
        public HashEntry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException("没有更多元素");
            return listIterator.next();
        }
    }
}
