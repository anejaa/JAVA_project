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
                    int userOption;
                    do {
                        displayUserOptions();
                        userOption = scanner.nextInt();

                        switch (userOption) {
                            case 1:
                                displayUsers();
                                break;
                            case 2:
                                System.out.print("Enter user id: ");
                                int id = scanner.nextInt();
                                showUser(id);
                                break;
                            case 3:

                                break;
                            case 4:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid option, please try again.");
                                break;
                        }
                    } while (userOption != 4);
                    break;
                case 2:
                    do {
                        displayCartOptions();
                        userOption = scanner.nextInt();

                        switch (userOption) {
                            case 1:
                                displayCarts();
                                break;
                            case 2:
                                System.out.print("Enter cart id: ");
                                int id = scanner.nextInt();
                                showCart(id);
                                break;
                            case 3:

                                break;
                            case 4:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid option, please try again.");
                                break;
                        }
                    } while (userOption != 4);
                    break;
                case 3:
                    do {
                        displayProductOptions();
                        userOption = scanner.nextInt();

                        switch (userOption) {
                            case 1:
                                displayProducts();
                                break;
                            case 2:
                                System.out.print("Enter product id: ");
                                int id = scanner.nextInt();
                                showProduct(id);
                                break;
                            case 3:

                                break;
                            case 4:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid option, please try again.");
                                break;
                        }
                    } while (userOption != 4);
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

    private static void showProduct(int id) {
        String uri = "https://dummyjson.com/products/" + id;
        try {
            String res = ApiClient.doGetRequest(uri);
            System.out.println(res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayProductOptions() {
        System.out.println("Select Menu");
        System.out.println("1. All products");
        System.out.println("2. Find product");
        System.out.println("3. Edit product");
        System.out.println("3. Delete product");
        System.out.println("4. Go back");
        System.out.print("Select option: ");
    }

    private static void showCart(int id) {
        String uri = "https://dummyjson.com/carts/" + id;
        try {
            String res = ApiClient.doGetRequest(uri);
            System.out.println(res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayCartOptions() {
        System.out.println("Select Menu");
        System.out.println("1. All carts");
        System.out.println("2. Find cart ");
        System.out.println("3. Edit cart");
        System.out.println("3. Delete cart");
        System.out.println("4. Go back");
        System.out.print("Select option: ");
    }

    private static void showUser(int id) {
        String uri = "https://dummyjson.com/users/" + id;
        try {
            String res = ApiClient.doGetRequest(uri);
            System.out.println(res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayUserOptions() {
        System.out.println("Select Menu");
        System.out.println("1. All users");
        System.out.println("2. Find user ");
        System.out.println("3. Edit user");
        System.out.println("3. Delete user");
        System.out.println("4. Go back");
        System.out.print("Select option: ");
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