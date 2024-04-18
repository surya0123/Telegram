package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.bytebuddy.implementation.InvokeDynamic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Github {

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
    }
}



