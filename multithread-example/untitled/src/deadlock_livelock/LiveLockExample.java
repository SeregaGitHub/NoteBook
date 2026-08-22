package deadlock_livelock;

public class LiveLockExample {
    public static void main(String[] args) throws InterruptedException {

        // If bmv.priority = audi.priority  =>  it will be LiveLock !!!
        Car bmv = new Car("BMV", true, 10);
        Car audi = new Car("AUDI", true, 10);
        // If bmv.priority != audi.priority  =>  program will be finished !!!

        new Thread(() -> bmv.checkRoad(audi)).start();
        new Thread(() -> audi.checkRoad(bmv)).start();

    }
}

class Car {
    private final String name;
    private boolean needRoad;
    private int priority;

    public Car(String name, boolean needRoad, int priority) {
        this.name = name;
        this.needRoad = needRoad;
        this.priority = priority;
    }

    void checkRoad(Car someCar) {
        while (needRoad) {
            if (someCar.needRoad) {
                System.out.println(name + " need empty road !!!");

                if (priority > someCar.priority) {
                    needRoad = false;
                    System.out.println(name + " go !!!");
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else {
                needRoad = false;
                System.out.println(name + " go !!!");
            }
        }
    }
}

