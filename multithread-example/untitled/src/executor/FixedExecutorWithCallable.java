package executor;

import executor.integer_generator.GenerateRandomIntegerCallableTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedExecutorWithCallable {
    public static void main(String[] args) {

        int processors = Runtime.getRuntime().availableProcessors();
        List<Future<Integer>> futures = new ArrayList<>();

        try (ExecutorService executorService = Executors.newFixedThreadPool(processors - 1)) {
            for (int i = 0; i < 1000; i++) {

                GenerateRandomIntegerCallableTask task = new GenerateRandomIntegerCallableTask();
                Future<Integer> future = executorService.submit(task);
                futures.add(future);
            }
        }

        futures.forEach(integerFuture -> {
            try {
                System.out.println(integerFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
