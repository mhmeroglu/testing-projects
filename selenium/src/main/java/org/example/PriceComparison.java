package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PriceComparison {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            // Web siteleri ve ürün fiyatlarını saklamak için değişkenler
            String[] urls = {
                    "https://www.trendyol.com/sony/playstation-5-dijital-slim-turkce-menu-1-tb-p-781022412?boutiqueId=61&merchantId=110889",
                    "https://www.n11.com/urun/sony-playstation-5-ps5-slim-1-tb-digital-edition-oyun-konsolu-ithalatci-garantili-46158593?magaza=teknodeposu&utm_source=comp_akakce&utm_medium=cpc&utm_campaign=akakce_genel",
                    "https://www.hepsiburada.com/sony-playstation-5-slim-digital-edition-p-HBCV00005FNTP9?magaza=BA%C5%9EAK%C5%9EEH%C4%B0R%20AWM&utm_source=pc&utm_medium=akakce&utm_campaign=c&utm_content=c&utm_term=k&wt_pc=akakce.c.k.pc&v=1.57.2"
            };
            String[] prices = new String[urls.length];

            for (int i = 0; i < urls.length; i++) {
                driver.get(urls[i]);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Sayfanın yüklenmesini bekler

                // Sayfa tamamen yüklenene kadar bekleyin
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                switch (i) {
                    case 0: // Trendyol
                        WebElement priceElementTrendyol = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.pr-bx-nm .prc-dsc")));
                        prices[i] = priceElementTrendyol.getText();
                        break;

                    case 1: // N11
                        WebElement priceElementN11 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.newPrice ins")));
                        prices[i] = priceElementN11.getText();
                        break;

                    case 2: // Hepsiburada
                        WebElement priceElementHepsiburada = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.rNEcFrF82c7bJGqQHxht span:first-child")));
                        prices[i] = priceElementHepsiburada.getText();
                        break;
                }
            }

            // Sonuçları karşılaştır ve terminale yazdır
            double cheapest = Double.MAX_VALUE;
            double mostExpensive = Double.MIN_VALUE;
            double total = 0;
            for (String price : prices) {
                double priceValue = Double.parseDouble(price.replace(",", "").replace(" TL", "").replace("₺", ""));
                total += priceValue;
                if (priceValue < cheapest) {
                    cheapest = priceValue;
                }
                if (priceValue > mostExpensive) {
                    mostExpensive = priceValue;
                }
            }
            double average = total / prices.length;

            System.out.println("En ucuz fiyat: " + cheapest + " TL");
            System.out.println("En pahalı fiyat: " + mostExpensive + " TL");
            System.out.println("Ortalama fiyat: " + average + " TL");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Tarayıcıyı kapat
            driver.quit();
        }
    }
}