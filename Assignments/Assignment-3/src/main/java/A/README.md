# Problem A

This problem requires us to sort an Array of CGPAs in descending order using Odd-Even Sort. The program must sort the array with and without parallelization and compare the time taken for each.

## Using Multithreading for Sorting

For parallelization, creating one thread for each comparison is not a good idea. This is because creating and running each thread will take more time than the usual compare and swap technique used by the sorting algorithm.

A better approach is to use two threads, one for each half of the array. This way, the array is divided into two halves and each half is sorted by a separate thread. The two sorted halves are then merged into a single sorted array using the sorted-merge technique.

## Sorting Technique

The following technique of Odd-Even Sort has been used. This algorithm only sorts the slice `array[start:end]` of the array.

```java
private static void sort(float[] array, int start, int end) {
    boolean sorted = false;
    while (!sorted) {
        sorted = true;
        for (int i = start + 1; i >= start; i--) {
            for (int j = i + 1; j < end; j++) {
                if (array[j - 1] < array[j]) {
                    swap(array, j - 1, j);
                    sorted = false;
                }
            }
        }
    }
}
```

## Compilation

To compile, run:

```bash
mvn compile
```
