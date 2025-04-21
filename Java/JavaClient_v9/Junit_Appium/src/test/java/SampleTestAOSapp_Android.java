import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.*;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SampleTestAOSapp_Android {
    private AndroidDriver driver;
    private String SERVER = "https://uftm-server:8080";
    
    @Before
    public void setUp() throws Exception {
        UiAutomator2Options caps = new UiAutomator2Options();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "RQCW505KPVB");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.Advantage.aShopping");
        caps.setCapability("appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("FTLab:oauthClientId", "oauth2-m2pbTT3wUdAvsdBJXr0w@microfocus.com");
        caps.setCapability("FTLab:oauthClientSecret", "tcNG3V5YVdjbgJhPl3Vl");
        caps.setCapability("FTLab:tenantId", "134848782");
        caps.setCapability("FTLab:video", true);

        driver = new AndroidDriver(new URL(SERVER + "/wd/hub"), caps);
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
            //Implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.findElement(AppiumBy.xpath(
                            "//android.widget.TextView[@resource-id=\"com.Advantage.aShopping:id/textViewCategory\" and @text=\"LAPTOPS\"]"))
                    .click();
            driver.findElement(AppiumBy.xpath(
                            "//android.widget.TextView[@resource-id=\"com.Advantage.aShopping:id/textViewProductName\" and @text=\"HP ENVY X360 - 15T LAPTOP\"]"))
                    .click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/linearLayoutProductDetails")).click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/imageViewProductDetailsClose")).click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/linearLayoutProductQuantity")).click();
            driver.findElement(AppiumBy.xpath(
                            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]"))
                    .click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/textViewApply")).click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/buttonProductAddToCart")).click();
            driver.findElement(AppiumBy.xpath(
                            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[3]/android.widget.EditText"))
                    .sendKeys("Username");
            driver.findElement(AppiumBy.xpath(
                            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[4]/android.widget.EditText"))
                    .sendKeys("password");
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/buttonLogin")).click();
            String ErrorMessage = driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/textViewFailed")).getText();
            assert ErrorMessage.contains("Incorrect user");
            driver.navigate().back();
            driver.navigate().back();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/imageViewMenu")).click();
            driver.findElement(AppiumBy.id("com.Advantage.aShopping:id/textViewMenuHome")).click();
            Thread.sleep(2000);
            updateFTLabResultStatus(driver, "Passed", "");
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
    public static void updateFTLabResultStatus(AndroidDriver driver, String status, String comment) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status);
        map.put("comment", comment);

        driver.executeScript("FTLab:status", map);
    }
}


