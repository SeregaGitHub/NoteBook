package ru.kraser.spring.aop.publishing_house;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Magazine {
    @Value("Maxim")
    private String magazineName;

    public String getMagazineName() {
        return magazineName;
    }
}
