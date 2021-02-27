package com.nlxr.spring.aop;

import org.springframework.stereotype.Service;

@Service
public class MyServcie implements IMyService{

    @RepeatCommitLimit(checkParams = {"id","name","age"})
    public String commitPerson(Person p){
        System.out.println("commit person : "+p);

        return "success";
    }
    @RepeatCommitLimit(checkParams = {"id","age"})
    public String commitPerson1(Person p){
        System.out.println("commit person : "+p);

        return "success";
    }

    @RepeatCommitLimit(checkParams = {"id","age","cluo","name"})
    public String commitPerson1(Person p,God g){
        System.out.println("commit person : "+p);
        System.out.println("commit god : "+g);

        return "success";
    }

    @RepeatCommitLimit(checkParams = {"id","age","name"})
    public String commitPerson2(Person p,God g){
        System.out.println("commit person : "+p);
        System.out.println("commit god : "+g);

        return "success";
    }

}
