import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sampleTestAOSapp_Android {
    private AndroidDriver driver;
    //Update the below variable to indicate which Appium version to use
    private boolean useAppiumV2 = true;
    private String SERVER = "https://uftm-server:8443";

    @Before
    public void setUp() throws Exception {
        UiAutomator2Options caps = new UiAutomator2Options();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:udid", "1a751a5020027ece");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "com.Advantage.aShopping");
        caps.setCapability("appium:appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("uftm:oauthClientId", "oauth2-hwaIXWOBe6yz7wLMffyu@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "eSGtEpUrjt2wL8DanEqK");
        caps.setCapability("uftm:tenantId", "999999999");

        //UFTM will default to Appium v1.x if appiumVersion capability is not indicated, so the "else" part is not needed
        if(useAppiumV2)
            caps.setCapability("uftm:appiumVersion", "v2.x");
        else
            caps.setCapability("uftm:appiumVersion", "v1.x");

        driver = new AndroidDriver(new URL(SERVER + "/wd/hub"), caps);
        System.out.println("UFTM session was successfully created [Android device]");
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
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"LAPTOPS\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"HP ENVY X360\")")).click();
        driver.findElement(AppiumBy.id("linearLayoutProductDetails")).click();
        driver.findElement(AppiumBy.id("imageViewProductDetailsClose")).click();
        driver.findElement(AppiumBy.id("linearLayoutProductQuantity")).click();
        driver.findElement(AppiumBy.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]"))
                .click();
        driver.findElement(AppiumBy.id("textViewApply")).click();
        driver.findElement(AppiumBy.id("buttonProductAddToCart")).click();
        WebElement username = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginUserName']/android.widget.EditText[1]"));
        username.click();
        username.sendKeys("Username");
        WebElement password = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginPassword']/android.widget.EditText[1]"));
        password.click();
        password.sendKeys("password");
        driver.findElement(AppiumBy.id("buttonLogin")).click();
        String ErrorMessage = driver.findElement(AppiumBy.id("textViewFailed")).getText();
        assert ErrorMessage.contains("Incorrect user");
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.findElement(AppiumBy.id("imageViewMenu")).click();
        driver.findElement(AppiumBy.id("textViewMenuHome")).click();
        printAppiumVersionFromLog();
    }

    private void printAppiumVersionFromLog(){
        Pattern MY_PATTERN = Pattern.compile("(Appium v[^\\s]+)");
        Matcher m = MY_PATTERN.matcher(getAppiumlog());
        m.find();
        System.out.println("Appium version extracted from the Appium log:");
        System.out.println(m.group().isEmpty() ? "Not found" : m.group());
    }

    private String getAppiumlog(){
        HashMap<String, String> encoding= new HashMap<String, String>();
        encoding.put("encoding", "UTF-8");
        String logFileContents = (String) driver.executeScript("mc-wd: downloadLogs", encoding);
        return logFileContents;
    }
}


