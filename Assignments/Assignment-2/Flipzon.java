import java.util.*;


public class Flipzon {
    private static final Scanner input = new Scanner(System.in);

    private static int inputChoice(int upper) {
        System.out.print(">>> ");
        int choice = input.nextInt();
        while (!(1 <= choice && choice <= upper)) {
            System.out.println("Please enter a number between 1 - " + upper);
            System.out.print(">>> ");
            choice = input.nextInt();
        }
        return choice;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Flipzon, the market at your doorstep!");

        while (true) {
            System.out.println("1. Enter the Application");
            System.out.println("2. Exit the Application");
            int choice = inputChoice(5);

            if (choice == 1) {
            } else if (choice == 2) {
            } else if (choice == 3) {
            } else if (choice == 4) {
            } else {
                System.out.println("Thanks for using Flipzon! We hope to see you again!");
                break;
            }
        }

        input.close();
    }
}

// I think:
// Elite, Prime, and Normal will inherit from Customer -> will not be able to switch from normal -> prime -> elite
// Maybe a class Product, and maybe a parent class Catgory
// class Admin
// class Cart