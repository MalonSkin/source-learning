package com.zhangzz;

import com.zhangzz.springioc.BeanDefinition;
import com.zhangzz.springioc.DefaultBeanFactory;
import com.zhangzz.springioc.GeneralBeanDefinition;
import org.junit.AfterClass;
import org.junit.Test;

public class DefaultBeanFactoryTest {

    private static DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();

    /**
     * 无工厂
     */
    @Test
    public void testRegistry() throws Exception {
        GeneralBeanDefinition generalBeanDefinition = new GeneralBeanDefinition();
        generalBeanDefinition.setBeanClass(TestBean.class);
        generalBeanDefinition.setInitMethodName("init");
        generalBeanDefinition.setDestroyMethodName("destroy");
        generalBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        defaultBeanFactory.registerBeanDefinition("testBean", generalBeanDefinition);
    }

    /**
     * 通过静态工厂方法创建
     */
    @Test
    public void testRegistryStaticFactoryMethod() throws Exception {
        GeneralBeanDefinition generalBeanDefinition = new GeneralBeanDefinition();
        generalBeanDefinition.setBeanClass(TestBeanFactory.class);
        generalBeanDefinition.setFactoryMethodName("getTestBean");
        defaultBeanFactory.registerBeanDefinition("staticBean", generalBeanDefinition);
    }

    @Test
    public void testRegistFactoryMethod() throws Exception {
        // 先注册工厂bean
        GeneralBeanDefinition generalBeanDefinition = new GeneralBeanDefinition();
        generalBeanDefinition.setBeanClass(TestBeanFactory.class);
        String factoryBeanName = "factory";
        defaultBeanFactory.registerBeanDefinition(factoryBeanName, generalBeanDefinition);

        // 再注册实体类bean
        generalBeanDefinition = new GeneralBeanDefinition();
        generalBeanDefinition.setFactoryBeanName(factoryBeanName);
        generalBeanDefinition.setFactoryMethodName("getOtherTestBean");
        generalBeanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        defaultBeanFactory.registerBeanDefinition("factoryBean", generalBeanDefinition);
    }

    @AfterClass
    public static void testGetBean() throws Exception {
        System.out.println("构造方法方式...");
        for (int i = 0; i < 3; i++) {
            TestBean testBean = (TestBean) defaultBeanFactory.getBean("testBean");
            testBean.doSomething();
        }

        System.out.println("静态工厂方法方式...");
        for (int i = 0; i < 3; i++) {
            TestBean testBean = (TestBean) defaultBeanFactory.getBean("staticBean");
            testBean.doSomething();
        }

        System.out.println("工厂方法方式...");
        for (int i = 0; i < 3; i++) {
            TestBean testBean = (TestBean) defaultBeanFactory.getBean("factoryBean");
            testBean.doSomething();
        }

        defaultBeanFactory.close();
    }
}
