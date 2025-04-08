package ru.kraser.spring.aop.account_comb_pointcuts.aspects_order;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(10) // AccountAspect @Order(10), AccountComboAspect @Order(20): первым выполнится - AccountAspect
public class AccountAspect {

    @Pointcut("execution(* ru.kraser.spring.aop.account_comb_pointcuts.Account.get*())")
    void allGetMethodsFromAccount() {}

    @Pointcut("execution(* ru.kraser.spring.aop.account_comb_pointcuts.Account.delete*())")
    void allDeleteMethodsFromAccount() {}

    // Комбинированный Pointcut: и get, и return методы
    @Pointcut("allGetMethodsFromAccount() || allDeleteMethodsFromAccount()")
    void allGetAndDeleteMethodsFromAccount() {}

    @Before("allGetMethodsFromAccount()")
    public void beforeAllGetLoggingAdvice() {
        System.out.println("From Advice: logging GET method...");
    }

    @Before("allDeleteMethodsFromAccount()")
    public void beforeAllDeleteLoggingAdvice() {
        System.out.println("From Advice: logging DELETE method...");
    }
}
