package executor;

import executor.integer_generator.GenerateRandomIntegerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedExecutor {
    public static void main(String[] args) {

        // CachedThreadExecutor
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 50; i++) {
                executorService.submit(new GenerateRandomIntegerTask());
            }
        }
    }
}
