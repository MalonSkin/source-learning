package com.zhangzz.springioc;

/**
 * Bean定义注册接口
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册Bean
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;

    /**
     * 获取Bean
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 判断Bean是否已经被注册
     */
    Boolean containsBeanDefinition(String beanName);
}
