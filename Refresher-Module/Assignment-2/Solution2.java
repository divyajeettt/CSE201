import java.util.*;


class Node {
    int data;
    Node next;

    public Node() {
        this.data = 0;
        this.next = null;
    }

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}


class LinkedList {
    Node head;
    int length;

    public LinkedList(Node head) {
        this.head = head;
        this.length = 1;
    }

    public void insert(Node node) {
        Node curr = this.head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = node;
        this.length++;
    }

    public void display() {
        Node curr = this.head;
        while (curr != null) {
            System.out.print(curr.data + "->");
            curr = curr.next;
        }
        System.out.println("");
    }

    public void sort() {
        for (Node p1=this.head; p1!=null; p1=p1.next) {
            for (Node p2=this.head; p2!=p1; p2=p2.next) {
                if (p1.data < p2.data) {
                    int temp = p1.data;
                    p1.data = p2.data;
                    p2.data = temp;
                }
            }
        }
    }

    public void find() {
        this.sort();
        Node curr = this.head;
        System.out.println("First: " + curr.data);
        while (curr.next != null) {
            curr = curr.next;
        }
        System.out.println("First: " + curr.data);
    }
}


public class Solution2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int headData = input.nextInt();
        Node head = new Node();
        head.data = headData;

        LinkedList L = new LinkedList(head);

        Node node;

        node = new Node(5);
        L.insert(node);

        node = new Node(1);
        L.insert(node);

        node = new Node(8);
        L.insert(node);

        node = new Node(2);
        L.insert(node);

        L.display();
        L.sort();
        L.display();
        L.find();
    }
}
