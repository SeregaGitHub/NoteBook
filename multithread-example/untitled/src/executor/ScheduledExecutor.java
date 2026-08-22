package executor;

import executor.integer_generator.GenerateRandomIntegerTask;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {
    public static void main(String[] args) {

        int processors = Runtime.getRuntime().availableProcessors();  // current processors quantity

        // ScheduledThreadPoolExecutor
        try (ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(processors)) {
            GenerateRandomIntegerTask task1 = new GenerateRandomIntegerTask();
            GenerateRandomIntegerTask task2 = new GenerateRandomIntegerTask();
            GenerateRandomIntegerTask task3 = new GenerateRandomIntegerTask();

            // Tasks will be start at 1, 5, and 10 seconds !!!
            executorService.schedule(task1, 1, TimeUnit.SECONDS);
            executorService.schedule(task2, 5, TimeUnit.SECONDS);
            executorService.schedule(task3, 10, TimeUnit.SECONDS);
        }
    }
}
