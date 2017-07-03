package com.whatistest.zbjaspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by rubyvirusqq@gmail.com on 15/05/17.
 * <p>
 * 加载全局配置类
 * 所有类、方法含有注解com.whatistest.annotation.ZBJTestConfig的，都会被加载处理
 */
@Aspect
@Component
public class ZBJAspect {

    @Pointcut(value = "execution(* (@com.whatistest.testng.annotation.ZBJTestDataProvider *).*(..))")
    void GlobalConfig() {

    }

    @Around(value = "GlobalConfig()")
    Object aroundAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object obj = null;
        proceedingJoinPoint.proceed();
        System.out.println("我是代理我是代理");
        return obj;
    }


}
