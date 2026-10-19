package standart;

import java.io.*;
import java.util.Arrays;

public class LargeNumber {
    public static void main(String[] args) throws IOException {
        // Даны числа. Нужно определить, какое самое большое число можно из них составить.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            String[] data = readNumbersToArray(reader);

            Arrays.sort(data, (s1, s2) -> {
                return (s2 + s1).compareTo(s1 + s2);
            });

            for (String s : data) {
                writer.write(s);
            }
        }
    }

    private static String[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new String[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .toArray(String[]::new);
    }
}
