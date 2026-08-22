package priority_of_threads;

public class SimplePriority {
    public static void main(String[] args) {

        // Priority set from 1 to 10 !!!
        MyThreadSimplePriority thread1 = new MyThreadSimplePriority("A", 5, 1);
        MyThreadSimplePriority thread2 = new MyThreadSimplePriority("B", 5, 10);

                         // Do not work with Virtual Threads !!!
                         // Priority will be not Accurate !!!

        thread1.start(); // will be second (priority = 1)
        thread2.start(); // will be first (priority = 10)

        System.out.println("Common Thread - Finish");
    }
}

class MyThreadSimplePriority extends Thread {

    private final String name;
    private final Integer range;

    public MyThreadSimplePriority(String name, Integer range, Integer priority) {
        this.name = name;
        this.range = range;
        super.setPriority(priority);
    }

    @Override
    public void run() {
        int count = 0;
        System.out.println("MyThreadSimplePriority - Start");
        while (count < range) {
            System.out.println(name + " : " + ++count);
        }
        System.out.println("MyThreadSimplePriority - Finish");
    }
}