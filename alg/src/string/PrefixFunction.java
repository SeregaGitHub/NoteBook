package string;

import java.io.*;
import java.util.Arrays;

public class PrefixFunction {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String str = reader.readLine();

            int[] pi = prefixFunction(str);

            Arrays.stream(pi).forEach(i -> {
                try {
                    writer.write(i + " ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static int[] prefixFunction(String str) {
        int n = str.length();
        int[] pi = new int[n];
        pi[0] = 0;
        for (int i = 1; i < n; i++) {
            int k = pi[i-1];
            while (k > 0 && str.charAt(k) != str.charAt(i)) {
                k = pi[k-1];
            }
            if (str.charAt(k) == str.charAt(i)) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }
}
