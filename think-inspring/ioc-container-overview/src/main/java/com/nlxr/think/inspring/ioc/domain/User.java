package com.nlxr.think.inspring.ioc.domain;

import com.nlxr.think.inspring.enums.City;
import org.springframework.beans.factory.BeanNameAware;

/**
 * 用户pojo
 *
 * @author labu
 * @date 2020/10/06
 */
public class User {
    private Long id;
    private String name;
    private int age;
    private City city;

    /**
     * 当前 Bean 的名称
     */
    private transient String beanName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city=" + city +
                '}';
    }
}
