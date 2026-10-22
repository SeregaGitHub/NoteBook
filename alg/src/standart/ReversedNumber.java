package standart;

import java.io.*;

public class ReversedNumber {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String str = reader.readLine();

            int result = reversNumber(str);
            writer.write(String.valueOf(result));
        }
    }

    private static Integer reversNumber(String str) {

        if (str == null || str.isBlank()) return null;

        int number = Integer.parseInt(str);
        int reversed = 0;

        while (number != 0) {
            reversed = (reversed * 10) + (number % 10);
            number = number / 10;
        }
        return reversed;
    }

    private static Integer reversNumberByMath(String str) {

        if (str == null || str.isBlank()) return null;

        int number = Integer.parseInt(str);
        int x = number < 0 ? -1 : 1;

        number = Math.abs(number);

        int result = 0;

        while (number > 0) {
            result = (result * 10) + (number % 10);
            number = number / 10;
        }

        return result * x;
    }

    private static Integer reversNumberByMathMiddle(String str) {

        if (str == null || str.isBlank()) return null;
        if (str.equals("0")) return 0;

        int index = str.length() - 1;
        int result = 0;
        int multi = 10;

        while (index >= 0) {

            //if (str.charAt(index) == '-') return (result - (result * 2));
            if (str.charAt(index) == '-') {
                result = -result;
                return result;
            }

            int checkInt = str.charAt(index) - '0';
            result = (result * multi) + checkInt;
            index--;
        }

        return result;
    }

    private static String reversNumberByString(String str) {

        if (str == null || str.isBlank()) return null;
        if (str.equals("0")) return "0";

        StringBuilder builder = new StringBuilder();

        int firstIndex = 0;
        if (str.charAt(0) == '-') {
            firstIndex = 1;
            builder.append('-');
        }

        boolean isCheckZero = true;

        for (int i = str.length() - 1; i >= firstIndex; i--) {
            if (isCheckZero && str.charAt(i) == '0') {
                continue;
            } else {
                isCheckZero = false;
                builder.append(str.charAt(i));
            }
        }

        return builder.toString();
    }
}
