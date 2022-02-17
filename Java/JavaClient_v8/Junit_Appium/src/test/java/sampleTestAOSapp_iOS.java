import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;


public class sampleTestAOSapp_iOS {
    private IOSDriver driver;
    private String SERVER = "http://uftm-server:8080";

    @Before
    public void setUp() throws Exception {
        XCUITestOptions caps = new XCUITestOptions();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("udid", "3ecdf8889a8b18cb8858410d1540f3e6b22a2cb1");
        caps.setCapability("bundleId", "com.mf.iShopping");
        caps.setCapability("uftm:oauthClientId", "oauth2-m2pbTT3wUdAvsdBJXr0w@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "tcNG3V5YVdjbgJhPl3Vl");
        caps.setCapability("uftm:tenantId", "999999999");
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
    }
}
