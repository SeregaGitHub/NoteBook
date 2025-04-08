package ru.kraser.spring.aop.join_point;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BookPointcut {
    @Pointcut("execution(* addBook(..))")
    void allAddBookMethods() {}

    @Pointcut("execution(* addMagazine(..))")
    void allAddMagazineMethods() {}
}
