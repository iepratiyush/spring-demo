package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestResponseLoggingAspect {
    
    @Pointcut("@annotation(com.example.demo.annotations.LogRequestResponse)")
    public void logAnnotationPointcut() {
    }

    @Before("logAnnotationPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature().getName());
    }


    @AfterReturning(pointcut = "logAnnotationPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After " + joinPoint.getSignature().getName() +  "Method returned value " + result);

        if (result instanceof ResponseEntity) {
            ResponseEntity<Object> response = (ResponseEntity<Object>) result;
            System.out.println("Response status: " + response.getStatusCode());
        }
    }

}
