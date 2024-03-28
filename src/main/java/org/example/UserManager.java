package org.example;

import com.google.gson.Gson;

import java.util.Scanner;

public class UserManager {
    protected static String addUser(Scanner scanner) {
        User user = new User();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        while(firstName.trim().isEmpty()) {
            System.out.println("First name cannot be empty. Please enter first name:");
            firstName = scanner.nextLine();
        }
        user.setFirstName(firstName);

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        while(lastName.trim().isEmpty()) {
            System.out.println("Last name cannot be empty. Please enter last name:");
            lastName = scanner.nextLine();
        }
        user.setLastName(lastName);

        System.out.println("Enter maiden name:");
        String maidenName = scanner.nextLine();
        user.setMaidenName(maidenName);

        System.out.println("Enter age:");
        while(!scanner.hasNextInt()) {
            System.out.println("Invalid input. Age must be a number. Enter age:");
            scanner.next();
        }
        int age = scanner.nextInt();
        scanner.nextLine();
        while(age <= 0) {
            System.out.println("Age must be a positive number. Please enter age:");
            while(!scanner.hasNextInt()) {
                System.out.println("Invalid input. Age must be a number. Enter age:");
                scanner.next();
            }
            age = scanner.nextInt();
            scanner.nextLine();
        }
        user.setAge(age);

        System.out.println("Enter gender (male | female):");
        String gender = scanner.nextLine();
        while(!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
            System.out.println("Invalid gender. Please enter 'male' or 'female':");
            gender = scanner.nextLine();
        }
        user.setGender(gender);

        System.out.println("Enter email:");
        String email = scanner.nextLine();
        while(!isValidEmail(email)) {
            System.out.println("Invalid email. Please enter a valid email:");
            email = scanner.nextLine();
        }
        user.setEmail(email);

        System.out.println("Enter phone number:");
        String phone = scanner.nextLine();
        user.setPhone(phone);

        Gson gson = new Gson();
        return gson.toJson(user);
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}