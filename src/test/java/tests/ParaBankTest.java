package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class ParaBankTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/index.htm";

    private String generateRandomString() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) sb.append(chars.charAt(rand.nextInt(chars.length())));
        return sb.toString();
    }

    @BeforeTest
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vovah\\OneDrive\\Desktop\\chromedriver.exe");
    }

    @BeforeMethod
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void closeBrowser() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testRegistrationOnly() {
        driver.findElement(By.linkText("Register")).click();
        wait.until(ExpectedConditions.titleContains("Register"));

        String username = generateRandomString();
        String password = generateRandomString();

        driver.findElement(By.id("customer.firstName")).sendKeys("Test");
        driver.findElement(By.id("customer.lastName")).sendKeys("User");
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("New York");
        driver.findElement(By.id("customer.address.state")).sendKeys("NY");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("10001");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys(username);
        driver.findElement(By.id("customer.password")).sendKeys(password);
        driver.findElement(By.id("repeatedPassword")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Your account was created successfully')]")));
        Assert.assertTrue(success.isDisplayed(), "Реєстрація не вдалася");
    }

    @Test
    public void testLoginAfterRegistration() {
        driver.findElement(By.linkText("Register")).click();
        wait.until(ExpectedConditions.titleContains("Register"));

        String username = generateRandomString();
        String password = generateRandomString();

        driver.findElement(By.id("customer.firstName")).sendKeys("Test");
        driver.findElement(By.id("customer.lastName")).sendKeys("User");
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("New York");
        driver.findElement(By.id("customer.address.state")).sendKeys("NY");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("10001");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys(username);
        driver.findElement(By.id("customer.password")).sendKeys(password);
        driver.findElement(By.id("repeatedPassword")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Your account was created successfully')]")));

        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out")));
        Assert.assertTrue(logoutLink.isDisplayed(), "Користувач не залогінився після реєстрації");
    }

    @Test
    public void testBillPay() {
        driver.findElement(By.linkText("Register")).click();
        wait.until(ExpectedConditions.titleContains("Register"));

        String username = generateRandomString();
        String password = generateRandomString();

        driver.findElement(By.id("customer.firstName")).sendKeys("Test");
        driver.findElement(By.id("customer.lastName")).sendKeys("User");
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("New York");
        driver.findElement(By.id("customer.address.state")).sendKeys("NY");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("10001");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys(username);
        driver.findElement(By.id("customer.password")).sendKeys(password);
        driver.findElement(By.id("repeatedPassword")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Your account was created successfully')]")));

        WebElement billPayLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Bill Pay")));
        billPayLink.click();
        wait.until(ExpectedConditions.titleContains("Bill Pay"));

        driver.findElement(By.name("payee.name")).sendKeys("Test Payee");
        driver.findElement(By.name("payee.address.street")).sendKeys("456 Payee St");
        driver.findElement(By.name("payee.address.city")).sendKeys("Los Angeles");
        driver.findElement(By.name("payee.address.state")).sendKeys("CA");
        driver.findElement(By.name("payee.address.zipCode")).sendKeys("90001");
        driver.findElement(By.name("payee.phoneNumber")).sendKeys("9876543210");
        driver.findElement(By.name("payee.accountNumber")).sendKeys("12345");
        driver.findElement(By.name("verifyAccount")).sendKeys("12345");
        driver.findElement(By.name("amount")).sendKeys("25.50");

        driver.findElement(By.xpath("//input[@value='Send Payment']")).click();

        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Bill Payment Complete')]")));
        Assert.assertTrue(successMsg.isDisplayed(), "Платіж не виконано");
    }
}
