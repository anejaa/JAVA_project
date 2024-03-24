package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        
        do {
            menu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    handleMenuOption(scanner, "user");
                    break;
                case 2:
                    handleMenuOption(scanner, "cart");
                    break;
                case 3:
                    handleMenuOption(scanner, "product");
                    break;
                case 4:
                    System.out.println("Finishing program.");
                    break;
                default:
                    System.out.println("Wrong option. Try again");
            }
        } while (option != 4);
        scanner.close();
    }

    private static void handleMenuOption(Scanner scanner, String type) {
        int option;
        do {
            displayOptions(type);
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayAll(type);
                    break;
                case 2:
                    System.out.print("Enter " + type + " id: ");
                    int id = scanner.nextInt();
                    show(type, id);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        } while (option != 5);

    }

    private static void show(String type, int id) {
        String uri = "https://dummyjson.com/" + type + "s/" + id;
        try {
            String res = ApiClient.doGetRequest(uri);
            System.out.println(res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayAll(String type) {
        String uri = "https://dummyjson.com/" + type + "s";
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayOptions(String type) {
        System.out.println("Select Menu");
        System.out.println("1. All " + type + "s");
        System.out.println("2. Find " + type);
        System.out.println("3. Edit "+ type);
        System.out.println("4. Delete " + type);
        System.out.println("5. Go back");
        System.out.print("Select option: ");
    }

    private static void menu() {
        System.out.println("Select Menu");
        System.out.println("1. Users");
        System.out.println("2. Carts");
        System.out.println("3. Products");
        System.out.println("4. Exit");
        System.out.print("Select option: ");
    }
}