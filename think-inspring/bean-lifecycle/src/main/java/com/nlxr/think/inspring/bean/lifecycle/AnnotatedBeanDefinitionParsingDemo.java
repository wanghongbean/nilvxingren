package com.nlxr.think.inspring.bean.lifecycle;


import com.nlxr.think.inspring.bean.expand.MyBeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.stereotype.Component;

/**
 * 注解 BeanDefinition 解析示例
 *  AnnotatedBeanDefinitionReader
 * @author labu
 * @date 2020/10/07
 */
//@Component(value = "labu")
public class AnnotatedBeanDefinitionParsingDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //基于java 注解的 AnnotatedBeanDefinitionReader的实现
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);

        //使用默认的annotationBeanNameGenerator
//        normal(beanFactory, beanDefinitionReader);

        //使用自定义的beanNameGenerator
        customBeanNameGeneratorDemo(beanFactory, beanDefinitionReader);
    }

    private static void customBeanNameGeneratorDemo(DefaultListableBeanFactory beanFactory, AnnotatedBeanDefinitionReader beanDefinitionReader) {
        MyBeanNameGenerator myBeanNameGenerator = new MyBeanNameGenerator();
        beanDefinitionReader.setBeanNameGenerator(myBeanNameGenerator);
        beanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);
        String beanName="com.nlxr.think.inspring.bean.lifecycle.AnnotatedBeanDefinitionParsingDemo$annotatedBeanDefinitionParsingDemo";
        AnnotatedBeanDefinitionParsingDemo bean = beanFactory.getBean(beanName,AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(bean);
    }

    private static void normal(DefaultListableBeanFactory beanFactory, AnnotatedBeanDefinitionReader beanDefinitionReader) {
        //手动注册类之前的 beanDefinition count
        int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();
        //注册当前类（非 @Component class）
        beanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);

        int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();
        System.out.println("已加载的 beanDefinition 数量："+(beanDefinitionCountAfter-beanDefinitionCountBefore));

        // 普通的 Class 作为 Component 注册到 Spring IoC 容器后，通常 Bean 名称为 annotatedBeanDefinitionParsingDemo
        // Bean 名称生成来自于 BeanNameGenerator，注解实现 AnnotationBeanNameGenerator
        AnnotatedBeanDefinitionParsingDemo bean =
//                beanFactory.getBean("labu", AnnotatedBeanDefinitionParsingDemo.class);
                beanFactory.getBean("annotatedBeanDefinitionParsingDemo", AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(bean);
    }
}
