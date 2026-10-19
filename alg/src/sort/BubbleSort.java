package sort;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {

        int[] data = {5, 3, 1, 6, 4, 2};

        // 5 3 1 6 4 2
        Arrays.stream(data).forEach(integer -> System.out.print(integer + " "));
        System.out.println();
        bubbleSort(data);
        Arrays.stream(data).forEach(integer -> System.out.print(integer + " "));

    }

    private static void bubbleSort(int[] data) {

        boolean isNeedSort = true;
        int lastIndexCorrection = 0;
        while (isNeedSort) {
            isNeedSort = false;
            // with out "- lastIndexCorrection" will be needless iterations
            // for (int i = 0; i < data.length - 1; i++) {
            for (int i = 0; i < data.length - 1 - lastIndexCorrection; i++) {
                if (data[i] > data[i + 1]) {
                    swap(data, i, i + 1);
                    isNeedSort = true;
                }
            }
            lastIndexCorrection++;
        }
    }

    private static void swap(int[] data, int l, int r) {
        int buffer = data[l];
        data[l] = data[r];
        data[r] = buffer;
    }
}
