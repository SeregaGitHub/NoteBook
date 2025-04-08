package ru.kraser.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("ru.kraser.spring.aop")
@EnableAspectJAutoProxy    // Позволяет использовать Spring AOP
public class Config {

}
