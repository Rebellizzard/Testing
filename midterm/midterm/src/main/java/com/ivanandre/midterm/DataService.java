package com.ivanandre.midterm;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class DataService {

    private static final String CSV_FILE_PATH = "coffee_list.csv";

    public boolean saveToCsv(List<Coffee> coffeeList) {
        try {
            // Create a new file in the root directory
            FileWriter csvWriter = new FileWriter(CSV_FILE_PATH);

            // Write CSV header
            csvWriter.append("ID,Name,Type,Size,Price,Roast Level,Origin,IsDecaf,Stock,Flavor Notes,Brew Method\n");

            // Write coffee data to CSV
            for (Coffee coffee : coffeeList) {
                csvWriter.append(String.valueOf(coffee.getId())).append(",");
                csvWriter.append(escapeCsvField(coffee.getName())).append(",");
                csvWriter.append(escapeCsvField(coffee.getType())).append(",");
                csvWriter.append(escapeCsvField(coffee.getSize())).append(",");
                csvWriter.append(String.valueOf(coffee.getPrice())).append(",");
                csvWriter.append(escapeCsvField(coffee.getRoastLevel())).append(",");
                csvWriter.append(escapeCsvField(coffee.getOrigin())).append(",");
                csvWriter.append(String.valueOf(coffee.isDecaf())).append(",");
                csvWriter.append(String.valueOf(coffee.getStock())).append(",");

                // Handle list of flavor notes by joining them with a semicolon
                String flavorNotesStr = String.join(";", coffee.getFlavorNotes());
                csvWriter.append(escapeCsvField(flavorNotesStr)).append(",");

                csvWriter.append(escapeCsvField(coffee.getBrewMethod())).append("\n");
            }

            // Close the writer
            csvWriter.flush();
            csvWriter.close();

            System.out.println("CSV file has been automatically updated at: " + CSV_FILE_PATH);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving coffee list to CSV: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }

        // If the field contains commas, quotes, or newlines, wrap it in quotes and escape any quotes
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}