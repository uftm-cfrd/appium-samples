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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class sampleTestAOSapp_iOS {
    private IOSDriver driver;
    //Update the below variable to indicate whether you want to use Appium v2 or the default Appium v1
    private boolean useAppiumV2 = true;
    private String SERVER = "https://cfrd-master.swinfra.net:8443";

    @Before
    public void setUp() throws Exception {
        XCUITestOptions caps = new XCUITestOptions();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("appium:udid", "fd95352a82e684a73378eb10a0e584fbb098a56c");
        caps.setCapability("appium:bundleId", "com.mf.iShopping");
        caps.setCapability("uftm:oauthClientId", "oauth2-hwaIXWOBe6yz7wLMffyu@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "eSGtEpUrjt2wL8DanEqK");
        caps.setCapability("uftm:tenantId", "999999999");

        //UFTM will default to Appium v1.x if appiumVersion capability is not indicated, so the "else" part is not needed
        if(useAppiumV2)
            caps.setCapability("uftm:appiumVersion", "v2.x");
        else
            caps.setCapability("uftm:appiumVersion", "v1.x");

        driver = new IOSDriver(new URL(SERVER + "/wd/hub"), caps);
        System.out.println("UFTM session was successfully created [iOS device]");
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
