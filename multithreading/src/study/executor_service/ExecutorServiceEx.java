package study.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceEx {
    public static void main(String[] args) throws InterruptedException {
        // ExecutorService executorService = new ThreadPoolExecutor();               // Редкий способ

        ExecutorService executorServiceSingle = Executors.newSingleThreadExecutor(); // Один поток

        ExecutorService executorServiceCached = Executors.newCachedThreadPool();     // Заранее неизвестное количество потоков

        ExecutorService executorService = Executors.newFixedThreadPool(5);  // 5 потоков

        for (int i = 0; i < 10; i++) {
            executorService.execute(new RunnableImpl100());
        }

        executorService.shutdown();    // окончание работы ExecutorService
                                       // awaitTermination() - выполняется после shutdown()
        executorService.awaitTermination(3, TimeUnit.SECONDS); //  поток (main) в котором вызван метод закончится через 3 сек
                                                                      //  либо когда закончит работу ExecutorService

        System.out.println("Main end.");
/*
        pool-1-thread-1
        pool-1-thread-4
        Main end.
        pool-1-thread-2
        pool-1-thread-3
        pool-1-thread-5
        pool-1-thread-1
        pool-1-thread-4
        pool-1-thread-2
        pool-1-thread-3
        pool-1-thread-5
*/
    }
}

class RunnableImpl100 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}