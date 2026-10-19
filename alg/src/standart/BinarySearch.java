package standart;

public class BinarySearch {
    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int result = biSearch(array, 0, array.length - 1, 3);
        System.out.println(result == -1 ? "No such element" : "Index = " + result);
    }

    private static int biSearch(int[] array, int l, int r, int n) {

        if (l == r && array[l] != n) return -1;

        //int middle = (l + r) / 2;
        int middle = l + (r - l) / 2;
        int result;

        if (array[middle] == n) {
            return middle;
        } else {
            if (array[middle] > n) {
                result = biSearch(array, l, middle, n);
            } else {
                result = biSearch(array, middle + 1, r, n);
            }
        }

        return result;
    }
}
