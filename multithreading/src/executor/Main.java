package executor;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        Заворачиваем задачу в интерфейс Callable
//        Отправляем в экзекьютор
//        Получаем объект Future для отслеживания результата

        ExecutorService executor = Executors.newCachedThreadPool();

        Callable<Integer> c = () -> {
            return 12*15;
        };

        Future<Integer> res = executor.submit(c); // отправляем задачу в экзекьютор
        Integer value = res.get();                // забираем результат вычислений

        // С помощью объекта Future мы можем проверить, завершилась задача или нет. Если нет, занять поток чем-то ещё:
        Future<Integer> result = executor.submit(c);
        while (!result.isDone()) {
            // какой-то другой код
        }
        Integer val = result.get();
    }
}
