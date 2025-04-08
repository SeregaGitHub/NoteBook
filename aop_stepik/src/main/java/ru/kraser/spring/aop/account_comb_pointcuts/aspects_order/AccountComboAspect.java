package ru.kraser.spring.aop.account_comb_pointcuts.aspects_order;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(20) // AccountAspect @Order(10), AccountComboAspect @Order(20): первым выполнится - AccountAspect
public class AccountComboAspect {

    @Before("ru.kraser.spring.aop.account_comb_pointcuts.aspects.AccountAspect.allGetAndDeleteMethodsFromAccount()")
    void beforeAllGetAndDeleteLoggingAdvice() {
        System.out.println("From Advice: logging GET and DELETE method...");
    }
}
