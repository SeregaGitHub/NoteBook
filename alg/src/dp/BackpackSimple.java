package dp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackpackSimple {
    public static void main(String[] args) throws IOException {
/*
В первой строке задано целое число M — грузоподъёмность рюкзака
Во второй строке дано количество куч с золотым песком
В каждой из следующих n строк описаны кучи, куча задаётся двумя целыми числами:
c - стоимость одного килограмма, k - килограммов золотого песка

Выведите единственное число – максимальную сумму, которую можно вынести в рюкзаке

10
3
8 1
2 10
4 5

36
 */
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int maxWeight = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());
            List<int[]> data = getData(reader, count);

            long result = getResult(data, maxWeight);
            writer.write(String.valueOf(result));
        }
    }

    private static long getResult(List<int[]> data, int maxWeight) {

        long result = 0;

        for (int[] arr : data) {
            if (maxWeight == 0) break;

            if (arr[1] <= maxWeight) {
                result += (long) arr[1] * arr[0];
                maxWeight -= arr[1];
            } else {
                result += (long) maxWeight * arr[0];
                break;
            }
        }

        return result;
    }

    private static List<int[]> getData(BufferedReader reader, int count) throws IOException {

        List<int[]> data = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            data.add(readNumbersToArray(reader));
        }

        data.sort((arr1, arr2) -> {
            return Integer.compare(arr2[0], arr1[0]);
        });

        return data;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
