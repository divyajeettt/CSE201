import java.util.*;


public class Question3 {
    static boolean isInt(double x) {
        int integerPart = (int)x;
        double remaining = x - integerPart;
        return (remaining == 0);
    }

    static boolean isPrime(int x) {
        if (x == 2 || x == 3)
            return true;

        for (int i=2; i <= Math.sqrt(x); i++) {
            if (x % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String string = input.nextLine();
        double n = string.length();
        int curr = 2;

        if (n == 1)
            System.out.println("1");
        else {
            while (n != 1) {
                if (!isPrime(curr)) {
                    curr++;
                    continue;
                }

                if (!isInt(n/curr))
                    curr++;
                else {
                    System.out.print(curr);
                    if (n/curr != 1)
                        System.out.print("*");
                    n /= curr;
                }
            }
        }
    }
}