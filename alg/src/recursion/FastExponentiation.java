package recursion;

import java.io.*;
import java.math.BigDecimal;

public class FastExponentiation {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            /*
Возводить в степень можно гораздо быстрее, чем за n умножений!
Для этого нужно воспользоваться следующими рекуррентными соотношениями:

a(в степени n)=(a(в степени 2) в степени n/2)  при четном n

a(в степени n)=a * a(в степени n - 1)  при нечетном n
             */

            double a = Double.parseDouble(reader.readLine());
            long n = Long.parseLong(reader.readLine());

            double doubleResult = pow(a, n);

            writer.write(
                    BigDecimal.valueOf(doubleResult)
                            .stripTrailingZeros()
                            .toPlainString()
            );
        }
    }

    private static double powRecursion(double a, long n) {

        if (n == 0) return 1;

        if (n % 2 == 0) {
            return powRecursion(Math.pow(a, 2), n / 2);
        } else {
            return a * powRecursion(a, n - 1);
        }
    }

    private static double pow(double a, long n) {

        if (n == 1) {
            return a;
        } else if (n == 0) {
            return 1;
        }

        return powRecursion(a, n);
    }
}
