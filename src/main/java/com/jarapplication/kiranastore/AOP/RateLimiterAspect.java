package com.jarapplication.kiranastore.AOP;

import com.jarapplication.kiranastore.AOP.annotation.RateLimiter;
import com.jarapplication.kiranastore.exception.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;


@Aspect
@Component
public class RateLimiterAspect {

    private HashMap<Method, Bucket> rateLimiterHashMap = new HashMap<>();
    /**
     * Executes the function when annotated
     * @param joinPoint
     * @throws IllegalAccessException
     */
    @Around("@annotation(com.jarapplication.kiranastore.AOP.annotation.RateLimiter)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // Get the annotation
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);

        int limit = rateLimiter.limit();

        rateLimiterHashMap.putIfAbsent(method ,Bucket.builder()
                .addLimit(Bandwidth.classic(limit, Refill.greedy(limit, Duration.ofMinutes(1))))
                .build());
        Bucket bucket = rateLimiterHashMap.get(method);
                System.out.println(bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();  // Proceed with the method execution
        } else {
            throw new RateLimitExceededException("Too many requests. Please try again later.");
        }
    }
}