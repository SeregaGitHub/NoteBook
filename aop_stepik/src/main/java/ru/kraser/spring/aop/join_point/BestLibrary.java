package ru.kraser.spring.aop.join_point;

import org.springframework.stereotype.Component;

@Component
public class BestLibrary {

    public void addBook(String personName, Book book) {
        System.out.println("We added the book.");
        System.out.println("=====================================================");
    }

    public void addMagazine() {
        System.out.println("We added the magazine.");
        System.out.println("=====================================================");
    }
}
