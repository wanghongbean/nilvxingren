package com.nlxr.think.inspring.bean.lifecycle;


import com.nlxr.think.inspring.ioc.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import javax.swing.text.EditorKit;
import java.util.Map;

/**
 * bean元数据配置演示
 * properties格式配置bean
 *
 * @author labu
 * @date 2020/10/06
 */
public class BeanMetadataConfigurationDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //实例化基于 Properties 资源 BeanDefinitionReader
        PropertiesBeanDefinitionReader definitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/user.properties";
//        luanma(beanFactory, definitionReader,location);

        String locationRs = "/META-INF/user1.properties";
        // 基于 ClassPath 加载 properties 资源
        Resource resource = new ClassPathResource(locationRs);
        // 指定字符编码 UTF-8
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        int i = definitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("加载到 BeanDefinition 的数量："+i);
        Map<String, User> beans = beanFactory.getBeansOfType(User.class);
        beans.forEach((k,v)-> System.out.println("beanName:"+k+"="+v));
//        User user = beanFactory.getBean("user", User.class);
//        User user1 = beanFactory.getBean("labu", User.class);
//        System.out.println(user);
//        System.out.println(user1);

    }

    private static void luanma(DefaultListableBeanFactory beanFactory,
                               PropertiesBeanDefinitionReader definitionReader,
                               String location) {
        //加载到配置BeanDefinition的数量
        int i = definitionReader.loadBeanDefinitions(location);
        System.out.println("加载到 BeanDefinition 的数量："+i);
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
