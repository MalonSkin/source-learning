package com.zhangzz;

import com.zhangzz.hashmap.MyHashMap;

import java.util.ArrayList;
import java.util.HashSet;

public class Test {

    public static void main(String[] args) {
        MyHashMap<String, Object> map = new MyHashMap<>(4, 0.75);
        map.put("1", 1);
        map.put("2", "哈哈");
        map.put("3", new MyHashMap<>());
        map.put("4", new ArrayList<>());
        map.put("5", new HashSet<>());
        System.out.println();
    }
}
