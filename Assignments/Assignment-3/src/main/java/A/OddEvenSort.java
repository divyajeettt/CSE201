package A;

public class OddEvenSort {
    private class SortingThread implements Runnable {
        private float[] array;
        private final int start, end;

        protected SortingThread(float[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            OddEvenSort.sort(array, start, end);
        }
    }

    private static void swap(float[] array, int i, int j) {
        float temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

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

    private static void merge(float[] array, int start, int mid, int end) {
        float[] temp = new float[end - start];
        int i = start, j = mid, k = 0;

        while (i < mid && j < end) {
            if (array[i] > array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < end) {
            temp[k++] = array[j++];
        }

        for (i = start; i < end; i++) {
            array[i] = temp[i - start];
        }
    }

    public static void sort(float[] array) {
        sort(array, 0, array.length);
    }

    public static void sortThreaded(float[] array) {
        Thread t1 = new Thread(new OddEvenSort().new SortingThread(array, 0, array.length / 2));
        Thread t2 = new Thread(new OddEvenSort().new SortingThread(array, array.length / 2, array.length));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        merge(array, 0, array.length / 2, array.length);
    }

    public static boolean checkSorted(float[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1]) return false;
        }
        return true;
    }
}
