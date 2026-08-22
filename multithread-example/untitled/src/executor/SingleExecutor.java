package executor;

import executor.integer_generator.GenerateRandomIntegerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleExecutor {
    public static void main(String[] args) {

        // SingleThreadExecutor
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            for (int i = 0; i < 50; i++) {
                executorService.submit(new GenerateRandomIntegerTask());
            }
        }
    }
}