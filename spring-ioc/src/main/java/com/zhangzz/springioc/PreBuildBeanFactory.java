package com.zhangzz.springioc;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于提前实例化单例对象
 */
@Log4j2
public class PreBuildBeanFactory extends DefaultBeanFactory {

    private final List<String> beanNames = new ArrayList<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
        super.registerBeanDefinition(beanName, beanDefinition);
        synchronized (beanNames) {
            beanNames.add(beanName);
        }
    }

    /**
     * 提前实例化单例对象
     * 使用synchronized解决线程安全问题
     */
    public void preInstantiateSingletons() throws Exception{
        synchronized (beanNames){
            for (String name : beanNames){
                BeanDefinition beanDefinition = this.getBeanDefinition(name);
                if (beanDefinition.isSingleton()){
                    this.doGetBean(name);
                    if (log.isDebugEnabled()){
                        log.debug("preInstantiate:name="+name+" "+beanDefinition);
                    }
                }
            }
        }
    }
}
