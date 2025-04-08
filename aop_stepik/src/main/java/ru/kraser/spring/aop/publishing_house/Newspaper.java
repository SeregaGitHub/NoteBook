package ru.kraser.spring.aop.publishing_house;

import org.springframework.stereotype.Component;

@Component("newspaperBean")
public class Newspaper {

    public void getNewspaper(String name) {
        System.out.println("We getting newspaper - " + name);
    }

    public void getNewspaper() {
        System.out.println("We getting some newspaper");
    }

    public void getMagazine(Magazine magazine) {
        System.out.println("We getting magazine - " + magazine.getMagazineName());
    }
}
