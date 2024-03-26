package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;

public class CartManager {
    protected static String addCart(Scanner scanner) {
        Cart cart = new Cart();
        System.out.println("Enter user id for the cart:");
        int userId;
        while (true) {
            try {
                userId = Integer.parseInt(scanner.nextLine());
                if (userId <= 0) {
                    System.out.println("Invalid user ID. Please enter a positive integer:");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for user ID:");
            }
        }
        cart.setUserId(userId);

        Gson gson = new Gson();
        boolean addMore = true;
        while(addMore) {
            System.out.println("Enter product ID:");
            int productId;

            while (true) {
                try {
                    productId = Integer.parseInt(scanner.nextLine());
                    if (productId <= 0) {
                        System.out.println("Invalid product ID. Please enter a positive integer:");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for product ID:");
                }
            }

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
                int quantity;

                while (true) {
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity <= 0) {
                            System.out.println("Invalid quantity. Please enter a positive integer:");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer for quantity:");
                    }
                }

                CartItem cartItem = new CartItem(product,quantity);
                cart.addItem(cartItem);
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }

            System.out.println("Add more products? (yes/no):");
            String response;
            while (true) {
                response = scanner.nextLine().toLowerCase();
                if (!response.equals("yes") && !response.equals("no")) {
                    System.out.println("Invalid response. Please enter 'yes' or 'no':");
                    continue;
                }
                break;
            }
            addMore = response.equals("yes");
        }

        return gson.toJson(cart);
    }
}
