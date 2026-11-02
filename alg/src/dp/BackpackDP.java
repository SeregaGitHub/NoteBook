package dp;

import java.io.*;
import java.util.*;

public class BackpackDP {
/*
В первой строке вводится число предметов n и грузоподъемность рюкзака
Далее следуют описания предметов по одному в строке:
k - вес предмета, c - условная значимость
Найдите максимальную суммарную значимость предметов, которые можно взять с собой, не порвав рюкзак,
и укажите, как набрать эту значимость.

Выведите в первой строке - максимальную суммарную значимость предметов.
Во второй строке единственное число — сколько предметов надо взять.
Во третьей строке перечислите их номера (нумерация с единицы).
Если ответов несколько, то выведите любой.

Пример:

Ввод
4 6
2 7
4 2
1 5
2 1
         0  1  2  3  4  5  6
         0  0  0  0  0  0  0
    2/7  0  0  7  7  7  7  7
    4/2  0  0  7  7  7  7  9
    1/5  0  5  7  12 12 12 12
    2/1  0  5  7  12 12 13 13

Вывод
13
3
4 3 1

4 5
4 13200
2 5800
3 9150
2 6400

15550

*/
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int count = data[0];
            int maxWeight = data[1];

            Item[] items = createArray(reader, count);
            int[][] dp = new int[items.length][maxWeight + 1];

            int result = getResult(dp, items, maxWeight);
            writer.write(String.valueOf(result));
            writer.write("\n");

            List<Integer> resultList = getResultList(dp, items);
            writer.write(String.valueOf(resultList.size()));
            writer.write("\n");
            for (int i : resultList) {
                writer.write(String.valueOf(i));
                writer.write(" ");
            }
        }
    }

    private static int getResult(int[][] dp, Item[] items, int maxWeight) {

        for (int i = 1; i < items.length; i++) {
            for (int j = 1; j <= maxWeight; j++) {

                // index     - checking ArrayIndexOutOfBoundsException possibility !!!
                int index = Math.max(j - items[i].weight, 0);
                // itemPrice - checking that items[i].weight >= current max weight
                int itemPrice = j - items[i].weight < 0 ? 0 : items[i].price;

                dp[i][j] = Math.max(
                        dp[i - 1][j],
                        itemPrice + dp[i - 1][index]
                );
            }
        }

        return dp[dp.length - 1][maxWeight];
    }

    private static List<Integer> getResultList(int[][] dp, Item[] items) {

        List<Integer> list = new ArrayList<>();

        int i = dp.length - 1;
        int j = dp[0].length - 1;

        while (dp[i][j] != 0) {
            if (dp[i][j] != dp[i - 1][j]) {
                list.add(i);
                j -= items[i].weight;
            }
            i--;
        }

        return list;
    }

    private static Item[] createArray(BufferedReader reader, int count) throws IOException {

        Item[] items = new Item[count + 1];

        for (int i = 1; i <= count; i++) {
            int[] numbers = readNumbersToArray(reader);
            Item item = new Item(numbers[0], numbers[1]);
            items[i] = item;
        }

        return items;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static class Item {
        int weight;
        int price;

        Item (int weight, int price) {
            this.weight = weight;
            this.price = price;
        }
    }
}
