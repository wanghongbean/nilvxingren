package com.nlxr.spring.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Aspect
@Component
public class MyAspects {

    @Pointcut("@annotation(com.nlxr.spring.aop.RepeatCommitLimit)")
    public void MyPointCut() {
    }


    @Around(value = "MyPointCut()")
    public Object around(ProceedingJoinPoint pjd) {
//        System.out.println("pjd.getSignature   " + JSON.toJSONString(pjd.getSignature()));
//        System.out.println("pjd.getSignature   " + JSON.toJSONString(pjd.getSignature().getClass()));
        MethodSignature signature = (MethodSignature) pjd.getSignature();
        Class[] parameterTypes = signature.getParameterTypes();
        String[] parameterNames = signature.getParameterNames();
        System.out.println("pjd.getArgs   " + JSON.toJSONString(pjd.getArgs()));
        RepeatCommitLimit annotation = signature.getMethod().getAnnotation(RepeatCommitLimit.class);
        Set<String> checkParams = new HashSet(Arrays.asList(annotation.checkParams()));
        Object[] args = pjd.getArgs();
        String fieldValue = null;
        try {
            fieldValue = getFieldValue(args, checkParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(fieldValue);


        return null;
    }

    public String getFieldValue(Object[] objs, Set<String> checks) throws Exception {
        StringBuilder sb = new StringBuilder();
        int t =0;
        for (Object o : objs) {
            if (Objects.equals(t,checks.size())){
                System.out.println("out break");
                break;
            }
            Class<?> oC = o.getClass();
            Field[] declaredFields = oC.getDeclaredFields();
            for (Field f : declaredFields) {
                if (checks.contains(f.getName())) {
                    f.setAccessible(true);
                    Object v = f.get(o);
                    sb.append(v);
                    t++;
                    if (Objects.equals(t,checks.size())){
                        System.out.println("in break;");
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
