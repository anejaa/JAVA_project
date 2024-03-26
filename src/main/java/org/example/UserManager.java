package org.example;

import com.google.gson.Gson;

import java.util.Scanner;

public class UserManager {
    protected static String addUser(Scanner scanner) {
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
}
