package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Github2 {

    public static void main(String[] args) {
        // Set base URI for GitHub API
        RestAssured.baseURI = "https://api.github.com";

        // Define repository name
        String repositoryName = "surya0123/Telegram";

        // Send GET request to retrieve repository details
        Response response = given()
                .header("Accept", "application/vnd.github.v3+json")
                .when()
                .get("/repos/" + repositoryName);

        // Validate HTTP status code
        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        // Validate response format and content
        if (statusCode == 200) {
            response.then()
                    .body("name", equalTo("Telegram"))
                    .body("full_name", equalTo("surya0123/Telegram"))
                    .body("description", anyOf(nullValue(), notNullValue()))
                    .body("owner.login", equalTo("surya0123"))
                    .body("private", equalTo(false));

            System.out.println("Repository details retrieved successfully.");
        } else if (statusCode == 404) {
            System.out.println("Repository not found.");
        } else {
            System.out.println("Unexpected error occurred.");
        }

        // Perform data-driven testing
        performDataDrivenTesting("C:\\Users\\Surya\\Downloads\\research-and-development-survey-2022.csv");
    }

    private static void performDataDrivenTesting(String csvFile) {
        try (FileReader fileReader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<CSVRecord> records = csvParser.getRecords();
            int totalRows = records.size();

            Random random = new Random();

            // Randomly select 5 rows from the CSV file
            for (int i = 0; i < 10; i++) {
                int randomRowIndex = random.nextInt(totalRows);
                CSVRecord randomRecord = records.get(randomRowIndex);

                // Retrieve and print the randomly selected row
                System.out.println("Randomly selected row " + (randomRowIndex + 1) + ": " + randomRecord);

                // Retrieve and print the "Status" field value if it is not empty
                String status = randomRecord.get("Status");
                if (status != null && !status.isEmpty()) {
                    System.out.println("Status: " + status);
                } else {
                    System.out.println("Status: <empty>");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}