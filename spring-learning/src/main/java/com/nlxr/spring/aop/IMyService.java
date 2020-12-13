package com.nlxr.spring.aop;

public interface IMyService {
    @RepeatCommitLimit(checkParams = {"id","name","age"})
    String commitPerson(Person p);

    @RepeatCommitLimit(checkParams = {"id","age"})
    String commitPerson1(Person p);

    @RepeatCommitLimit(checkParams = {"id","age","cluo","name"})
    String commitPerson1(Person p,God g);

    @RepeatCommitLimit(checkParams = {"id","age","name"})
    String commitPerson2(Person p,God g);
}
