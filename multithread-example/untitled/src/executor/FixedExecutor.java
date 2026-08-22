package executor;

import executor.integer_generator.GenerateRandomIntegerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedExecutor {
    public static void main(String[] args) {

        int processors = Runtime.getRuntime().availableProcessors();  // current processors quantity

        // FixedThreadPool  -  will be faster than SingleThreadExecutor !!!
        try (ExecutorService executorService = Executors.newFixedThreadPool(processors - 1)) {
            for (int i = 0; i < 50; i++) {
                executorService.submit(new GenerateRandomIntegerTask());
            }
        }
    }
}
