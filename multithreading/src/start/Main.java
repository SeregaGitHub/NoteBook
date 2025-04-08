package start;

public class Main {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> System.out.println("Start MultiThreading !!!"));
        thread.start();

        System.out.println(thread.getName());
        thread.setName("Some_name");
        System.out.println(thread.getName());

        Thread threadJoin = new Thread(() -> System.out.println("DONE"));


        Runnable task1 = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 10; i <= 14; i++) {
                System.out.println(i);
            }
        };

        Thread sveta = new Thread(task1);
        Thread misha = new Thread(task2);
        sveta.start();
        misha.start();
    }
}
