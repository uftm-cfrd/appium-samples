import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;


public class sampleTestAOSapp_iOS {
    private IOSDriver driver;
    private String SERVER = "http://uftm-server:8080";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("udid", "3ecdf8889a8b18cb3658410d1540f3e6b22a2cb1");
        caps.setCapability("bundleId", "com.mf.iShopping");
        caps.setCapability("oauthClientId", "oauth2-m2pbTT3wUHAvsGBJxrOw@microfocus.com");
        caps.setCapability("oauthClientSecret", "tcNG3V6YVdjPgJfPl3Vl");
        caps.setCapability("tenantId", "999999999");
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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@name=\"LAPTOPS\"]")).click();
        driver.findElement(MobileBy.AccessibilityId("HP ENVY x360 - 15t Laptop")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("productDetailsButton"))).click();
        driver.findElement(MobileBy.AccessibilityId("Close2")).click();
        driver.findElement(MobileBy.AccessibilityId("quantityButtonId")).click();
        driver.findElement(MobileBy.AccessibilityId("Plus")).click();
        driver.findElement(MobileBy.AccessibilityId("APPLY")).click();
        driver.findElement(MobileBy.AccessibilityId("ADD TO CART")).click();
        driver.findElement(MobileBy.AccessibilityId("Ok")).click();
        driver.findElement(MobileBy.AccessibilityId("userNameLabel")).sendKeys("Username");
        driver.findElement(MobileBy.AccessibilityId("passwordLabel")).sendKeys("password");
        driver.findElement(MobileBy.AccessibilityId("LOGIN")).click();
        WebElement loginErrorText = wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("Incorrect user name or password.")));
        assert loginErrorText.isDisplayed() == true;
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Menu"))).click();
        driver.findElement(MobileBy.AccessibilityId("HOME")).click();
    }
}
