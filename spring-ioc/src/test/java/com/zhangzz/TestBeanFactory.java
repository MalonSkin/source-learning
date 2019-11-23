package com.zhangzz;

public class TestBeanFactory {

    public static TestBean getTestBean(){
        return new TestBean();
    }

    public TestBean getOtherTestBean(){
        return new TestBean();
    }
}
