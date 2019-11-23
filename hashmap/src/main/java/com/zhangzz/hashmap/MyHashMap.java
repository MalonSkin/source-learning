package com.zhangzz.hashmap;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements BaseMap<K, V> {

    /**
     * 默认长度
     */
    private int defaultLength = 16;
    /**
     * 默认负载因子
     */
    private double defaultAddFactor = 0.75;
    /**
     * 使用数组位置的数量
     */
    private double userSize;
    /**
     * 数组
     */
    private Entry<K, V>[] table;

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int defaultLength, double defaultAddFactor) {
        if (defaultLength < 0) {
            throw new IllegalArgumentException("数组异常");
        }
        if (defaultAddFactor <= 0 || Double.isNaN(defaultAddFactor)) {
            throw new IllegalArgumentException("因子异常");
        }
        this.defaultLength = defaultLength;
        this.defaultAddFactor = defaultAddFactor;
        table = new Entry[defaultLength];
    }

    /**
     * 使用每个object的hashCode计算hashCode
     */
    private int hash(int hashCode) {
        hashCode = hashCode ^ ((hashCode >>> 20) ^ (hashCode >>> 12));
        return hashCode ^ ((hashCode >>> 7) ^ hashCode >>> 4);
    }

    private int getIndex(K k, int length) {
        int m = length - 1;
        int index = hash(k.hashCode()) & m; //取模
        return index >= 0 ? index : -index;
    }

    @Override
    public V put(K k, V v) {
        if (userSize > defaultAddFactor * defaultLength) {
            // 扩容
            dilatation();
        }
        // 计算出下标
        int index = getIndex(k, table.length);
        Entry<K, V> entry = table[index];
        Entry<K, V> newEntry = new Entry<>(k, v, null);
        if (entry == null) {
            // 该位置为空
            table[index] = newEntry;
            userSize++; // 位置使用加一
        } else {
            Entry<K, V> t = entry;
            // 相同key 修改对应的值
            if (t.getKey() == k || (t.getKey() != null && t.getKey().equals(k))) {
                t.v = v;
            } else {
                while (t.next != null) {
                    // 相同key 修改对应的值
                    if (t.next.getKey() == k || (t.next.getKey() != null && t.next.getKey().equals(k))) {
                        t.next.v = v;
                        break;
                    } else {
                        t = t.next;
                    }
                }
                if (t.next == null) {
                    t.next = newEntry;
                }
            }
        }
        return newEntry.getValue();
    }

    /**
     * 扩容
     */
    private void dilatation() {
        Entry<K, V>[] newTable = new Entry[defaultLength * 2];
        List<Entry<K, V>> list = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry == null) {
                continue;
            }
            // 遍历链表添加到list集合中
            while (entry != null) {
                list.add(entry);
                entry = entry.next;
            }
        }
        if (list.size() > 0) {
            userSize = 0;
            defaultLength = defaultLength * 2;
            table = newTable;
            for (Entry<K, V> entry : list) {
                // 分离所有entry，重新put
                if (entry.next != null) {
                    entry.next = null;
                }
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public V get(K k) {
        int index = getIndex(k, table.length);
        Entry<K, V> entry = table[index];
        if (entry == null) {
            throw new NullPointerException();
        }
        while (entry != null) {
            if (k == entry.getKey() || k.equals(entry.getKey())) {
                return entry.v;
            } else {
                entry = entry.next;
            }
        }
        return null;
    }
}
