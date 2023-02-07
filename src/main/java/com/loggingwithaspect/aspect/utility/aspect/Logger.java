package com.loggingwithaspect.aspect.utility.aspect;

import com.loggingwithaspect.aspect.model.CustomLog;
import com.loggingwithaspect.aspect.repo.CustomLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class Logger {

    @Autowired
    private CustomLogRepository customLogRepository;

    @Around("execution(* com.loggingwithaspect.aspect.controller.*.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // Get method arguments
        Object[] args = joinPoint.getArgs();
        String request = Arrays.toString(args);

        // Get endpoint information
        String endpoint = signature.getMethod().getName();

        // Proceed with method execution
        Object result = joinPoint.proceed();

        // Get response
        String response = result.toString();

        // Save log information to the database
        CustomLog customLog = new CustomLog();
        customLog.setCreatedDate(now);
        customLog.setEndpoint(endpoint);
        customLog.setRequest(request);
        customLog.setResponse(response);
        customLogRepository.save(customLog);

        return result;
    }
}
