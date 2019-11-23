package com.zhangzz;

public class TestBean {

    public void init() {
        System.out.println("TestBean的init方法执行...");
    }

    public void doSomething() {
        System.out.println(System.currentTimeMillis() + "    " + this);
    }

    public void destroy() {
        System.out.println("TestBean的destroy方法执行...");
    }
}
