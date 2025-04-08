package ru.kraser.spring.aop.join_point;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class BookAspect {

    @Before("ru.kraser.spring.aop.join_point.BookPointcut.allAddBookMethods() || ru.kraser.spring.aop.join_point.BookPointcut.allAddMagazineMethods()")
    void beforeAdd(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // Получаем сигнатуру метода (более информативно)
        Signature signature = joinPoint.getSignature();                               // Получаем сигнатуру метода
        Object[] args = joinPoint.getArgs();                                          // Получаем аргументы метода

        System.out.println("-----------------------methodSignature--------------------------");
        System.out.println("From BookAspect:");
        System.out.println(methodSignature.getName());
        System.out.println(methodSignature.getDeclaringType());
        System.out.println(methodSignature.getDeclaringTypeName());
        System.out.println(methodSignature.getModifiers());
        System.out.println("-----------------------methodSignature--------------------------");
        System.out.println(methodSignature.getMethod());
        System.out.println(methodSignature.getReturnType());
        System.out.println("-----------------------methodSignature--------------------------");

        System.out.println("-----------------------signature--------------------------");
        System.out.println("From BookAspect:");
        System.out.println(signature.getName());
        System.out.println(signature.getDeclaringType());
        System.out.println(signature.getDeclaringTypeName());
        System.out.println(signature.getModifiers());
        System.out.println("-----------------------signature--------------------------");

        System.out.println("-----------------------args--------------------------");
        Arrays.stream(args).forEach(System.out::println);
        System.out.println("-----------------------args--------------------------");

    }
}
