# Problem B

This problem requires us to create a balanced binary tree. The program must create the tree with and without parallelization and compare the time taken for each, and display some information about the tree.

## Using Multithreading for Creating the Tree

For parallelization, the following approach has been used:

- For two threads
  - The root node is created by the main thread.
  - The left and right subtrees are created by the two child threads using `unthreaded(Node, int)`.
- For four threads
  - The root node is created by the main thread.
  - The left and right subtrees are created by the two child threads, which in turn create two more threads.
  - These 4 child threads create the left and right subtrees of the left and right subtrees of the root node, while the parent threads only wait for the child threads to finish.
- For 2ⁿ threads
  - The first 2 threads create the left and right subtrees of the root node.
  - The next 4 threads create the left and right subtrees of the left and right subtrees of the root node.
  - This process continues until all the threads have been created.
  - Then, the children thread create the trees, while the parent threads wait.

## Tree Creating Technique

The trees are created using the following (Level-Order Insertion) technique:

```java
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
```

## Compilation

To compile, run:

```bash
mvn compile
```
