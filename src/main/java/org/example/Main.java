package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            menu();
            option = scanner.nextInt();
            scanner.nextLine();

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
            scanner.nextLine();

            switch (option) {
                case 1:
                    displayAll(type);
                    break;
                case 2:
                    System.out.print("Enter " + type + " id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    show(type, id);
                    break;
                case 3:
                    System.out.println("Enter " + type + " id: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine();
                    edit(editId);
                    break;
                case 4:
                    System.out.print("Enter " + type + " id to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    delete(type, deleteId);
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

    private static void delete(String type, int id) {
        String uri = "https://dummyjson.com/" + type + "s/" + id;
        try {
            String response = ApiClient.doDeleteRequest(uri);
            System.out.println("Deleted " + type + " with id " + id + ": " + response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error deleting the " + type + ". Please try again later.");
        }
    }

    private static void edit(int id) {

    }

    private static void show(String type, int id) {
        String uri = "https://dummyjson.com/" + type + "s/" + id;
        try {
            String res = ApiClient.doGetRequest(uri);
            Gson gson = new Gson();
            if (type.equals("user")) {
                User user = gson.fromJson(res, User.class);
                System.out.println(user.toString());
            } else if (type.equals("cart")) {
                Cart cart = gson.fromJson(res, Cart.class);
                System.out.println(cart.toString());
            } else if (type.equals("product")) {
                Product product = gson.fromJson(res, Product.class);
                System.out.println(product.toString());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error retrieving the data. Please try again later.");
        }
    }

    private static void displayAll(String type) {
        String uri = "https://dummyjson.com/" + type + "s";
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error retrieving the data. Please try again later.");
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