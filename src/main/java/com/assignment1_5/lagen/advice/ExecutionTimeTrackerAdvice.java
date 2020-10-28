package com.assignment1_5.lagen.advice;

import com.assignment1_5.lagen.helper.RequestHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

    Logger logger = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);

    @Pointcut("execution(* com.assignment1_5.lagen.presentation.controller.*.*(..))")
    private void controllerMethods() {}

    @Pointcut("execution(* com.assignment1_5.lagen.dataAcces.repository.*.*(..))")
    private void repositoryMethods() {}

    @Around("controllerMethods()")
    public Object logControllerMethodDurations(ProceedingJoinPoint pjp) throws Throwable {
        return trackMethodExecutionTime(pjp);
    }

    public Object trackMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Method name " + pjp.getSignature() + " time taken to execute " + (endTime - startTime));
        return result;
    }

    @Around("repositoryMethods()")
    public Object trackRepositoryMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        final String header = RequestHelper.getCurrentRequest().getHeader("X-Trace-Database-Time");
        if (header == null) {
            return  pjp.proceed();
        }
        return trackMethodExecutionTime(pjp);
    }

}
