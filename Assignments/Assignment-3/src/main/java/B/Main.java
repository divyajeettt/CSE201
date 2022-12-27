package B;

public class Main {
    private static void timeTree(int N, int threads) {
        long start = System.currentTimeMillis();
        BinaryTree tree = new BinaryTree(N, threads);
        long end = System.currentTimeMillis();

        System.out.println(tree);
        System.out.println("Time Taken: " + (end - start) + " ms");

        long startSearch = System.currentTimeMillis();
        // search for a non-existent value
        tree.search(-1);
        long endSearch = System.currentTimeMillis();
        System.out.println("Time Taken by unsuccessful search: " + (endSearch - startSearch) + " ms");
    }

    public static void main(String[] args) {
        int[] sizes = {10, 1000, 1000000};
        int[] threads = {0, 2, 4};

        for (int size : sizes) {
            for (int thread : threads) {
                timeTree(size, thread);
            }
            System.out.println();
        }
    }
}
