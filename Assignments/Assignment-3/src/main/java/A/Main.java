package A;

public class Main {
    private static float[] generateCGPAs(int n) {
        float[] CGPAs = new float[n];
        for (int i = 0; i < n; i++)
            CGPAs[i] = (float) Math.random() * 10f;
        return CGPAs;
    }

    private static void shuffle(float[] array) {
        for (int i = 0; i < array.length; i++) {
            int j = (int) (Math.random() * array.length);
            float temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static void timeSorting(float[] CGPAs, boolean threaded) {
        long start = System.currentTimeMillis();
        if (threaded)
            OddEvenSort.sortThreaded(CGPAs);
        else
            OddEvenSort.sort(CGPAs);
        long end = System.currentTimeMillis();

        if (!OddEvenSort.checkSorted(CGPAs)) {
            System.out.println("Sorting Failed!");
            return;
        }

        String type = threaded ? "with" : "without";
        System.out.println("Time taken (" + type + " Parallelization): " + (end - start) + " ms");
    }

    public static void main(String[] args) {
        int[] sizes = {1, 10, 100, 1000, 10000};
        for (int n : sizes) {
            float[] CGPAs = generateCGPAs(n);
            System.out.println("Array Size: " + n);
            Main.timeSorting(CGPAs, false);
            shuffle(CGPAs);
            Main.timeSorting(CGPAs, true);
            System.out.println();
        }
    }
}
