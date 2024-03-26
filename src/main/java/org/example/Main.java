package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            menu();
            try {
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
                        String imageUrl = "https://images.unsplash.com/photo-1710874087896-6683c258deb4?q=80&w=2487&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
                        String destinationFile = "C:\\Users\\aneja\\OneDrive\\Pictures\\savedImage.jpg";

                        try {
                            ImageHandler.saveImageFromURL(imageUrl, destinationFile);
                        } catch (Exception e) {
                            System.out.println("There was an error saving the image: " + e.getMessage());
                        }
                    case 5:
                        System.out.println("Finishing program.");
                        break;
                    default:
                        System.out.println("Wrong option. Try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                option = 0;
            }
        } while (option != 5);

        scanner.close();
    }

    private static void handleMenuOption(Scanner scanner, String type) {
        int option;
        do {
            displayOptions(type);
            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        displayAll(type);
                        break;
                    case 2:
                        if(type.equals("cart")) {
                            System.out.println("Enter id of cart: ");
                        } else {
                            System.out.println("Enter search query for " + type + ": ");
                        }

                        String searchQuery = scanner.nextLine();
                        search(searchQuery, type);
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
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                option = 0;
            }
        } while (option != 6);

    }

    private static void search(String searchQuery, String type) {
        String uri;
        if(type.equals("cart")) {
            uri = "https://dummyjson.com/" + type + "s/" + searchQuery;
        } else {
            uri = "https://dummyjson.com/" + type + "s/search?q=" + searchQuery;
        }
        try {
            String response = ApiClient.doGetRequest(uri);
            System.out.println("Search results: " + response);
        } catch (IOException | InterruptedException e) {
            System.out.println("There was an error performing the search: " + e.getMessage());
        }
    }

    private static void add(Scanner scanner, String type) {
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
            String response = ApiClient.doPostRequest(uri, data);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry, there was an error adding " + type + ". Please try again later.");
        }
    }

    private static String addCart(Scanner scanner) {
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

    private static String addProduct(Scanner scanner) {
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
                System.out.println("Title cannot be empty. Please enter a valid title:");
            }
        }

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
        System.out.println("2. Search " + type);
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
        System.out.println("4. Save Image");
        System.out.println("5. Exit");
        System.out.print("Select option: ");
    }
}