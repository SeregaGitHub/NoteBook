package ru.kraser.spring.aop.join_point;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Book {

    @Value("Treasure island")
    private String name;

    @Value("Robert Luis Stevenson")
    private String author;

    @Value("1883")
    private int yearsOfPublication;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearsOfPublication() {
        return yearsOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearsOfPublication=" + yearsOfPublication +
                '}';
    }
}
