package org.example;

import org.jetbrains.annotations.TestOnly;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@TestOnly
public class GiphyViewsTrackerBot {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String giphyProjectUrl = "https://giphy.com/gifs/i3q36zEFShC18oS6v0";
        String telegramBotToken = "6768561787:AAH7oNzeB4DEPqclN91HPnf2nJlwOCvHwa8";
        String chatId = "5930317471";

        driver.get(giphyProjectUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement viewsElement = driver.findElement(By.xpath("//div[@ class=\"ViewCountContainer-sc-15ri43l hCQudc ss-view\"]"));

        String totalViews = viewsElement.getText();
        System.out.println("Daily Giphy views Update:" + totalViews);

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
}
