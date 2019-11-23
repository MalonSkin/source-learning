package com.zhangzz.springioc;

/**
 * Bean工厂
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;
}
