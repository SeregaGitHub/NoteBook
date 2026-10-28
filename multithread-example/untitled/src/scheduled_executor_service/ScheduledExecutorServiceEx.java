package study.scheduled_executor_service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceEx {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 5; i++) {
            //executorService.execute(new RunnableImpl200());
            executorService.schedule(
                    new RunnableImpl200(), 3, TimeUnit.SECONDS);  // Выполнить задание через 3 сек (сразу 5)
        }


        for (int i = 0; i < 5; i++) {
            executorService.scheduleAtFixedRate(
                    new RunnableImpl200(), 3, 2, TimeUnit.SECONDS);  // Выполнить задание через 3 сек (сразу 5)
        }                                                  // 2 сек - период между началом первого task и началом следующего task

        for (int i = 0; i < 5; i++) {
            executorService.scheduleWithFixedDelay(
                    new RunnableImpl200(), 3, 2, TimeUnit.SECONDS);  // Выполнить задание через 3 сек (сразу 5)
        }                                                  // 2 сек - период между окончанием первого task и началом следующего task

        Thread.sleep(20000);
        executorService.shutdown();
    }
}

class RunnableImpl200 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begin work");
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}