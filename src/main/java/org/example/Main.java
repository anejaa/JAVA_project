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
                    show(type, editId);
                    System.out.println("Type property you want to edit: ");
                    String key = scanner.nextLine();
                    System.out.println("New value:");
                    String value = scanner.nextLine();
                    edit(type, editId, key, value);
                    break;
                case 4:
                    System.out.print("Enter " + type + " id to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    delete(type, deleteId);
                    break;
                case 5:
                    add(scanner, type);
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        } while (option != 6);

    }

    private static void add(Scanner scanner,String type) {
        String uri = "https://dummyjson.com/" + type + "s/add";
        String data = "";
        if(type.equals("user")) {
            data = addUser(scanner);
        } else if (type.equals("cart")) {
            data = addCart(scanner);
        } else if(type.equals("product")) {
            data = addProduct(scanner);
        }

        try {
            String response = ApiClient.doPostRequest(uri,data);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error adding " + type + ". Please try again later.");
        }
    }

    private static String addCart(Scanner scanner) {
        Cart cart = new Cart();
        System.out.println("Enter user id for the cart:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        cart.setUserId(userId);

        Gson gson = new Gson();
        boolean addMore = true;
        while(addMore) {
            System.out.println("Enter product ID:");
            int productId = scanner.nextInt();
            scanner.nextLine();

            String uri = "https://dummyjson.com/products/" + productId;
            Product product = null;

            try {
                String res = ApiClient.doGetRequest(uri);
                product = gson.fromJson(res, Product.class);
            } catch (IOException | InterruptedException e) {
                System.out.println("Sorry, there was an error getting the product. Please try again.");
                continue;
            }

            if (product != null) {
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                CartItem cartItem = new CartItem(product,quantity);
                cart.addItem(cartItem);
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }

            System.out.println("Add more products? (yes/no):");
            String response = scanner.nextLine();
            addMore = response.equalsIgnoreCase("yes");
        }

        return gson.toJson(cart);
    }

    private static String addProduct(Scanner scanner) {
        Product product = new Product();

        System.out.println("Enter title:");
        String title = scanner.nextLine();
        product.setTitle(title);

        System.out.println("Enter description:");
        String description = scanner.nextLine();
        product.setDescription(description);

        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        product.setPrice(price);

        System.out.println("Enter discount percentage:");
        double discountPercentage = scanner.nextDouble();
        product.setDiscountPercentage(discountPercentage);

        System.out.println("Enter rating:");
        double rating = scanner.nextDouble();
        product.setRating(rating);

        System.out.println("Enter stock:");
        int stock = scanner.nextInt();
        product.setStock(stock);

        System.out.println("Enter brand:");
        String brand = scanner.nextLine();
        scanner.nextLine();
        product.setBrand(brand);

        System.out.println("Enter category:");
        String category = scanner.nextLine();
        product.setCategory(category);

        Gson gson = new Gson();
        return gson.toJson(product);
    }


    private static String addUser(Scanner scanner) {
        User user = new User();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        user.setFirstName(firstName);

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        user.setLastName(lastName);

        System.out.println("Enter maiden name:");
        String maidenName = scanner.nextLine();
        user.setMaidenName(maidenName);

        System.out.println("Enter age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        user.setAge(age);

        System.out.println("Enter gender (male | female):");
        String gender = scanner.nextLine();
        user.setGender(gender);

        System.out.println("Enter email:");
        String email = scanner.nextLine();
        user.setEmail(email);

        System.out.println("Enter phone number:");
        String phone = scanner.nextLine();
        user.setPhone(phone);

        Gson gson = new Gson();
        return gson.toJson(user);
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

    private static void edit(String type, int id, String key, String value) {
        String uri = "https://dummyjson.com/" + type + "s/" + id;
        String jsonData = String.format("{\"%s\":\"%s\"}", key, value);
        try {
            String response = ApiClient.doPutRequest(uri, jsonData);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error updating the " + type + ". Please try again later.");
        }
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
                //Cart cart = gson.fromJson(res, Cart.class);
                System.out.println(res);
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
        System.out.println("5. Add " + type);
        System.out.println("6. Go back");
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