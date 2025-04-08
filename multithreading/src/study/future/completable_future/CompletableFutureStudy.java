package study.future.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureStudy {
    public static void main(String[] args) {
/*
          Класс CompletableFuture , который появился в Java 1.8, расширяет функциональность обычного Future.
        Он обладает теми же методами, но с нововведениями:

    - возможность создавать задачи в как стандартном пуле потоков fjp.commonPool, так и в пользовательском;
    - обработка ошибок;
    - обработка результатов задачи в функциональном стиле;
    - большое количество методов для разных сценариев.

      Для запуска задачи больше не нужно создавать отдельный пул:
    CompletableFuture использует пул потоков ForkJoinPool#commonPool.

      Для создания задач есть два метода: runAsync и supplyAsync.
    Первый использует интерфейс - Runnable,
    второй — Supplier.
*/

        // Задача Runnable выполняется в CommonPool
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> System.out.println("Задача завершена!"));

        // Задача Supplier выполняется в CommonPool
        CompletableFuture<String> stringFuture = CompletableFuture.supplyAsync(() -> "Задача завершена!");

        // Задача Supplier выполняется в ThreadPoolExecutor
        CompletableFuture.supplyAsync(() -> "Задача завершена!", Executors.newSingleThreadExecutor());

        // С помощью метода thenAccept можно асинхронно обработать результат задачи:
        CompletableFuture
                .supplyAsync(() -> "Задача завершена!")
                .thenAccept(System.out::println); // Задача завершена!

        // А с помощью метода thenApply можно преобразовать результат задачи в новый CompletableFuture :
        CompletableFuture
                .supplyAsync(() -> "Задача завершена!")
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println); // задача завершена!



    }

}

class User {
    private int id;

    public void refreshUserAndNotify(Long id) {
        CompletableFuture
                .supplyAsync(() -> fetchUser(id))  // Получение данных
                .thenApply(this::updateUser)       // Обновление данных
                .thenAccept(this::sendEmail);      // И отправка уведомления
    }

    private static User fetchUser(Long id) {
        return null;
    }

    private User updateUser(User user) {
        return user;
    }

    private void sendEmail(Object o) {
    }

    private void sendEmail() {
    }
}
