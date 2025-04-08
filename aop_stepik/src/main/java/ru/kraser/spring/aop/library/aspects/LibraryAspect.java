package ru.kraser.spring.aop.library.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect    // Чтобы заработало необходимо подцепить jar - https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
           // Project Structure -> Libraries -> + -> Apply
public class LibraryAspect {
                                         // Без указания класса Advice сработает на название метода из любого класса !!!
    @Before("execution(public void getBook())")
    public void beforeGettingTheBook() {
        System.out.println("From Aspect: Try getting the book...");
    }
                                         // Сработает только в классе SchoolLibrary.class
    @Before("execution(public void ru.kraser.spring.aop.library.SchoolLibrary.returnBook())")
    public void beforeReturningTheBook() {
        System.out.println("From Aspect: Try returning the book...");
    }
                                         // Сработает на все методы, начинающиеся с get... , только в классе UniLibrary.class
    @Before("execution(public void ru.kraser.spring.aop.library.UniLibrary.get*())")
    public void beforeGettingTheMagazine() {
        System.out.println("From Aspect: We visited the library. (get*())");
    }
                                         // Сработает на все методы, начинающиеся с returnType... , с любым модификатором доступа и любым типом возвращающегося значения
    @Before("execution(* ru.kraser.spring.aop.library.SchoolLibrary.returnType*())")
    public void beforeReturningTheBookNotVoid() {
        System.out.println("From Aspect: We go away from the library. (returnType**())");
    }
}
