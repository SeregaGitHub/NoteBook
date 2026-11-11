package string;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KnuthMorrisPratt {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

//          String str = "aabaabaaaaaabaabaabaabbaaab";
//          String pattern = "aabaab";

            String str = reader.readLine();
            String pattern = reader.readLine();

            //  0 10 13 16
            List<Integer> result = knuthMorrisPratt(str, pattern);

            for (int i : result) {
                writer.write(String.valueOf(i));
                writer.write(" ");
            }
        }
    }

    /*private static List<Integer> knutMorrisPrut(String str, String pattern) {

        List<Integer> result = new ArrayList<>();
        int[] pf = prefixFunction(pattern);

        int p = 0;
        for (int s = 0; s < str.length();) {
            if (pattern.charAt(p) == str.charAt(s)) {
                p++;
                s++;
            }

            if (p == pattern.length()) {
                result.add(s - p);
                p = pf[p - 1];
            } else if (s < str.length() && pattern.charAt(p) != str.charAt(s)) {
                if (p != 0) {
                    p = pf[p - 1];
                } else {
                    s = s + 1;
                }
            }
        }
        return result;
    }*/

    private static List<Integer> knuthMorrisPratt(String str, String pattern) {

        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty()) return result;

        int[] pf = prefixFunction(pattern);

        int s = 0;
        int p = 0;

        while (s < str.length()) {
            if (pattern.charAt(p) == str.charAt(s)) {
                s++;
                p++;
                if (p == pattern.length()) {
                    result.add(s - p);
                    p = pf[p - 1];
                }
            } else if (p > 0) {
                p = pf[p - 1];
            } else {
                s++;
            }
        }
        return result;
    }

    private static int[] prefixFunction(String str) {
        int[] pf = new int[str.length()];
        for (int i = 1; i < str.length(); i++) {
            int k = pf[i-1];
            while (k > 0 && str.charAt(k) != str.charAt(i)) {
                k = pf[k-1];
            }
            if (str.charAt(k) == str.charAt(i)) {
                k++;
            }
            pf[i] = k;
        }
        return pf;
    }
}
