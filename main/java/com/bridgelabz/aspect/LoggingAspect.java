package com.bridgelabz.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.bridgelabz.service..*(..))")
    public Object logServiceExecution(
            ProceedingJoinPoint joinPoint) throws Throwable {

        String className =
                joinPoint
                        .getSignature()
                        .getDeclaringTypeName();

        String methodName =
                joinPoint
                        .getSignature()
                        .getName();

        long startTime =
                System.currentTimeMillis();

        log.info(
                "START {}.{}",
                className,
                methodName
        );

        try {

            Object result =
                    joinPoint.proceed();

            long executionTime =
                    System.currentTimeMillis()
                            - startTime;

            log.info(
                    "SUCCESS {}.{} - executionTime={} ms",
                    className,
                    methodName,
                    executionTime
            );

            return result;

        } catch (Throwable exception) {

            long executionTime =
                    System.currentTimeMillis()
                            - startTime;

            log.error(
                    "FAILED {}.{} - executionTime={} ms - error={}",
                    className,
                    methodName,
                    executionTime,
                    exception.getMessage()
            );

            throw exception;
        }
    }
}