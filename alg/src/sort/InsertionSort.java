package sort;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(4);
        list.add(6);
        list.add(1);
        list.add(3);

        // 5 3 1 6 4 2
        list.forEach(integer -> System.out.print(integer + " "));
        System.out.println();
        insertionSort(list);
        list.forEach(integer -> System.out.print(integer + " "));
    }

    public static void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            int checkInt = list.get(i);
            int j = i;
            while (j > 0 && checkInt < list.get(j - 1)) {
                list.set(j, list.get(j - 1));
                j--;
            }
            list.set(j, checkInt);
        }
    }
}
