package org.dc.sort;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {10, 7};
        quickSort(arr, 0, 1);

        for (int value : arr) {
            System.out.println(value);
        }
    }

    public static void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int po = partition(array, start, end);
            quickSort(array, start, po - 1);
            quickSort(array, po + 1, end);
        }
    }

    private static int partition(int[] array, int start, int end) {
        int base = array[start];
        int left = start;
        int right = end;

        while (left < right) {
            while (left < right && array[right] >= base) {
                right--;
            }

            while (left < right && array[left] <= base) {
                left++;
            }

            swap(array, left, right);
        }

        swap(array, start, right);

        return right;
    }

    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
