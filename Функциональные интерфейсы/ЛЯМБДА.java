
                                   ЛЯМБДА

    Сортировка по длине строк

String[] array = {"aaa", "bbbbbbbbb", "cccccc"};

Arrays.sort(array, (a, b) -> b.length() - a.length());
System.out.println(Arrays.toString(array));                    //  [bbbbbbbbb, cccccc, aaa]

Arrays.sort(array, Comparator.comparingInt(String::length));
System.out.println(Arrays.toString(array));                    //  [aaa, cccccc, bbbbbbbbb]

//    [aaa, cccccc, bbbbbbbbb]

Сортировка массива по длине строк

String[] array1 = {"ccc", "bb", "a"};
String[] array2 = {"abc", "abcabcabc", "abcabc"};
String[] array3 = {"abc", "cba", "bca"};
List<String[]> list = new ArrayList<>();
list.add(array1);
list.add(array2);
list.add(array3);
for (String[] s: list
) {
Arrays.sort(s);
Arrays.sort(s, (a,b) -> a.length() - b.length());
System.out.println(Arrays.toString(s));
}

//    [a, bb, ccc]
//    [abc, abcabc, abcabcabc]
//    [abc, bca, cba]
======================================================================================================

interface DefInterf {
    int myMethod(String str);                                        // 1)
}

interface DefInerf_2 {
    int myMethod_2(String str, int i);
}

interface DefInterf_3 {
    String myMethod_3();
}

public class Example

    public static void def(String string, DefInterf defInterf) {     // 2)
        System.out.println(defInterf.myMethod(string));
    }

    public static void def2(String string, int i, DefInerf_2 defInerf_2) {
        System.out.println(defInerf_2.myMethod_2(string, i));
    }

    public static void def3(DefInterf_3 defInterf_3) {
        System.out.println(defInterf_3.myMethod_3());
    }

    public static void main(String[] args) {

        String words = "Hello Serega !!!";
        def(words, (String str) -> {return str.length();});          // 3)
        def(words, (String str) -> str.length());                    // - то же самое.
        def(words, str -> str.length());                             // - то же самое.
        def(words, String::length);                                  // - то же самое.

        DefInterf defInterf = (String str) -> str.length();          // - можно вынести лямбду в переменную
        def(words, defInterf);

        // Если тело метода состоит более чем из одного параметра: {} и return - ОБЯЗАТЕЛЬНО !!!
        def(words, str -> {System.out.print("Длина словосочетания: "); return str.length();});

/*
        1) Есть Функциональный интерфейс, который принимает строку и возвращает число.
        2) Есть метод, который принимает строку и объект функционального интерфейса.
        3) Вызываем метод (2), который принимает строку (words) и далее объект ФИ - Лямбда:
           в скобках параметр метода ФИ (String s) -> {return действия с str, чтобы возвратить число}
                                          ИЛИ
           название переменной ФИ, ->, действие с переменной (тело метода)
 */
        int x = 2;
        def2(words, x, (str, i) -> str.length() / x);                // - если два параметра, то они в скобках

        if (x > 0) {
            def3( () -> "It's nice !!!");                            // - если метод ФИ без параметров, то () ->
        }
    }
}

 ======================================================================================================
 public static void main(String[] args) {
     /*Predicate<List<Integer>> sortedPredicate1 = new Predicate<List<Integer>>() {
         @Override
         public boolean test(List<Integer> list) {
             if (list.size() <= 1) return true;
             for (int i = 1; i < list.size(); i++) {
                 if (list.get(i) < list.get(i - 1)) return false;
             }
             return true;
         }
     };*/
                                       // SAME !!!

     Predicate<List<Integer>> sortedPredicate = list -> {if (list.size() <= 1) return true; for (int i = 1; i < list.size(); i++) {
         if (list.get(i) < list.get(i - 1)) return false;
     }
         return true;
     };

     List<Integer> list = List.of(4, 8, 15, 16, 23, 32);
     System.out.println("Отсортирован?");
     //System.out.println(sortedPredicate1.test(list));
     System.out.println(sortedPredicate.test(list));

 }
}
