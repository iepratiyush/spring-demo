package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Pratiyush Prakash
 * 
 * This Aspect is a module which is responsible 
 * for logging all requests and response where we apply the 
 * LogRequestResponse annotation
 */
@Aspect
@Component
public class RequestResponseLoggingAspect {
    
    // Define the pointcut for LogRequestResponse annotation
    @Pointcut("@annotation(com.example.demo.annotations.LogRequestResponse)")
    public void logAnnotationPointcut() {
    }

    // Before advice
    @Before("logAnnotationPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature().getName());
    }


    // Normal after advice
    @AfterReturning(pointcut = "logAnnotationPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // Logging the response
        System.out.println("After " + joinPoint.getSignature().getName() +  "Method returned value " + result);

        // Logging the response status
        if (result instanceof ResponseEntity) {
            ResponseEntity<Object> response = (ResponseEntity<Object>) result;
            System.out.println("Response status: " + response.getStatusCode());
        }
    }

}
