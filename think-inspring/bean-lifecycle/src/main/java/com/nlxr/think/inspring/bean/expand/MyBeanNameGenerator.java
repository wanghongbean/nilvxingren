package com.nlxr.think.inspring.bean.expand;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @Author labu
 * @Date 2020/10/7
 * @Description
 */
public class MyBeanNameGenerator implements BeanNameGenerator {
    /**
     * Generate a bean name for the given bean definition.
     *
     * @param definition the bean definition to generate a name for
     * @param registry   the bean definition registry that the given definition
     *                   is supposed to be registered with
     * @return the generated bean name
     */
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        String shortClassName = ClassUtils.getShortName(beanClassName);
        String decapitalize = Introspector.decapitalize(shortClassName);
        return beanClassName+"$"+decapitalize;
    }
}
