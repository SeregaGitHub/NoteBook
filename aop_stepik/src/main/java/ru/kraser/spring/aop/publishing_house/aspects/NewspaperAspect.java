package ru.kraser.spring.aop.publishing_house.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NewspaperAspect {

    @Before("execution(public void getNewspaper(String))")
    public void beforeGettingTheNewspaper() {
        System.out.println("From Aspect: Try getting the newspaper...");
    }

    // ---------- Можно вынести Pointcut в метод и переиспользовать ----------
    @Pointcut("execution(public void getNewspaper(..))")
    private void allGetNewspaper() {}

    @Before("allGetNewspaper()")
    public void securityAdvice() {
        System.out.println("From Aspect: Checking security...");
    }

    @Before("allGetNewspaper()")
    public void beforeGettingSomeNewspaper() {
        System.out.println("From Aspect: We visited \"Publishing House\"...");
    }
    // -----------------------------------------------------------------------

    @Before("execution(public void getMagazine(ru.kraser.spring.aop.publishing_house.Magazine))")
    public void beforeGettingTheMagazine() {
        System.out.println("From Aspect: Try getting the magazine...");
    }


}
