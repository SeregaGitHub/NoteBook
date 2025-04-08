package ru.kraser.spring.aop.library;

import org.springframework.stereotype.Component;

@Component("uniLibraryBean")
public class UniLibrary extends AbstractLibrary {

    @Override
    public void getBook() {
        System.out.println("We get the book from UniLibrary");
    }

    @Override
    public void returnBook() {
        System.out.println("We return book to UniLibrary");
    }

    public void getMagazine() {
        System.out.println("We get the magazine from UniLibrary");
    }
}
