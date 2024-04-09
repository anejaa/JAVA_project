package org.example;

import com.google.gson.Gson;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductManager {
    protected static String addProduct(Scanner scanner) {
        Product product = new Product();
        Gson gson = new Gson();

        System.out.println("Enter title:");
        String title = "";
        while (title.isEmpty()) {
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty. Please enter a valid title:");
            }
        }
        product.setTitle(title);

        System.out.println("Enter description:");
        String description ="";
        while (description.isEmpty()) {
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty. Please enter a valid description:");
            }
        }
        product.setDescription(description);

        System.out.println("Enter price:");
        double price = 0.0;
        while (price <= 0.0) {
            try {
                price = scanner.nextDouble();
                if (price <= 0.0) {
                    System.out.println("Price must be a positive number. Please enter a valid price:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price:");
                scanner.nextLine();
            }
        }
        product.setPrice(price);

        System.out.println("Enter discount percentage:");
        double discountPercentage = -1.0;
        while (discountPercentage < 0.0 || discountPercentage > 100.0) {
            try {
                discountPercentage = scanner.nextDouble();
                if (discountPercentage < 0.0 || discountPercentage > 100.0) {
                    System.out.println("Discount percentage must be between 0 and 100. Please enter a valid discount percentage:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid discount percentage:");
                scanner.nextLine();
            }
        }
        product.setDiscountPercentage(discountPercentage);

        System.out.println("Enter rating:");
        double rating = -1.0;
        while (rating < 0.0 || rating > 5.0) {
            try {
                rating = scanner.nextDouble();
                if (rating < 0.0 || rating > 5.0) {
                    System.out.println("Rating must be between 0 and 5. Please enter a valid rating:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid rating:");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
        product.setRating(rating);

        System.out.println("Enter stock:");
        int stock = 0;
        while (stock <= 0) {
            try {
                stock = scanner.nextInt();
                if (stock <= 0) {
                    System.out.println("Stock must be a positive integer. Please enter a valid stock:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid stock:");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
        product.setStock(stock);

        System.out.println("Enter brand:");
        String brand = "";
        while (brand.isEmpty()) {
            brand = scanner.nextLine().trim();
            if (brand.isEmpty()) {
                System.out.println("Brand cannot be empty. Please enter a valid brand:");
            }
        }
        product.setBrand(brand);

        System.out.println("Enter category:");
        String category = "";
        while (category.isEmpty()) {
            category = scanner.nextLine().trim();
            if (category.isEmpty()) {
                System.out.println("Category cannot be empty. Please enter a valid category:");
            }
        }
        product.setCategory(category);

        return gson.toJson(product);
    }
}
