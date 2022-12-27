package B;

import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;

public class BinaryTree {
    private Node root = null;
    private final int N;
    private int threads;

    private class Node {
        private final int value;
        private Node left = null;
        private Node right = null;

        protected Node() {
            Random random = new Random();
            this.value = random.nextInt((int) 10e9);
        }
    }

    public BinaryTree(int N, int threads) {
        this.N = N;
        this.root = new Node();
        this.threads = threads;
        if (threads == 0) unthreaded(root, N);
        else threaded(root, N, threads);
    }

    @Override
    public String toString() {
        return "BinaryTree(N=" + N + ", threads=" + threads + ")";
    }

    private void unthreaded(Node root, int N) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        for (int i = 1; i < N; i++) {
            Node node = new Node();
            Node parent = queue.peek();
            if (parent.left == null) parent.left = node;
            else {
                parent.right = node;
                queue.remove();
            }
            queue.add(node);
        }
    }

    private void threaded(final Node root, int N, int threads) {
        root.left = new Node();
        root.right = new Node();
        int n = N/2;

        Thread t1, t2;
        if (threads == 2) {
            t1 = new Thread(() -> unthreaded(root.left, n));
            t2 = new Thread(() -> unthreaded(root.right, N-n-1));
        } else {
            t1 = new Thread(() -> threaded(root.left, n, threads-2));
            t2 = new Thread(() -> threaded(root.right, N-n-1, threads-2));
        }

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public int getHeight() {
        int height = 0;
        Node node = root;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }

    public boolean search(int value) {
        return search(root, value);
    }

    private boolean search(Node node, int value) {
        if (node == null) return false;
        if (node.value == value) return true;
        else return (search(node.left, value) || search(node.right, value));
    }
}
