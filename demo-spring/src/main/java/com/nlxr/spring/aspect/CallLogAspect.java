package com.nlxr.spring.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 方法调用记录切面
 *
 * @author labu
 * @date 2020/09/15
 */
@Aspect
@Component
public class CallLogAspect {

    @Pointcut("@annotation(com.nlxr.spring.annotation.CallLog)")
    public void callLog() {

    }

    @Around("callLog()")
    public Object surroundCallLog(ProceedingJoinPoint pjp) {
        System.out.println("==== call log surroud start ====");
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("pjp.getArgs()"+i+" : "+args[i]);
        }
        System.out.println("pjp.getKind() :"+pjp.getKind());
        System.out.println("pjp.getSignature().getName() :"+pjp.getSignature().getName());
        System.out.println("pjp.getSignature().getDeclaringTypeName() :"+pjp.getSignature().getDeclaringTypeName());
        System.out.println("pjp.getSignature().getDeclaringType() :"+pjp.getSignature().getDeclaringType());
        System.out.println("pjp.getSignature().getModifiers() :"+pjp.getSignature().getModifiers());
        System.out.println("pjp.getSourceLocation() :"+pjp.getSourceLocation());
        System.out.println("pjp.getStaticPart() :"+pjp.getStaticPart());
        System.out.println("pjp.getTarget() :"+pjp.getTarget());
        System.out.println("pjp.getThis() :"+pjp.getThis());


        try {
            Object proceed = pjp.proceed();
            System.out.println("pjp.proceed:"+proceed);
            return proceed;
        }catch (Throwable t){
            t.printStackTrace();
            return null;
        }
    }
}
