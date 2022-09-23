import java.util.*;


public class Solution1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int T = input.nextInt();

        for (int t=0; t < T; t++) {
            int n = input.nextInt();

            int[] arr = new int[n];
            for (int i=0; i < n; i++)
                arr[i] = input.nextInt();

            int x = input.nextInt();
            int countNodes = 0;

            if (1 <= x && x <= arr.length) {
                System.out.println("True");
                countNodes = arr.length - x + 1;
            }
            else {
                System.out.println("False");
            }

            System.out.println(countNodes);

            for (int i=0; i < n; i++) {
                if (i != x-1)
                    System.out.print(arr[i] + " ");
            }

            System.out.println("");
        }
    }
}
