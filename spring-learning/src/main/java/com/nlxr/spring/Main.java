package com.nlxr.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = 
                new ClassPathXmlApplicationContext("classpath:spring-context.xml");

//        applicationContext.start();
        System.out.println("IOC 启动成功");
        DemoService demoService =  applicationContext.getBean(DemoService.class);
//        DemoService demoService = (DemoService) applicationContext.getBean("demoService");

        demoService.sayHello();
        System.out.println();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName:"+beanDefinitionName);
        }

        BeanFactory parentBeanFactory = applicationContext.getParentBeanFactory();
        System.out.println(parentBeanFactory);

        String[] services = applicationContext.getAliases("service");
        for (String service : services) {
            System.out.println("aliase: " + service);
        }
    }
}
