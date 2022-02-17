import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class sampleTestAOSapp_Android {
    private AndroidDriver driver;
    private String SERVER = "http://uftm-server:8080";

    @Before
    public void setUp() throws Exception {
        UiAutomator2Options caps = new UiAutomator2Options();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "RF8N879MPYH");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.Advantage.aShopping");
        caps.setCapability("appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("uftm:oauthClientId", "oauth2-m2pbTT3wUdAvsdBJXr0w@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "tcNG3V5YVdjbgJhPl3Vl");
        caps.setCapability("uftm:tenantId", "999999999");
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
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(AppiumBy.xpath(
                "//android.view.ViewGroup[@content-desc=\"Home Page\"]/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.TextView"))
                .click();
        driver.findElement(AppiumBy.xpath(
                "//android.widget.RelativeLayout[@content-desc=\"Laptops\"]/android.widget.LinearLayout/android.widget.GridView/android.widget.RelativeLayout[4]/android.widget.TextView[1]"))
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

    }

}


