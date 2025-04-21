import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class SampleTestAOSapp_iOS {
    private IOSDriver driver;
    private String SERVER = "https://uftm-server:8080";

    @Before
    public void setUp() throws Exception {
        XCUITestOptions caps = new XCUITestOptions();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("udid", "00008110-0008048A0C45401E");
        caps.setCapability("bundleId", "com.mf.iShopping");
        caps.setCapability("FTLab:oauthClientId", "oauth2-m2pbTT3wUdAvsdBJXr0w@microfocus.com");
        caps.setCapability("FTLab:oauthClientSecret", "tcNG3V5YVdjbgJhPl3Vl");
        caps.setCapability("FTLab:tenantId", "134848782");
        caps.setCapability("FTLab:video", true);

        driver = new IOSDriver(new URL(SERVER + "/wd/hub"), caps);
    }

    @After
    public void teardown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAOSapp(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.findElement(AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"LAPTOPS\"]")).click();
            driver.findElement(AppiumBy.accessibilityId("HP ENVY x360 - 15t Laptop")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("productDetailsButton"))).click();
            driver.findElement(AppiumBy.accessibilityId("Close2")).click();
            driver.findElement(AppiumBy.accessibilityId("quantityButtonId")).click();
            driver.findElement(AppiumBy.accessibilityId("Plus")).click();
            driver.findElement(AppiumBy.accessibilityId("APPLY")).click();
            driver.findElement(AppiumBy.accessibilityId("ADD TO CART")).click();
            driver.findElement(AppiumBy.accessibilityId("Ok")).click();
            driver.findElement(AppiumBy.accessibilityId("userNameLabel")).sendKeys("Username");
            driver.findElement(AppiumBy.accessibilityId("passwordLabel")).sendKeys("password");
            driver.findElement(AppiumBy.accessibilityId("LOGIN")).click();
            WebElement loginErrorText = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("Incorrect user name or password.")));
            assert loginErrorText.isDisplayed() == true;
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Menu"))).click();
            driver.findElement(AppiumBy.accessibilityId("HOME")).click();
        } catch (Exception e) {
            e.printStackTrace();
            updateFTLabResultStatus(driver, "Failed", e.getMessage());
        }
    }

    /**
     * Updates the test status in FT Lab.
     *
     * @param driver  The WebDriver instance.
     * @param status  The status to set (e.g., "Failed", "Success").
     * @param comment The comment or message to associate with the status.
     */
    public static void updateFTLabResultStatus(IOSDriver driver, String status, String comment) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status);
        map.put("comment", comment);

        driver.executeScript("FTLab:status", map);
    }
}
