package com.nlxr.spring.annotation;

import java.lang.annotation.*;


/**
 * 方法调用log
 * 自定义注解
 * Java5.0定义的元注解：
 * 　　　　1.@Target,说明了Annotation所修饰的对象范围
 *          取值(ElementType)有：
 *              1.CONSTRUCTOR:用于描述构造器
 *   　　　　     2.FIELD:用于描述域
 *   　　　　     3.LOCAL_VARIABLE:用于描述局部变量
 *   　　　　     4.METHOD:用于描述方法
 *   　　　　     5.PACKAGE:用于描述包
 *   　　　　     6.PARAMETER:用于描述参数
 *   　　　　     7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * 　　　　2.@Retention,表示需要在什么级别保存该注释信息，用于描述注解的生命周期
 *          取值（RetentionPoicy）有：
 * 　　　　       1.SOURCE:在源文件中有效（即源文件保留）
 * 　　　　       2.CLASS:在class文件中有效（即class保留）
 * 　　　　       3.RUNTIME:在运行时有效（即运行时保留）
 * 　　　　3.@Documented,用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，
 *          因此可以被例如javadoc此类的工具文档化
 * 　　　　4.@Inherited,表示某个被标注的类型是被继承的
 * @author labu
 * @date 2020/09/15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CallLog {
    String value() default "";
}
