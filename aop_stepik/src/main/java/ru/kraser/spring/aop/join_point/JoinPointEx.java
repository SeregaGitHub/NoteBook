package ru.kraser.spring.aop.join_point;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kraser.spring.aop.Config;

public class JoinPointEx {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        BestLibrary library = context.getBean("bestLibrary", BestLibrary.class);
        Book book = context.getBean("book", Book.class);

        library.addBook("Serega", book);
        library.addMagazine();

        context.close();
    }
}
