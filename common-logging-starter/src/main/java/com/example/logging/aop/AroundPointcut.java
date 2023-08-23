package com.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AroundPointcut {

    @Around(value = "com.example.logging.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)", argNames = "joinPoint,service,id")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("AROUND before - invoked findById method in class {}, with id {}", service, id);
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findById method in class {}, with result {}", service, result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked findById method in class {}, with exception {}: {}", service, ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.info("AROUND after (finally) - invoked findById method in class {}", service);
        }
    }

}
