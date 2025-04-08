package ru.kraser.spring.aop.publishing_house;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kraser.spring.aop.Config;

public class PublishingHouse {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Newspaper newspaper = context.getBean("newspaperBean", Newspaper.class);
        newspaper.getNewspaper("Times");
        newspaper.getNewspaper();

        Magazine magazine = context.getBean("magazine", Magazine.class);
        newspaper.getMagazine(magazine);

        context.close();
    }
}
