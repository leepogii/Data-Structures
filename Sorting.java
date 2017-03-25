import java.util.Queue;
import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail shaker sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * When writing your sort, don't recheck already sorted items. The amount of
     * items you are comparing should decrease by 1 for each pass of the array
     * (in either direction). See the PDF for more info.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailShakerSort(T[] arr,
                                              Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        int index = 0;
        while (true) {
            boolean sorted = true;
            for (int i = index; i < arr.length - index - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    sorted = false;
                }
            }
            if (sorted) {
                return;
            }
            sorted = true;
            for (int j = arr.length - index - 2; j >= (index + 1); j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                    sorted = false;
                }
            }
            if (sorted) {
                return;
            }
            index++;
        }
    }


    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            T get = arr[i];
            while (j > 0) {
                if (comparator.compare(get, arr[j - 1]) < 0) {
                    swap(arr, j - 1, j);
                } else {
                    j = 0;
                }
                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        int i = 0;
        while (i < arr.length) {
            T min = arr[i];
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(min, arr[j]) > 0) {
                    min = arr[j];
                    index = j;
                }
            }
            swap(arr, i, index);
            i++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        partition(arr, 0, arr.length - 1, rand, comparator);

    }

    /**
     * Helper method for quickSort.
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comp the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param i low
     * @param j high
     */
    private static <T> void partition(T[] arr, int i, int j,
                                      Random rand, Comparator<T> comp) {
        int low = i;
        int high = j;
        if (low >= high) {
            return;
        }
        int pivotIndex = rand.nextInt(high - low) + low;
        if (j - i == 1) {
            if (comp.compare(arr[i], arr[j]) < 0) {
                return;
            } else {
                swap(arr, i, j);
                return;
            }
        }
        swap(arr, pivotIndex, high);
        j--;
        while (i < j) {
            while (i <= j
                    && comp.compare(arr[i], arr[high]) < 0) {
                i++;
            }
            while (i <= j
                    && comp.compare(arr[j], arr[high]) > 0) {
                j--;
            }
            if (i < j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, i, high);
        partition(arr, low, i - 1, rand, comp);
        partition(arr, i + 1, high, rand, comp);
    }

    /**
     * Helper method to swap two elements
     * @param arr array that contains the elements
     * @param i index of the first element
     * @param j index of the second element
     * @param <T> data type to sort
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        T[] temp = mergeH(arr, comparator);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
        }
    }

    /**
     * Helper method for mergeSort.
     * It makes the array separated.
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @return the completed array
     */
    private static <T> T[] mergeH(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return arr;
        }
        T[] left = (T[]) new Object[(arr.length / 2)];
        T[] right = (T[]) new Object[(arr.length - left.length)];
        T[] result;
        int middle = arr.length / 2;
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i < middle) {
                left[i] = arr[i];
            } else {
                right[j] = arr[i];
                j++;
            }
        }
        left = mergeH(left, comparator);
        right = mergeH(right, comparator);
        result = merge(left, right, comparator);
        return result;
    }

    /**
     * Helper method for mergeH.
     * It sorts and combines left and right array.
     * @param left left side of the array
     * @param right right side of the array
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     * @return the combined array
     */
    private static <T> T[] merge(T[] left, T[] right,
                                 Comparator<T> comparator) {

        T[] result = (T[]) new Object[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                result[k] = left[i];
                k++;
                i++;
            } else if (j < right.length
                    && comparator.compare(right[j], left[i]) < 0) {
                result[k] = right[j];
                k++;
                j++;
            }
        }
        if (i < left.length) {
            for (int x = i; x < left.length; x++) {
                result[k] = left[x];
                k++;
            }
        }
        if (j < right.length) {
            for (int x = j; x < right.length; x++) {
                result[k] = right[x];
                k++;
            }
        }
        return result;
    }


    /**
     * Implement radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * DO NOT USE {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use an ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] radixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        radixH(arr, 1);
        return arr;
    }

    /**
     * Helper mothod for radix.
     * @param arr the array to be sorted
     * @param exp exponent value
     */
    private static void radixH(int[] arr, int exp) {
        Queue[] q = new Queue[10];
        for (int i = 0; i < 10; i++) {
            q[i] = new LinkedList();
        }

        int mod = pow(10, exp);
        int div = mod / 10;
        int index;
        for (int anArr : arr) {
            index = Math.abs((anArr % mod) / div);
            q[index].add(anArr);
        }
        if (q[0].size() == arr.length) {
            return;
        }
        int count = 0;
        for (int i = 0; i < 10; i++) {
            while (!q[i].isEmpty()) {
                arr[count] = (int) q[i].remove();
                count++;
            }
        }
        radixH(arr, exp + 1);
    }


    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sort instead of {@code Math.pow()}. DO NOT MODIFY THIS METHOD.
     *
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power.
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Invalid exponent.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
