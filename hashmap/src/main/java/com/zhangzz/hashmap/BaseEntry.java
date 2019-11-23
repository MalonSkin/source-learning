package com.zhangzz.hashmap;

/**
 * 节点的基类
 */
public interface BaseEntry<K, V> {
    /**
     * 获取键
     */
    public K getKey();

    /**
     * 获取值
     */
    public V getValue();
}
