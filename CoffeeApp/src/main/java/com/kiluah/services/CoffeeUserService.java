package com.kiluah.services;

import com.kiluah.models.CoffeeUser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeUserService {
    private List<CoffeeUser> coffeeUser;
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/coffee_users.csv";

    @PostConstruct
    public void init() throws IOException {
        coffeeUser = new ArrayList<>();
        
        // Ensure data directory exists
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // Create users file if it doesn't exist
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            // Create default admin user
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("admin,admin");
            }
        }

        // Read users
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {  // Skip empty lines
                    String[] parts = line.split(",");
                    if (parts.length == 2) {  // Only process valid lines
                        CoffeeUser user = new CoffeeUser();
                        user.setUsername(parts[0].trim());
                        user.setPassword(parts[1].trim());
                        coffeeUser.add(user);
                    }
                }
            }
        }
    }

    public CoffeeUser findByUsername(String username) {
        return coffeeUser.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(CoffeeUser user) {
        coffeeUser.add(user);
        saveAllUsers();
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        for (CoffeeUser user : coffeeUser) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(oldPassword)) {
                    user.setPassword(newPassword);
                    saveAllUsers();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private void saveAllUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (CoffeeUser user : coffeeUser) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
