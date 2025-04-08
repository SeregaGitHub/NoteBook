import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();

        String[] result = new String[3];
        Arrays.fill(result, "");

        int input = scanner.nextInt();

        while (input != 0) {
            Scanner scanner1 = new Scanner(System.in);
            if (input == 1) {
                String in = scanner1.nextLine();
                result[1] = in;
            } else if (input == 2) {
                String in = scanner1.nextLine();
                result[0] = in;
            } else if (input == 3) {
                String in = scanner1.nextLine();
                result[2] = in;
            } else if (input == 4) {
                Arrays.fill(result, "");
            }  else if (input == 5) {
                boolean check = true;
                for (String s: result) {
                    if (s.isEmpty()) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    Arrays.stream(result).forEach(System.out::println);
                } else {
                    System.out.println("Необходимо ввести все данные для Вашего полного имени\n");
                }
            } else {
                System.out.println("Вы ввели некорректную команду.");
                System.out.println("Пожалуйста, попробуйте ещё раз.\n");
            }

            printMenu();

            input = scanner.nextInt();
        }
        System.out.println("ДО ВСТРЕЧИ !!!");
    }

    public static void printMenu() {
        System.out.println("Ваши действия ?");
        System.out.println("1 - Ввести Ваше имя");
        System.out.println("2 - Ввести Вашу фамилию");
        System.out.println("3 - Ввести Ваше отчество");
        System.out.println("4 - Сбросить Ваши вводы");
        System.out.println("5 - Вывести Ваше полное имя");
        System.out.println("0 - Выйти из приложения");
    }
}
