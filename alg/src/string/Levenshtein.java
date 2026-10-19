package string;

import java.io.*;
import java.util.Arrays;

public class Levenshtein {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String str1 = reader.readLine();
            String str2 = reader.readLine();

            writer.write(String.valueOf(levenshteinDistance(str1, str2)));
        }
    }

    private static int levenshteinDistance(String str1, String str2) {
        int[] prevValues = fillArray(str1.length() + 1);
        int[] currValues = new int[str1.length() + 1];

        for (int i = 1; i <= str2.length(); i++) {
            currValues[0] = i;
            for (int j = 1; j <= str1.length(); j++) {
                if (str2.charAt(i - 1) == str1.charAt(j - 1)) {
                    currValues[j] = Math.min(prevValues[j - 1], Math.min(prevValues[j] + 1, currValues[j - 1] + 1));
                } else {
                    currValues[j] = Math.min(prevValues[j - 1] + 1, Math.min(prevValues[j] + 1, currValues[j - 1] + 1));
                }
            }
            prevValues = Arrays.copyOf(currValues, prevValues.length);
        }

        return prevValues[currValues.length - 1];
    }

    private static int[] fillArray(Integer arrayLength) {
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = i;
        }
        return array;
    }
}
