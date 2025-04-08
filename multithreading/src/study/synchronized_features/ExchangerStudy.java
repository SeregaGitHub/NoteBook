package study.synchronized_features;

/*
        Exchanger - синхронизатор, позволяющий обмениваться данными между двумя потоками.
                    Обеспечивает то, что оба потока получат информацию друг от друга - одновременно.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerStudy {
    // Камень - Ножницы - Бумага
    public static void main(String[] args) {
        Exchanger<Action> exchanger = new Exchanger<>();

        List<Action> myAct = new ArrayList<>();
        myAct.add(Action.STONE);
        myAct.add(Action.SCISSORS);
        myAct.add(Action.PAPER);

        List<Action> frAct = new ArrayList<>();
        frAct.add(Action.SCISSORS);
        frAct.add(Action.STONE);
        frAct.add(Action.STONE);

/*
        Serega WINS !!!
        Alex WINS !!!
        Serega WINS !!!
*/

        new BestFriend("Serega", myAct, exchanger);
        new BestFriend("Alex", frAct, exchanger);
    }
}

enum Action {
    STONE,
    SCISSORS,
    PAPER
}

class BestFriend extends Thread {
    String name;
    List<Action> actions;
    Exchanger<Action> exchanger;

    public BestFriend(String name, List<Action> actions, Exchanger<Action> exchanger) {
        this.name = name;
        this.actions = actions;
        this.exchanger = exchanger;

        this.start();
    }

    private void whoWins(Action myAction, Action friendAction) {
        if ((myAction == Action.STONE && friendAction == Action.SCISSORS)
        || (myAction == Action.SCISSORS && friendAction == Action.PAPER)
        || (myAction == Action.PAPER && friendAction == Action.STONE)) {
            System.out.println(name + " WINS !!!");
        }
    }

    @Override
    public void run() {
        Action reply;
        for (Action action: actions) {
            try {
                reply = exchanger.exchange(action);

                whoWins(action, reply);
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}