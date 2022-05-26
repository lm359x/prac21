package com.example.prac21;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {

    private long date;

    @Before("allServiceMethods()")
    public void startTime(JoinPoint joinPoint) {
        date = System.currentTimeMillis();
    }

    @After("allServiceMethods()")
    public void logTime(JoinPoint joinPoint){
        log.info("{} executed in {}", joinPoint.toString(),(double) (System.currentTimeMillis()-date));
    }
    @Pointcut("within(com.example.prac21.services.*)")
    public void allServiceMethods() {}
}
