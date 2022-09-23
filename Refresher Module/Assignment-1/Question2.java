import java.util.*;


public class Question2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();

        int count = 0;

        for (int i=0; i < string.length(); i++) {
            char current = string.charAt(i);
            count++;

            if (i == string.length()-1 || current != string.charAt(i+1)) {
                System.out.print(current);
                System.out.print(count);
                count = 0;
            }
        }
    }
}