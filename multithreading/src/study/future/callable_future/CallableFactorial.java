package study.future.callable_future;

import java.util.concurrent.*;

public class CallableFactorial {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Factorial factorial = new Factorial(5);

     // Future - получает результат executorService.submit(Callable)
        // Callable (в отличие от Runnable) возвращает значение
        Future<Integer> future = executorService.submit(factorial);
        executorService.shutdown();

        try {
            System.out.println(future.isDone());

            System.out.println("Want to get result...");
            // Пока Task не завершит работу - результат не будет записан во Future
            System.out.println(future.get());
            System.out.println("The result was got now.");

            System.out.println(future.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
        } finally {
            executorService.shutdown();
        }
    }
}

class Factorial implements Callable<Integer> {
    int f;

    public Factorial(int f) {
        this.f = f;
    }

    @Override
    public Integer call() throws InterruptedException {
        if (f <= 0) {
            throw new RuntimeException("Wrong number !!!");
        }

        int result = 1;
        for (int i = 1; i <= f; i++) {
            result *= i;
            Thread.sleep(1000);
        }
        return result;
    }
}