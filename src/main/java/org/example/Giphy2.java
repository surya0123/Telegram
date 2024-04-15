package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

public class Giphy2 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String gifId = "chzz1FQgqhytWRWbp3";

        String giphyApiKey = "7JX42YkuBt7DGHaZExDDxKZDnHzN84H4"; // Replace with your actual Giphy API key
        String telegramBotToken = "6768561787:AAH7oNzeB4DEPqclN91HPnf2nJlwOCvHwa8";
        String chatId = "5930317471";

        String giphyApiUrl = "https://api.giphy.com/v1/gifs/" + gifId + "?api_key=" + giphyApiKey;
        String totalViews = getTotalViews(giphyApiUrl);
        System.out.println("Daily Giphy views Update: " + totalViews);
        driver.quit();
        sendTelegramMessage(telegramBotToken, chatId, "Daily Giphy Views Update:Total Views: " + totalViews);

    }
    public static void sendTelegramMessage(String botToken, String chatId, String message) {
        String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + message;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.getResponseCode();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTotalViews(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject data = jsonResponse.getJSONObject("data");
        JSONObject image = data.getJSONObject("images");
        JSONObject original = image.getJSONObject("original");
        String totalViews = original.getString("mp4_size");

        return totalViews;
    }
}