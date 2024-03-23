package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        byte option;
        do {
            menu();
            option = scanner.nextByte();

            switch (option) {
                case 1:
                    displayUsers();
                    break;
                case 2:
                    displayCarts();
                    break;
                case 3:
                    displayProducts();
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

    private static void displayProducts() {
        String uri = "https://dummyjson.com/products";
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayCarts() {
        String uri = "https://dummyjson.com/carts";
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayUsers() {
        String uri = "https://dummyjson.com/users";
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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