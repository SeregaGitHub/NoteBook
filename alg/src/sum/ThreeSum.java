package sum;

import java.io.*;
import java.util.*;

public class ThreeSum {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

/*
10
6
4 8 1 1 5 5 16
*/

/*
            Что всё ещё можно улучшить (та же асимптотика O(n²), но быстрее на практике):

* HashSet<List<Integer>> дорог.
  Каждая тройка — это ArrayList + Collections.sort + хеширование + автоупаковка Integer.
  Дедупликацию лучше делать in-place пропуском дублей.

* Collections.sort(list) избыточен.
  Массив уже отсортирован, и i < left < right по индексам, поэтому значения уже в порядке:
  data[i] <= data[left] <= data[right]. Сортировать нечего.

* Дубликаты по i не пропускаются. Если data[i] == data[i-1], вся работа для i повторяется впустую.

* Нет отсечений. Можно рано выходить/пропускать итерации i:

* if (data[i] + data[i+1] + data[i+2] > sum) break; — дальше только больше.

* if (data[i] + data[n-2] + data[n-1] < sum) continue; — эта i слишком мала.
*/

            int sum = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());
            int[] data = readNumbersToArray(reader);

            Set<List<Integer>> result = getResult(data, sum);

            if (!result.isEmpty()) {
                for (List<Integer> list : result) {
                    writer.write(String.valueOf(list.getFirst()));
                    writer.write(" ");
                    writer.write(String.valueOf(list.get(1)));
                    writer.write(" ");
                    writer.write(String.valueOf(list.getLast()));
                    writer.write("\n");
                }
            } else {
                writer.write("NO");
            }
        }
    }

    private static List<List<Integer>> getTwoNumbers(int[] data, int target, int notCheckIndex) {

        List<List<Integer>> twoNumbers = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = data.length - 1;

        while (leftIndex < rightIndex) {

            if (leftIndex == notCheckIndex) {
                leftIndex++;
                continue;
            }
            if (rightIndex == notCheckIndex) {
                rightIndex--;
                continue;
            }

            int check = data[leftIndex] + data[rightIndex];

            if (check < target) {
                leftIndex++;
            } else if (check > target) {
                rightIndex--;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(data[leftIndex]);
                list.add(data[rightIndex]);
                twoNumbers.add(list);
                leftIndex++;
                rightIndex--;
            }
        }
        return twoNumbers;
    }

    private static Set<List<Integer>> getResult(int[] data, int sum) {

        Set<List<Integer>> result = new HashSet<>();
        Arrays.sort(data);


        for (int i = 0; i < data.length; i++) {
            List<List<Integer>> twoNumbers = getTwoNumbers(data, sum - data[i], i);
            if (!twoNumbers.isEmpty()) {
                for (List<Integer> list : twoNumbers) {
                    list.add(data[i]);
                    Collections.sort(list);
                    result.add(list);
                }
            }
        }
        return result;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
