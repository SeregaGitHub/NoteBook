package ru.kraser.spring.aop.library;

import org.springframework.stereotype.Component;

@Component("schoolLibraryBean")
public class SchoolLibrary extends AbstractLibrary {

    @Override
    public void getBook() {
        System.out.println("We get the book from SchoolLibrary");
    }

    @Override
    public void returnBook() {
        System.out.println("We return book to SchoolLibrary");
    }

    public String returnTypeStringBook() {
        return "We return string book";
    }

    protected Integer returnTypeIntBook() {
        return 1;
    }
}
