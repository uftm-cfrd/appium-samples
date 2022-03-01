import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.URL;
import java.util.concurrent.TimeUnit;

public class sampleTestAOSapp_Android {
    private AndroidDriver driver;
    private String SERVER = "http://uftm-server:8080";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "RF8N807MPYH");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.Advantage.aShopping");
        caps.setCapability("appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("oauthClientId", "oauth2-m2pbTT3wUHAvsGBJxrOw@microfocus.com");
        caps.setCapability("oauthClientSecret", "tcNG3V6YVdjPgJfPl3Vl");
        caps.setCapability("tenantId", "999999999");
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(MobileBy.xpath(
                "//android.view.ViewGroup[@content-desc=\"Home Page\"]/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.TextView"))
                .click();
        driver.findElement(MobileBy.xpath(
                "//android.widget.RelativeLayout[@content-desc=\"Laptops\"]/android.widget.LinearLayout/android.widget.GridView/android.widget.RelativeLayout[4]/android.widget.TextView[1]"))
                .click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/linearLayoutProductDetails")).click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/imageViewProductDetailsClose")).click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/linearLayoutProductQuantity")).click();
        driver.findElement(MobileBy.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]"))
                .click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/textViewApply")).click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/buttonProductAddToCart")).click();
        driver.findElement(MobileBy.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[3]/android.widget.EditText"))
                .sendKeys("Username");
        driver.findElement(MobileBy.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[4]/android.widget.EditText"))
                .sendKeys("password");
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/buttonLogin")).click();
        String ErrorMessage = driver.findElement(MobileBy.id("com.Advantage.aShopping:id/textViewFailed")).getText();
        assert ErrorMessage.contains("Incorrect user");
        driver.navigate().back();
        driver.navigate().back();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/imageViewMenu")).click();
        driver.findElement(MobileBy.id("com.Advantage.aShopping:id/textViewMenuHome")).click();
    }

}


