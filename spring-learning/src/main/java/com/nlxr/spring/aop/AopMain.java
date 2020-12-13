package com.nlxr.spring.aop;

import com.nlxr.spring.DemoService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = 
                new ClassPathXmlApplicationContext("classpath:spring-context.xml");

//        applicationContext.start();
        System.out.println("IOC 启动成功");
        IMyService servcie =  applicationContext.getBean(IMyService.class);
        Person labu = new Person(1, "labu", 30);
        servcie.commitPerson(labu);

        God god = new God("CL", "MX", "wo");
        servcie.commitPerson1(labu,god);
        servcie.commitPerson2(labu,god);

    }
}
