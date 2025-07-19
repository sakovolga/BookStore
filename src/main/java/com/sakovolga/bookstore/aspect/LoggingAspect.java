package com.sakovolga.bookstore.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.sakovolga.bookstore.controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(public * com.sakovolga.bookstore.service.*.*(..))")
    public void serviceLog(){}

    @Before("controllerLog()")
    public void doBeforeController(JoinPoint jp){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null){
            request = attributes.getRequest();
        }
        if(request != null){
            log.info("NEW REQUEST: IP: {}, URL: {}, HTTP_METHOD: {}, CONTROLLER_METHOD: {}.{}",
                    request.getRemoteAddr(),
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    jp.getSignature().getDeclaringTypeName(),
                    jp.getSignature().getName());
        }
    }

    @Before("serviceLog()")
    public void doBeforeService(JoinPoint jp){
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        String argsString = args.length > 0 ? Arrays.toString(args) : "METHOD HAS NO ARGUMENTS";

        log.info("RUN SERVICE: SERVICE_METHOD: {}.{}. METHOD ARGUMENTS: [{}]",
                className, methodName, argsString);
    }

    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
    public void doAfterReturning(Object returnObject){
        log.info("Return value: {}", returnObject);
    }

    @After("controllerLog()")
    public void doAfter(JoinPoint jp){
        log.info("Controller Method executed successfully: {}.{}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName());
    }

    @Around("controllerLog()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        log.info("Execution method: {}.{}. Execution time: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                executionTime);

        return proceed;

    }


    @AfterThrowing(throwing = "ex", pointcut = "controllerLog()")
    public void throwsException(JoinPoint jp, Exception ex){
        String methodName = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getSimpleName();

        log.error("Exception in {}.{} with arguments {}. Exception message: {}",
                className, methodName, Arrays.toString(jp.getArgs()), ex.getMessage());
    }

//    @Pointcut("execution(public * com.sakovolga.bookstore.kafka.KafkaProducer.send(..))")
//    public void kafkaSend(){}
//
//    @Around("kafkaSend()")
//    public Object logKafkaSend(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        String topic = args[0].toString();
//        Object message = args[1];
//
//        log.info("Sending message: {} to kafka topic {}",
//                message, topic);
//
//        return joinPoint.proceed();
//    }
//
//    @Pointcut("execution(public * com.sakovolga.bookstore.kafka.KafkaListener(..))")
//    public void kafkaListener(){}
//
//    @Before("kafkaListener()")
//    public void logKafkaListener(JoinPoint jp){
//        Object[] args = jp.getArgs();
//        Object message = args[0];
//
//        log.info("Received message from kafka: {}", message);
//    }




}
